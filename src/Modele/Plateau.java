package Modele;

public class Plateau {
    final static int TAILLE = 4;
    int[][] contenu;
    final static int BLANC = 1;
    final static int NOIR = 2;
    final static int GRAINE = 4;
    final static int ARBUSTE = 8;
    final static int ARBRE = 16;
    final static int ARBRE_COUCHE = 32;

    Plateau() {
    }

    public boolean estVide(int l, int c) {
        return false;
    }

    public boolean aBlanc(int l, int c) {
        return false;
    }

    public boolean aNoir(int l, int c) {
        return false;
    }

    public boolean aGraine(int l, int c) {
        return false;
    }

    public boolean aArbuste(int l, int c) {
        return false;
    }

    public boolean aArbre(int l, int c) {
        return false;
    }

    public boolean aArbreCouche(int l, int c) {
        return false;
    }

    public boolean estOccupable(int l, int c) {
        return false;
    }

    void ajouter(int l, int c, int piece) {

    }

    void supprimer(int l, int c, int piece) {

    }

    void deplacer(int l, int c, int dL, int dC) {

    }

    void annulerDeplacement() {
        
    }
}
