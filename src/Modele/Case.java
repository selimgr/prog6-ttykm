package Modele;

public class Case {
    private final int ligne, colonne;
    private final Plateau plateau;

    Case(int ligne, int colonne, Plateau p) {
        if (Math.min(ligne, colonne) < 0 || Math.max(ligne, colonne) >= Plateau.TAILLE) {
            throw new IllegalArgumentException(
                    "Impossible de créer la case (" + ligne + ", " + colonne + ", " + p + ") : case invalide"
            );
        }
        this.ligne = ligne;
        this.colonne = colonne;
        plateau = p;
    }

    public int ligne() {
        return ligne;
    }

    public int colonne() {
        return colonne;
    }

    public Plateau plateau() {
        return plateau;
    }

    int indicePlateau() {
        return plateau.indice();
    }

    @Override
    public String toString() {
        return "(" + ligne + ", " + colonne + ", " + plateau + ")";
    }
}
