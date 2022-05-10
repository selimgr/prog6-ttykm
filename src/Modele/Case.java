package Modele;

public class Case {
    private final int l, c;
    private final int typePlateau;
    private final int typeCase;
    final static int VIDE = 0;
    final static int BLANC = 1;
    final static int NOIR = 2;
    final static int GRAINE = 4;
    final static int ARBUSTE = 8;
    final static int ARBRE = 16;
    final static int ARBRE_COUCHE = 32;

    Case(int l, int c, int typePlateau, int typeCase) {
        this.l = l;
        this.c = c;
        if (typePlateau == Plateau.PASSE || typePlateau == Plateau.PRESENT || typePlateau == Plateau.FUTUR) {
            this.typePlateau = typePlateau;
        } else {
            throw new IllegalArgumentException("Impossible de créer la case : type de plateau " + typePlateau + " inconnu");
        }
    }

    public int ligne() {
        return l;
    }

    public int colonne() {
        return c;
    }

    public int typePlateau() {
        return typePlateau;
    }

    @Override
    public String toString() {
        return "(" + l + ", " + c + ")";
    }
}
