package Modele;

public class Plateau {
    final static int TAILLE = 4;
    int[][] contenu;
    final static int PASSE = 0;
    final static int PRESENT = 1;
    final static int FUTUR = 2;

    Plateau() {
        contenu = new int[TAILLE][TAILLE];
        //ajout des pions dans les coins
        ajouter(0,0,BLANC);
        ajouter(TAILLE - 1,TAILLE - 1, NOIR);
    }

    int contenu(int l, int c) {
        verifierCaseCorrecte(l, c);
        return contenu[l][c];
    }

    boolean estVide(int l, int c) {
        verifierCaseCorrecte(l, c);
        return contenu[l][c] == VIDE;
    }

    boolean aBlanc(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & BLANC) != 0;
    }

    boolean aNoir(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & NOIR) != 0;
    }

    boolean aPion(int l, int c) {
        return aBlanc(l, c) || aNoir(l, c);
    }

    boolean aGraine(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & GRAINE) != 0;
    }

    boolean aArbuste(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & ARBUSTE) != 0;
    }

    boolean aArbre(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & ARBRE) != 0;
    }

    boolean aArbreCouche(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & ARBRE_COUCHE) != 0;
    }

    boolean estOccupable(int l, int c) {
        return estCaseCorrecte(l, c) && (estVide(l, c) || aGraine(l, c));
    }

    boolean aObstacleMortel(int l, int c) {
        return !estCaseCorrecte(l, c) || aArbuste(l, c) || aArbreCouche(l, c);
    }

    boolean estDeplacementCorrect(int dL, int dC) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dL + dC != 0;
    }

    boolean estJouable(int l, int c, int dL, int dC) {
        if (!estDeplacementCorrect(dL, dC) || !aPion(l, c)) {
            return false;
        }
        int destL = l + dL;
        int destC = c + dC;

        int pieceDepart = contenu(l, c) & (BLANC | NOIR);
        int pieceArrivee = contenu(destL, destC) & (BLANC | NOIR);

        return estOccupable(destL, destC) && pieceDepart != pieceArrivee && !aObstacleMortel(destL, destC);
    }

    void ajouter(int l, int c, int piece) {
        verifierCaseCorrecte(l, c);
        contenu[l][c] |= piece;
    }

    void supprimer(int l, int c, int piece) {
        verifierCaseCorrecte(l, c);
        contenu[l][c] &= ~piece;
    }

    void annulerDeplacement() { // TODO : idem
        
    }

    boolean estCaseCorrecte(int l, int c) {
        return l >= 0 && c >= 0 && l < TAILLE && c < TAILLE;
    }

    void verifierCaseCorrecte(int l, int c) {
        if (!estCaseCorrecte(l, c)) {
            throw new IllegalArgumentException("Case (" + l + ", " + c + ") incorrecte");
        }
    }

//    casesAccessibles(int l, int c)
}
