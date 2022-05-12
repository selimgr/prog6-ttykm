package Modele;

public class Case {
    private final int ligne, colonne;
    private final Epoque epoque;

    Case(int ligne, int colonne, Epoque e) {
        if (Math.min(ligne, colonne) < 0 || Math.max(ligne, colonne) >= Plateau.TAILLE) {
            throw new IllegalArgumentException(
                    "Impossible de créer la case (" + ligne + ", " + colonne + ", " + e + ") : case invalide"
            );
        }
        this.ligne = ligne;
        this.colonne = colonne;
        epoque = e;
    }

    public int ligne() {
        return ligne;
    }

    public int colonne() {
        return colonne;
    }

    public Epoque epoque() {
        return epoque;
    }

    int indiceEpoque() {
        return epoque.indice();
    }

    @Override
    public String toString() {
        return "(" + ligne + ", " + colonne + ", " + epoque + ")";
    }
}
