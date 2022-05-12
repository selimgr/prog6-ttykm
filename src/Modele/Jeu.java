package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Niveau niveau;
    Joueur joueur1;
    Joueur joueur2;
    int joueurActuel;
    Tour tourActuel;
    boolean partieCommencee;
    boolean partieTerminee;

    public Jeu() {
        this.niveau = new Niveau();
        
    }

    public void nouveauJoueur(String nom, TypeJoueur type) {
        if(this.joueur1 == null){
            this.joueur1 = new Joueur(nom, type) ;
        }else{
            this.joueur2 = new Joueur(nom,type);
        }
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
        return joueur1;
    }

    public Joueur joueur2() {
        return joueur2;
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
