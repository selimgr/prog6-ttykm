package Modele;

public class Etat {
    private final int l, c;
    private final Epoque e;
    private final Piece avant, apres;

    Etat(int l, int c, Epoque e, Piece avant, Piece apres) {
        Plateau.verifierCoordoneesCorrectes(l, c, e);
        this.l = l;
        this.c = c;
        this.e = e;
        this.avant = avant;
        this.apres = apres;
    }

    int ligne() {
        return l;
    }

    int colonne() {
        return c;
    }

    Epoque epoque() {
        return e;
    }

    Piece pieceAvant() {
        return avant;
    }

    Piece pieceApres() {
        return apres;
    }
}
