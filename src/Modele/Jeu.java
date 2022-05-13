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

    public void nouveauJoueur(String nom, TypeJoueur type, TypePion pions, int handicap) {
        if(this.joueur1 == null){
            this.joueur1 = new Joueur(nom, type, pions , handicap) ;
        }else{
            this.joueur2 = new Joueur(nom,type, pions , handicap);
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
            //joueurActuel().fixerPlateauFocus();
            if(partieTerminee(joueurActuel().pions())){
                // TODO : Gérer la fin de partie;
            }
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

    public boolean partieTerminee(TypePion pions) {
        int[] pla = {0, 0, 0};
        int i, j, k;
        i = j = k = 0;
        switch (pions) {
            case BLANC:
                while (i < Plateau.NOMBRE_PLATEAUX && (pla[0] + pla[1] + pla[2]) < 2) {
                    while (j < Plateau.TAILLE && pla[i] <= 0) {
                        while (k < Plateau.TAILLE && pla[i] <= 0) {
                            if (niveau.aNoir(new Case(j, k, i))) {//TODO : Modifier pour l'implem finale de case
                                pla[i] = 1;
                            }
                            k++;
                        }
                        j++;
                    }
                    i++;
                }
                break;
            case NOIR:
                while (i < Plateau.NOMBRE_PLATEAUX && (pla[0] + pla[1] + pla[2]) < 2) {
                    while (j < Plateau.TAILLE && pla[i] <= 0) {
                        while (k < Plateau.TAILLE && pla[i] <= 0) {
                            if (niveau.aBlanc(new Case(j, j, i))) { //TODO : Modifier pour l'implem finale de case
                                pla[i] = 1;
                            }
                            k++;
                        }
                        j++;
                    }
                    i++;
                }
                break;
        }
        return (pla[0] + pla[1] + pla[2]) < 2;
    }

    public Joueur vainqueur() {
        return joueurActuel();
    }

    private void tourSuivant() {
        //inutile gérer dans tour et jeu.jouerTour;
    }
}
