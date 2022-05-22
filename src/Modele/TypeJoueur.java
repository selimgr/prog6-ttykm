package Modele;

public enum TypeJoueur {
    HUMAIN("Humain", 0),
    IA_FACILE("IA Facile", 1),
    IA_MOYEN("IA Moyen", 2),
    IA_DIFFICILE("IA Difficile", 3);

    public static final int NOMBRE = values().length;

    private final String nom;
    private final int indice;

    TypeJoueur(String nom, int indice) {
        this.nom = nom;
        this.indice = indice;
    }

    int indice() {
        return indice;
    }

    static TypeJoueur depuisIndice(int indice) {
        switch (indice) {
            case 0:
                return HUMAIN;
            case 1:
                return IA_FACILE;
            case 2:
                return IA_MOYEN;
            case 3:
                return IA_DIFFICILE;
            default:
                throw new IllegalArgumentException("Aucun type de joueur correspondant à l'indice " + indice);
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
