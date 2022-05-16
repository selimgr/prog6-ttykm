package Modele;

public enum Pion {
    BLANC("Blanc", 1),
    NOIR("Noir", 2);

    public static final int NOMBRE_RESERVE = 4;

    private final String nom;
    private final int valeur;

    Pion(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    int valeur() {
        return valeur;
    }

    static Pion depuisValeur(int valeur) {
        switch (valeur) {
            case 1:
                return BLANC;
            case 2:
                return NOIR;
            default:
                throw new IllegalArgumentException("Aucune pièce correspondant à cette valeur");
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
