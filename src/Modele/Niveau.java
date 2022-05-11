package Modele;

import java.util.Objects;

public class Niveau {
    private int[][][] contenu;

    Niveau() {
        contenu = new int[Plateau.NOMBRE_PLATEAUX][Plateau.TAILLE][Plateau.TAILLE];

        // ajout des pions dans les coins
        ajouter(new Case(0, 0, Plateau.PASSE), Piece.BLANC);
        ajouter(new Case(Plateau.TAILLE - 1, Plateau.TAILLE - 1, Plateau.FUTUR), Piece.NOIR);
    }

    Niveau (Niveau n) { // Utile pour l'IA
        contenu = new int[Plateau.NOMBRE_PLATEAUX][Plateau.TAILLE][Plateau.TAILLE];
        for (int i =0; i < Plateau.NOMBRE_PLATEAUX; i++ )
            for (int j =0; j < Plateau.TAILLE; j++ )
                for (int k =0; k < Plateau.TAILLE; k++ )
                    contenu[i][j][k] = n.getPlateau(i)[j][k];
    }

    int contenu(Case c) {
        Objects.requireNonNull(c);
        return contenu[c.indicePlateau()][c.ligne()][c.colonne()];
    }
    int[][] getPlateau(int epoque){
        return contenu[epoque];
    }

    boolean aPiece(Case c, Piece p) {
        return (contenu(c) & p.valeur()) != 0;
    }

    public boolean estVide(Case c) {
        return contenu(c) == 0;
    }

    public boolean aBlanc(Case c) {
        return aPiece(c, Piece.BLANC);
    }

    public boolean aNoir(Case c) {
        return aPiece(c, Piece.NOIR);
    }

    public boolean aPion(Case c) {
        return aBlanc(c) || aNoir(c);
    }

    public boolean aGraine(Case c) {
        return aPiece(c, Piece.GRAINE);
    }

    public boolean aArbuste(Case c) {
        return aPiece(c, Piece.ARBUSTE);
    }

    public boolean aArbre(Case c) {
        return aPiece(c, Piece.ARBRE);
    }

    public boolean aArbreCouche(Case c) {
        return aPiece(c, Piece.ARBRE_COUCHE);
    }

    public boolean estOccupable(Case c) {
        return estVide(c) || aGraine(c);
    }

    public boolean estDeplacementCorrect(int dL, int dC) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dL + dC != 0;
    }

    public boolean aMur(int l, int c) {
        return Math.min(l, c) < 0 && Math.max(l, c) >= Plateau.TAILLE;
    }

    public boolean aObstacleMortel(Case c, int dL, int dC) {
        Objects.requireNonNull(c);

        if (!estDeplacementCorrect(dL, dC)) {
            throw new IllegalArgumentException("Déplacement incorrect : " + dL + ", " + dC);
        }
        int destL = c.ligne() + dL;
        int destC = c.colonne() + dC;

        if (aArbuste(c) || aArbreCouche(c)) {
            return true;
        }
        if (aMur(destL, destC)) {
            return aArbre(c);
        }
        Case suivant = new Case(destL, destC, c.plateau());

        return aArbre(c) && (aArbuste(suivant) || aArbreCouche(suivant) || aObstacleMortel(suivant, dL, dC));
    }

    public boolean estJouable(Case depart, Case arrivee) {
        Objects.requireNonNull(depart);
        Objects.requireNonNull(arrivee);
        int dL = arrivee.ligne() - depart.ligne();
        int dC = arrivee.colonne() - depart.colonne();

        return estDeplacementCorrect(dL, dC) && aPion(depart) && !aObstacleMortel(arrivee, dL, dC);
    }

    void ajouter(Case c, Piece p) {
        if (aPiece(c, p) || (!estOccupable(c))) {
            throw new IllegalStateException("Impossible d'ajouter la pièce " + p + " sur la case " + c);
        }
        contenu[c.indicePlateau()][c.ligne()][c.colonne()] |= p.valeur();
    }

    void supprimer(Case c, Piece p) {
        if (!aPiece(c, p)) {
            throw new IllegalStateException(
                    "Impossible de supprimer la pièce " + p + " de la case " + c + " : pièce absente"
            );
        }
        contenu[c.indicePlateau()][c.ligne()][c.colonne()] &= ~p.valeur();
    }

    // TODO
    Coup jouerCoup(Case depart, Case arrivee) {
        return null;
    }

    // TODO
    void annulerCoup(Coup coup) {

    }

    // casesAccessibles(int l, int c)
}
