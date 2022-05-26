package Modele;

import Patterns.Observable;

import java.util.Random;

import static java.util.Objects.requireNonNull;

// TODO: Ajouter des logs quand des actions correctes et incorrectes sont effectuées

public class Jeu extends Observable {
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private int joueurActuel;
    private Tour tourActuel;
    private TypeCoup prochainCoup;
    private boolean partieTerminee;
    private Historique historique;
    private final Random rand;
    private int choixJoueurDebut = -1;

    public Jeu() {
        rand = new Random();
        joueurActuel = -1;
        partieTerminee = true;
    }

    public void nouveauJoueur(String nom, TypeJoueur type, Pion p, int handicap) {
        if (joueur1 == null) {
            joueur1 = new Joueur(nom, type, p, handicap);
        }
        else if (joueur2 == null) {
            if (p == joueur1.pions()) {
                throw new IllegalArgumentException("Impossible de créer le nouveau joueur : le joueur 1 possède déjà les pions " + p);
            }
            joueur2 = new Joueur(nom, type, p, handicap);
        }
        else {
            throw new IllegalStateException("Impossible d'ajouter un nouveau joueur : tous les joueurs ont déjà été ajoutés");
        }
    }

    public void nouvellePartie() {
        if (joueur1 == null || joueur2 == null) {
            throw new IllegalStateException("Impossible de lancer une nouvelle partie : joueurs manquants");
        }
        // Si une partie est en cours mais n'est pas terminée, on choisit le joueur qui commence la nouvelle aléatoirement
        if (!partieTerminee()) {
            joueurActuel = -1;
        }

        // On choisit le joueur commençant la partie aléatoirement lorsqu'il s'agit de la première
        // Sinon le perdant de la partie précédente commence
        if (joueurActuel == -1) {
            joueurActuel = choixJoueurDebut == -1 ? rand.nextInt(2) : choixJoueurDebut;
        } else if (vainqueur() == joueur1) {
            joueurActuel = 1;
        } else {
            joueurActuel = 0;
        }
        joueur1.initialiserJoueur();
        joueur2.initialiserJoueur();
        plateau = new Plateau();
        partieTerminee = false;
        historique = new Historique();
        tourActuel = historique.nouveauTour(joueurActuel().focus());
        metAJour();
    }

    public Plateau plateau() {
        requireNonNull(plateau, "Impossible de récupérer le plateau : aucune partie créée");
        return plateau;
    }

    public Joueur joueur1() {
        requireNonNull(joueur1, "Impossible de récupérer le joueur 1 : le joueur n'a pas été créé");
        return joueur1;
    }

    public Joueur joueur2() {
        requireNonNull(joueur2, "Impossible de récupérer le joueur 2 : le joueur n'a pas été créé");
        return joueur2;
    }

    public Joueur joueurActuel() {
        if (joueurActuel == 0) {
            return joueur1();
        }
        return joueur2();
    }

    public Joueur joueurSuivant() {
        if (joueurActuel == 0) {
            return joueur2();
        }
        return joueur1();
    }

    public void selectionnerMouvement() {
        prochainCoup = TypeCoup.MOUVEMENT;
        metAJour();
    }

    public void selectionnerPlantation() {
        prochainCoup = TypeCoup.PLANTATION;
        metAJour();
    }

    public void selectionnerRecolte() {
        prochainCoup = TypeCoup.RECOLTE;
        metAJour();
    }

    public void jouer(int l, int c, Epoque e) {
        if (prochaineActionSelectionPion()) {
            selectionnerPion(l, c, e);
        }
        else if (prochaineActionJouerCoup()) {
            Coup coup;

            switch (prochainCoup) {
                case MOUVEMENT:
                    if (tourActuel.deselectionnerPion(l, c, e)) {
                        plateau.resetBrillance();
                        metAJour();
                        return;
                    }
                    coup = new Mouvement(plateau, joueurActuel(), tourActuel.lignePion(), tourActuel.colonnePion(),
                            tourActuel.epoquePion());
                    break;
                case PLANTATION:
                    coup = new Plantation(plateau, joueurActuel(), tourActuel.lignePion(), tourActuel.colonnePion(),
                            tourActuel.epoquePion());
                    break;
                default:
                    coup = new Recolte(plateau, joueurActuel(), tourActuel.lignePion(), tourActuel.colonnePion(),
                            tourActuel.epoquePion());
                    break;
            }
            jouerCoup(coup, l, c, e);
        }
        else {
            changerFocus(e);
            plateau.resetBrillance();
        }
    }

