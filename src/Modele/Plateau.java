package Modele;

public enum Plateau {
    PASSE("Passé", 0),
    PRESENT("Présent", 1),
    FUTUR("Futur", 2);

    public static final int NOMBRE_PLATEAUX = values().length;
    public static final int TAILLE = 4;

    private final String nom;
    private final int indice;

    Plateau(String nom, int indice) {
        this.nom = nom;
        this.indice = indice;
    }

    int indice() {
        return indice;
    }

    @Override
    public String toString() {
        return nom;
    }
}
