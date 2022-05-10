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
        contenu = new int[TAILLE][TAILLE];
        ajouter(0,0,BLANC); //ajout des pions dans les coins
        ajouter(TAILLE - 1,TAILLE - 1, NOIR); //
    }

    int contenu(int l, int c) {
        verifierCaseCorrecte(l, c);
        return contenu[l][c];
    }

    public boolean estVide(int l, int c) {
        verifierCaseCorrecte(l, c);
        return contenu[l][c] == VIDE;
    }

    public boolean aBlanc(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & BLANC) != 0;
    }

    public boolean aNoir(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & NOIR) != 0;
    }

    public boolean aPion(int l, int c) {
        return aBlanc(l, c) || aNoir(l, c);
    }

    public boolean aGraine(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & GRAINE) != 0;
    }

    public boolean aArbuste(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & ARBUSTE) != 0;
    }

    public boolean aArbre(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & ARBRE) != 0;
    }

    public boolean aArbreCouche(int l, int c) {
        verifierCaseCorrecte(l, c);
        return (contenu[l][c] & ARBRE_COUCHE) != 0;
    }

    public boolean estOccupable(int l, int c) {
        return estVide(l, c) || aGraine(l, c);
    }

    void ajouter(int l, int c, int piece) {
        verifierCaseCorrecte(l, c);
        contenu[l][c] |= piece;
    }

    void supprimer(int l, int c, int piece) {
        verifierCaseCorrecte(l, c);
        contenu[l][c] &= ~piece;
    }

    boolean deplacer(int l, int c, int dL, int dC) { // TODO: Gérer le poussage d'arbre
        if (!estDeplacementCorrect(dL, dC)) {
            throw new IllegalArgumentException("Déplacement ("+ dL + ", " + dC + ") incorrect");
        }
        if (!aPion(l, c)) {
                throw new IllegalStateException("Aucun pion dans la case (" + l + ", " + c + ")");
        }
        int destL = l + dL;
        int destC = c + dC;

        int pieceDepart = contenu(l, c) & (BLANC | NOIR);
        int pieceArrivee = contenu(destL, destC) & (BLANC | NOIR);

        if (!estOccupable(destL, destC) || pieceDepart == pieceArrivee) {
            return false;
        }
        pousser(destL, destC, dL, dC);

        supprimer(l, c, pieceDepart);
        ajouter(destL, destC, pieceDepart);
        return true;
    }

    void pousser(int l, int c, int dL, int dC) { // pion poussé, non pousseur
        int destL = l + dL;
        int destC = c + dC;

        int pieceDepart = contenu(l, c) & (BLANC | NOIR);

        if (!estCaseCorrecte(destL, destC) || aArbuste(l, c) || aArbreCouche(l, c)) { // TODO: vérifier les règles
            supprimer(l, c, pieceDepart);
            return;
        }

        int pieceArrivee = contenu(destL, destC) & (BLANC | NOIR);

        if (pieceDepart == pieceArrivee) {
            supprimer(l, c, pieceDepart);
            supprimer(destL, destC, pieceArrivee);
        } else {
            pousser(destL, destC, dL, dC);
        }
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

    boolean estDeplacementCorrect(int dL, int dC) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dL + dC != 0;
    }

//    casesAccessibles(int l, int c)
}
