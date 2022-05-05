package Modele;

import Global.Observable;

public class Jeu extends Observable {
    Niveau niveau;
    Joueur joueur1;
    Joueur joueur2;
    int joueurActuel;
    Tour tourActuel;
    boolean partieCommencee;
    boolean partieTerminee;

    public Jeu() {

    }

    public void nouveauJoueur(String nom, TypeJoueur type) {

    }

    public void nouvellePartie() {

    }

    public Plateau plateauPasse() {
        return null;
    }

    public Plateau plateauPresent() {
        return null;
    }

    public Plateau plateauFutur() {
        return null;
    }

    public void jouerCoup() {

    }

    public void annulerCoup() {

    }

    public Joueur joueur1() {
        return null;
    }

    public Joueur joueur2() {
        return null;
    }

    public Joueur joueurActuel() {
        return null;
    }

    public Joueur joueurSuivant() {
        return null;
    }

    public boolean partieTerminee() {
        return false;
    }

    public Joueur vainqueur() {
        return null;
    }

    private void tourSuivant() {

    }
}
