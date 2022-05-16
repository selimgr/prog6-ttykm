package Controleur;

public enum Action {
    MOUVEMENT("Mouvement", 0),
    PLANTATION("Plantation", 1),
    RECOLTE("Récolte", 2);

    private final String nom;
    private final int valeur;

    Action(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    int valeur() {
        return valeur;
    }

    static Action depuisValeur(int valeur) {
        switch (valeur) {
            case 0:
                return MOUVEMENT;
            case 1:
                return PLANTATION;
            case 2:
                return RECOLTE;
            default:
                throw new IllegalArgumentException("Aucune action correspondant à cette valeur");
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