    private void selectionnerPion(int l, int c, Epoque e) {
        if (((joueurActuel().aPionsBlancs() && plateau.aBlanc(l, c, e)) || (joueurActuel().aPionsNoirs() &&
                plateau.aNoir(l, c, e))) && tourActuel.selectionnerPion(l, c, e)) {
            historique.reinitialiserToursSuivants();
            plateau.briller(l, c, e);
            selectionnerMouvement();
        }
    }

    private void jouerCoup(Coup coup, int l, int c, Epoque e) {
        if (tourActuel.jouerCoup(coup, l, c, e)) {
            historique.reinitialiserToursSuivants();
            plateau.briller(l, c, e);
            selectionnerMouvement();
        }
    }

    private void changerFocus(Epoque e) {
        if (!tourActuel.changerFocus(e)) {
            return;
        }
        joueurActuel().fixerFocus(e);
        joueurActuel = (joueurActuel + 1) % 2;

        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2 || plateau.nombrePlateauVide(Pion.NOIR) >= 2) {
            partieTerminee = true;
            ajouterVictoire();
        } else {
            tourActuel = historique.nouveauTour(joueurActuel().focus());
        }
        historique.reinitialiserToursSuivants();
        metAJour();
    }

    private void ajouterVictoire() {
        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2) {
            if (joueur1.pions() == Pion.BLANC) {
                joueur2.ajouterVictoire();
            } else {
                joueur1.ajouterVictoire();
            }
        } else {
            if (joueur1.pions() == Pion.BLANC) {
                joueur1.ajouterVictoire();
            } else {
                joueur2.ajouterVictoire();
            }
        }
    }

    public void annuler() {
        if (!historique.peutAnnuler()) {
            return;
        }
        plateau.resetBrillance();
        if (!tourActuel.pionSelectionne()) {
            tourActuel = historique.tourPrecedent();
            joueurActuel = (joueurActuel + 1) % 2;
            joueurActuel().fixerFocus(tourActuel.focus());
        }
        if (tourActuel.annuler()) {
            selectionnerMouvement();
        }
    }

    public void refaire() {
        if (!historique.peutRefaire()) {
            return;
        }

        if (tourActuel.refaire()) {
            if (tourActuel.termine()) {
                joueurActuel().fixerFocus(tourActuel.prochainFocus());
                joueurActuel = (joueurActuel + 1) % 2;
                tourActuel = historique.tourSuivant();
            }
            selectionnerMouvement();
        }
    }

    public boolean partieTerminee() {
        return partieTerminee;
    }

    public Joueur vainqueur() {
        if (!partieTerminee()) {
            return null;
        }

        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2) {
            return joueur1.pions() == Pion.NOIR ? joueur1 : joueur2;
        } else if (plateau.nombrePlateauVide(Pion.NOIR) >= 2) {
            return joueur1.pions() == Pion.BLANC ? joueur1 : joueur2;
        } else {
            throw new IllegalStateException("Impossible de renvoyer le vainqueur : aucun vainqueur");
        }
    }

    public boolean prochaineActionSelectionPion() {
        return !tourActuel.pionSelectionne() && !prochaineActionChangementFocus();
    }

    public boolean prochaineActionJouerCoup() {
        return tourActuel.pionSelectionne() && !prochaineActionChangementFocus();
    }

    public boolean prochaineActionChangementFocus() {
        int nombrePionPlateau = plateau.nombrePionPlateau(joueurActuel().pions(), joueurActuel().focus());

        return tourActuel.nombreCoupsRestants() == 0 || (nombrePionPlateau == 0 &&
                (!tourActuel.pionSelectionne() || tourActuel.epoquePion() == joueurActuel().focus()));
    }

    public boolean prochainCoupMouvement() {
        return prochainCoup == TypeCoup.MOUVEMENT;
    }

    public boolean prochainCoupPlantation() {
        return prochainCoup == TypeCoup.PLANTATION;
    }

    public boolean prochainCoupRecolte() {
        return prochainCoup == TypeCoup.RECOLTE;
    }

    public void choixJoueurDebut(int numeroJoueur) {
        choixJoueurDebut = numeroJoueur % 2;
    }
}
