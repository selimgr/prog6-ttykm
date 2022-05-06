package Modele;

import Global.Observable;

public class Jeu extends Observable {
    Niveau niveau;
    Joueur joueur1;
    Joueur joueur2;
    int joueurActuel; // 1 ou 2 ?
    Tour tourActuel;
    boolean partieCommencee;
    boolean partieTerminee;

    public Jeu( String nom1, TypeJoueur t1, String nom2, TypeJoueur t2, int joueurActuel, Tour tourActuel) {
        this.niveau = new Niveau();
        this.joueurActuel = joueurActuel;
        this.tourActuel = tourActuel;
        this.partieCommencee = true;
        this.partieTerminee = false;
        nouveauJoueur( nom1 , t1 );
        nouveauJoueur( nom2 , t2 );
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
        return this.joueur1;
    }

    public Joueur joueur2() {
        return this.joueur2;
    }

    public Joueur joueurActuel() {
        if(joueurActuel==1){
            return joueur1;
        }else{
            return joueur2;
        }
    }

    public Joueur joueurSuivant() {
        if(joueurActuel()==joueur1){
            return joueur2;
        }else{
            return joueur1;
        }
    }

    public boolean partieTerminee() {
        return partieTerminee;
    }

    public Joueur vainqueur() {
        return null;
    }

    private void tourSuivant() {

    }
}
