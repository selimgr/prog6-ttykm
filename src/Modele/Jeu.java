package Modele;

import Patterns.Observable;

import java.util.Random;

public class Jeu extends Observable {
    Plateau plateau;
    Joueur joueur1;
    Joueur joueur2;
    int joueurActuel;
    int dernierVainqueur;
    Tour tourActuel;
    Random rand;

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

    public void jouerMouvement(int departL, int departC, Epoque eDepart, int arriveeL, int arriveeC, Epoque eArrivee) {
        Coup coup = new Mouvement(plateau, joueurActuel(), departL, departC, eDepart);
        if (tourActuel.jouerCoup(coup, arriveeL, arriveeC, eArrivee)) {
            metAJour();
        }
    }

    public void jouerPlantation(int departL, int departC, Epoque eDepart, int arriveeL, int arriveeC, Epoque eArrivee) {
        Coup coup = new Plantation(plateau, joueurActuel(), departL, departC, eDepart);
        if (tourActuel.jouerCoup(coup, arriveeL, arriveeC, eArrivee)) {
            metAJour();
        }
    }

    public void jouerRecolte(int departL, int departC, Epoque eDepart, int arriveeL, int arriveeC, Epoque eArrivee) {
        Coup coup = new Recolte(plateau, joueurActuel(), departL, departC, eDepart);
        if (tourActuel.jouerCoup(coup, arriveeL, arriveeC, eArrivee)) {
            metAJour();
        }
    }

    public void annulerCoup() {
        tourActuel.annulerCoup();
        metAJour();
    }

    public void changerFocus(Epoque nouveau) {
        if (!tourActuel.estTermine()) {
            throw new IllegalStateException("Impossible de changer le focus : tour non terminé");
        }
        if (nouveau == joueurActuel().focus()) {
            throw new IllegalArgumentException("Impossible de changer le focus : nouveau focus identique au focus actuel");
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
}
