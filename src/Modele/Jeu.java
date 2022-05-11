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

    public int[][] plateauPasse() {
        return niveau.getPlateau(Plateau.PASSE.ordinal());
    }

    public int[][] plateauPresent() {
        return niveau.getPlateau(Plateau.PRESENT.ordinal());
    }

    public int[][] plateauFutur() {
        return niveau.getPlateau(Plateau.FUTUR.ordinal());
    }

    public void jouerTour(Case depart, Case arrivee) {
        tourActuel.jouerCoup(depart,arrivee);
        if (tourActuel.changerJoueur()){
            tourActuel.changerTour();
            // TODO : Attendre Vue pour changement de plateau
            //joueurActuel().fixerPlateau();
            joueurActuel = (joueurActuel+1) %2;
        }
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
        if (joueurActuel % 2 == 0){
            return joueur1();
        }
        else {
            return joueur2();
        }
    }

    public Joueur joueurSuivant() {
        if (joueurActuel % 2 == 0){
            return joueur2();
        }
        else {
            return joueur1();
        }
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
