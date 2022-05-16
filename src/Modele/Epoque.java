package Modele;

public enum Epoque {
    PASSE("Passé", 0),
    PRESENT("Présent", 1),
    FUTUR("Futur", 2);

    public static final int NOMBRE = values().length;

    private final String nom;
    private final int indice;

    Epoque(String nom, int indice) {
        this.nom = nom;
        this.indice = indice;
    }

    int indice() {
        return indice;
    }

    public static Epoque depuisIndice(int indice) {
        switch (indice) {
            case 0:
                return PASSE;
            case 1:
                return PRESENT;
            case 2:
                return FUTUR;
            default:
                throw new IllegalArgumentException("Aucune époque correspondant à cet indice");
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
