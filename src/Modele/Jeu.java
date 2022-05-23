package Modele;

import Controleur.IA;
import Patterns.Observable;

import java.util.Random;

import static java.util.Objects.requireNonNull;

// TODO: Ajouter des logs quand des actions correctes et incorrectes sont effectuées
// TODO: Prendre en compte le fait que annuler doit pouvoir annuler le changement du focus (gérer dans l'historique)

public class Jeu extends Observable {
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private int joueurActuel;
    private Tour tourActuel;
    private Action prochaineAction;
    private boolean partieTerminee;
    private final Random rand;

    public Jeu() {
        rand = new Random();
        joueurActuel = -1;
        partieTerminee = true;
    }

    public void nouveauJoueur(String nom, TypeJoueur type, Pion p, int handicap) {
        if (joueur1 == null) {
            joueur1 = new Joueur(nom, type, p, handicap);
        } else if (joueur2 == null) {
            if (p == joueur1.pions()) {
                throw new IllegalArgumentException("Impossible de créer le nouveau joueur : le joueur 1 possède déjà les pions " + p);
            }
            joueur2 = new Joueur(nom, type, p, handicap);
        } else {
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
            joueurActuel = rand.nextInt(2);
        } else if (vainqueur() == joueur1) {
            joueurActuel = 1;
        } else {
            joueurActuel = 0;
        }
        joueur1.initialiserJoueur();
        joueur2.initialiserJoueur();
        plateau = new Plateau();
        tourActuel = new Tour();
        partieTerminee = false;
        metAJour();
    }

    private void verifierPartieCree(String message) {
        if (joueurActuel == -1) {
            throw new IllegalStateException(message + " : aucune partie créée");
        }
    }

    private void verifierPartieEnCours(String message) {
        verifierPartieCree(message);

        if (partieTerminee()) {
            throw new IllegalStateException(message + " : partie terminée");
        }
    }

    public Plateau plateau() {
        requireNonNull(plateau, "Impossible de récupérer le plateau : aucune partie créée");
        return plateau;
    }

    public  boolean tourTerminee() {
        return tourActuel.estTermine();
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
        verifierPartieEnCours("Impossible de récupérer le joueur actuel");

        if (joueurActuel == 0) {
            return joueur1();
        }
        return joueur2();
    }

    public Joueur joueurSuivant() {
        verifierPartieEnCours("Impossible de récupérer le joueur suivant");

        if (joueurActuel == 0) {
            return joueur2();
        }
        return joueur1();
    }

    public void selectionnerPlantation() {
        verifierPartieEnCours("Impossible de sélectionner l'action planter une graine");

        if (!pionSelectionne()) {
            return;
        }

        if (prochaineAction == Action.PLANTATION) {
            prochaineAction = Action.MOUVEMENT;
        } else {
            prochaineAction = Action.PLANTATION;
        }
    }

    public void selectionnerRecolte() {
        verifierPartieEnCours("Impossible de sélectionner l'action récolter une graine");

        if (!pionSelectionne()) {
            return;
        }

        if (prochaineAction == Action.RECOLTE) {
            prochaineAction = Action.MOUVEMENT;
        } else {
            prochaineAction = Action.RECOLTE;
        }
    }

    private void selectionnerPion(int l, int c, Epoque e) {
        if (e == joueurActuel().focus() && (
                (joueurActuel().pions() == Pion.BLANC && plateau.aBlanc(l, c, e)) ||
                (joueurActuel().pions() == Pion.NOIR && plateau.aNoir(l, c, e)))) {
            tourActuel.selectionnerPion(l, c, e);
        }
    }

    public void jouer(int l, int c, Epoque e) {
        verifierPartieEnCours("Impossible de jouer");

        if (!tourActuel.pionSelectione() && !tourActuel.estCommence()) {
            selectionnerPion(l, c, e);
            prochaineAction = Action.MOUVEMENT;
        }
        else if (!tourActuel.estTermine()) {
            switch (prochaineAction) {
                case MOUVEMENT:
                    jouerMouvement(l, c, e);
                    break;
                case PLANTATION:
                    jouerPlantation(l, c, e);
                    break;
                case RECOLTE:
                    jouerRecolte(l, c, e);
                    break;
            }
            prochaineAction = Action.MOUVEMENT;
        }
    }

    private void jouerMouvement(int l, int c, Epoque e) {
        if (tourActuel.deselectionnerPion(l, c, e)) {
            metAJour();
            return;
        }
        Coup coup = new Mouvement(
                plateau, joueurActuel(), tourActuel.lignePion(), tourActuel.colonnePion(), tourActuel.epoquePion()
        );
        if (tourActuel.jouerCoup(coup, l, c, e)) {
            metAJour();
        }
    }

    private void jouerPlantation(int l, int c, Epoque e) {
        Coup coup = new Plantation(
                plateau, joueurActuel(), tourActuel.lignePion(), tourActuel.colonnePion(), tourActuel.epoquePion()
        );
        if (tourActuel.jouerCoup(coup, l, c, e)) {
            metAJour();
        }
    }

    private void jouerRecolte(int arriveeL, int arriveeC, Epoque eArrivee) {
        Coup coup = new Recolte(
                plateau, joueurActuel(), tourActuel.lignePion(), tourActuel.colonnePion(), tourActuel.epoquePion()
        );
        if (tourActuel.jouerCoup(coup, arriveeL, arriveeC, eArrivee)) {
            metAJour();
        }
    }

    public void annuler() {
        verifierPartieCree("Impossible d'annuler");

        if (tourActuel.annulerCoup()) {
            metAJour();
        }
    }

    public void focusPasse() {
        verifierPartieEnCours("Impossible de changer le focus");
        int nombrePionPlateau = plateau.nombrePionPlateau(joueurActuel().pions(), joueurActuel().focus());

        if (nombrePionPlateau != 0 && !tourActuel.estTermine()) {
            return;
        }
        changerFocus(Epoque.PASSE);
    }

    public void focusPresent() {
        verifierPartieEnCours("Impossible de changer le focus");
        int nombrePionPlateau = plateau.nombrePionPlateau(joueurActuel().pions(), joueurActuel().focus());

        if (nombrePionPlateau != 0 && !tourActuel.estTermine()) {
            return;
        }
        changerFocus(Epoque.PRESENT);
    }

    public void focusFutur() {
        verifierPartieEnCours("Impossible de changer le focus");
        int nombrePionPlateau = plateau.nombrePionPlateau(joueurActuel().pions(), joueurActuel().focus());

        if (nombrePionPlateau != 0 && !tourActuel.estTermine()) {
            return;
        }
        changerFocus(Epoque.FUTUR);
    }

    private void changerFocus(Epoque nouveau) {
        if (joueurActuel().focus() == nouveau) {
            return;
        }
        joueurActuel().fixerFocus(nouveau);
        joueurActuel = (joueurActuel + 1) % 2;

        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2 || plateau.nombrePlateauVide(Pion.NOIR) >= 2) {
            partieTerminee = true;
            ajouterVictoire();
        } else {
            tourActuel = new Tour();
        }
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

    public boolean pionSelectionne() {
        verifierPartieCree("Impossible de vérifier si le pion est sélectionné");
        return tourActuel.pionSelectione();
    }

    public boolean tourCommence() {
        verifierPartieCree("Impossible de vérifier si le tour est commencé");
        return !tourActuel.estCommence();
    }

    public boolean tourTermine() {
        verifierPartieCree("Impossible de vérifier si le tour est terminé");
        return tourActuel.estTermine();
    }
}
