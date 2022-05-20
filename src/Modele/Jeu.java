package Modele;

import Patterns.Observable;

import java.util.Random;

// TODO: A tester
// TODO: Ajouter des logs quand des actions incorrects sont effectuées

public class Jeu extends Observable {
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private int joueurActuel;
    private int dernierVainqueur;
    private Tour tourActuel;
    private Action prochaineAction;
    private boolean focusSelectionne;
    private final Random rand;

    public Jeu() {
        rand = new Random();
        joueurActuel = -1;
    }

    public void nouveauJoueur(String nom, TypeJoueur type, Pion p, int handicap) {
        if (joueur1 == null) {
            joueur1 = new Joueur(nom, type, p, handicap);
        } else if (joueur2 == null) {
            joueur2 = new Joueur(nom,type, p, handicap);
        } else {
            throw new IllegalStateException("Impossible d'ajouter un nouveau joueur");
        }
    }

    public void nouvellePartie() {
        if (joueur1 == null || joueur2 == null) {
            throw new IllegalStateException("Impossible de lancer une nouvelle partie : joueurs manquants");
        }
        plateau = new Plateau();
        if(joueur1.pions() == Pion.NOIR){
            joueur1.fixerFocus(Epoque.FUTUR);
            joueur2.fixerFocus(Epoque.PASSE);
        }else{
            joueur2.fixerFocus(Epoque.FUTUR);
            joueur1.fixerFocus(Epoque.PASSE);
        }

        if (joueurActuel == -1) {
            joueurActuel = rand.nextInt(2);
        } else {
            joueurActuel = (dernierVainqueur + 1) % 2;
        }
        tourActuel = new Tour();
        metAJour();
    }

    public Plateau plateau() {
        return plateau;
    }

    public Joueur joueur1() {
        return joueur1;
    }

    public Joueur joueur2() {
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

    public void selectionnerPlantation() {
        if (prochaineAction == Action.PLANTATION) {
            prochaineAction = Action.MOUVEMENT;
        }
        prochaineAction = Action.PLANTATION;
    }

    public void selectionnerRecolte() {
        if (prochaineAction == Action.RECOLTE) {
            prochaineAction = Action.MOUVEMENT;
        }
        prochaineAction = Action.RECOLTE;
    }

    public void jouerCoup(int l, int c, Epoque e) {
        if (!tourActuel.pionSelectione() && !tourActuel.estCommence()) {
            tourActuel.selectionnerPion(l, c, e);
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

    private void selectionnerPion(int l, int c, Epoque e) {
        if (e == joueurActuel().focus() && (
                (joueurActuel().pions() == Pion.BLANC && plateau.aBlanc(l, c, e)) ||
                (joueurActuel().pions() == Pion.NOIR && plateau.aNoir(l, c, e)))) {
            tourActuel.selectionnerPion(l, c, e);
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

    public void annulerCoup() {
        if (tourActuel.annulerCoup()) {
            metAJour();
        }
    }

    public void focusPasse() {
        if (joueurActuel().focus() == Epoque.PASSE) {
            focusSelectionne = true;
        } else if (focusSelectionne) {
            changerFocus(Epoque.PASSE);
        }
    }

    public void focusPresent() {
        if (joueurActuel().focus() == Epoque.PRESENT) {
            focusSelectionne = true;
        } else if (focusSelectionne) {
            changerFocus(Epoque.PRESENT);
        }
    }

    public void focusFutur() {
        if (joueurActuel().focus() == Epoque.FUTUR) {
            focusSelectionne = true;
        } else if (focusSelectionne) {
            changerFocus(Epoque.FUTUR);
        }
    }

    private void changerFocus(Epoque nouveau) {
        if (!tourActuel.estTermine()) {
//            Configuration.logger.info("Impossible de changer le focus : tour non terminé");
            return;
        }
        if (nouveau == joueurActuel().focus()) {
//            Configuration.logger.info("Impossible de changer le focus : nouveau focus identique au focus actuel");
            return;
        }
        joueurActuel().fixerFocus(nouveau);
        joueurActuel = (joueurActuel + 1) % 2;
        metAJour();

        if (partieTerminee()) {
            if (vainqueur() == joueur1) {
                dernierVainqueur = 0;
            } else {
                dernierVainqueur = 1;
            }
        } else {
            tourActuel = new Tour();
        }
        focusSelectionne = false;
    }

    public boolean partieTerminee() {
        return vainqueur() != null;
    }

    public Joueur vainqueur() {
        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2) {
            return joueur1.pions() == Pion.NOIR ? joueur1 : joueur2;
        } else if (plateau.nombrePlateauVide(Pion.NOIR) >= 2) {
            return joueur1.pions() == Pion.BLANC ? joueur1 : joueur2;
        } else {
            return null;
        }
    }

    public boolean pionSelectionne() {
        return tourActuel.pionSelectione();
    }

    public boolean tourCommence() {
        return tourActuel.estCommence();
    }

    public boolean tourTermine() {
        return tourActuel.estTermine();
    }
}
