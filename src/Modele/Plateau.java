package Modele;

public class Plateau {
    final static int TAILLE = 4;
    int[][] contenu;
    final static int VIDE = 0;
    final static int BLANC = 1;
    final static int NOIR = 2;
    final static int GRAINE = 4;
    final static int ARBUSTE = 8;
    final static int ARBRE = 16;
    final static int ARBRE_COUCHE = 32;

    Plateau() {
        this.contenu = new int [TAILLE][TAILLE];
        initialiser(); //tout a 0
        ajouter(0,0,BLANC); //ajout des pions dans les coins
        ajouter(3,3,NOIR); //
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
        this.contenu[l][c] = piece;
    }

    void supprimer(int l, int c /* , int piece*/ ) {   //pas besoin piece
        this.contenu[l][c] = VIDE;
    }

    void deplacer(int l, int c, int dL, int dC) { //TODO: on gere ici les déplacements? pas dans la classe mouvement/coup/tour ?

    }

    void annulerDeplacement() { //TODO : idem
        
    }

    void initialiser(){
        for(int i = 0 ; i < TAILLE ; i++){
            for(int j = 0 ; j < TAILLE ; j++){
                this.contenu[i][j] = VIDE;
            }
        }
    }
}
