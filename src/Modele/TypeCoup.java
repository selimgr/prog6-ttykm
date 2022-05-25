package Modele;

public enum TypeCoup {
    MOUVEMENT("Effectuer un mouvement", 0),
    PLANTATION("Planter une graine", 1),
    RECOLTE("Récolter une graine", 2);

    public static int NOMBRE = values().length;

    private final String nom;
    private final int valeur;

    TypeCoup(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    int valeur() {
        return valeur;
    }

    static TypeCoup depuisValeur(int valeur) {
        switch (valeur) {
            case 0:
                return MOUVEMENT;
            case 1:
                return PLANTATION;
            case 2:
                return RECOLTE;
            default:
                throw new IllegalArgumentException("Aucune action correspondant à la valeur " + valeur);
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
