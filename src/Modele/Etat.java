package Modele;

public class Etat {
    private final int l, c;
    private final Epoque e;
    private final int contenuAvant, contenuApres;

    Etat(int l, int c, Epoque e, int contenuAvant, int contenuApres) {
        this.l = l;
        this.c = c;
        this.e = e;
        this.contenuAvant = contenuAvant;
        this.contenuApres = contenuApres;
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

    int contenuAvant() {
        return contenuAvant;
    }

    int contenuApres() {
        return contenuApres;
    }
}
