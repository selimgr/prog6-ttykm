package Modele;

public enum Action {
    SELECTION_PION("Sélectionner un pion", 0),
    MOUVEMENT("Mouvement", 1),
    PLANTATION("Planter une graine", 2),
    RECOLTE("Récolter une graine", 3),
    CHANGEMENT_FOCUS("Changer le focus d'époque", 4);

    public static int NOMBRE = values().length;

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
                return SELECTION_PION;
            case 1:
                return MOUVEMENT;
            case 2:
                return PLANTATION;
            case 3:
                return RECOLTE;
            case 4:
                return CHANGEMENT_FOCUS;
            default:
                throw new IllegalArgumentException("Aucune action correspondant à la valeur " + valeur);
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
