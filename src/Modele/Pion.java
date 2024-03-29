package Modele;

public enum Pion {
    BLANC("Blanc", 1),
    NOIR("Noir", 2);

    public static final int NOMBRE_MAX_RESERVE = 4;

    private final String nom;
    private final int valeur;

    Pion(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public int valeur() {
        return valeur;
    }

    static Pion depuisValeur(int valeur) {
        switch (valeur) {
            case 1:
                return BLANC;
            case 2:
                return NOIR;
            default:
                throw new IllegalArgumentException("Aucun pion correspondant à la valeur " + valeur);
        }
    }

    Piece toPiece() {
        if (this == BLANC) {
            return Piece.BLANC;
        }
        return Piece.NOIR;
    }

    @Override
    public String toString() {
        return nom;
    }
}
