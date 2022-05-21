package Modele;

enum Piece {
    BLANC("Blanc", 1),
    NOIR("Noir", 2),
    GRAINE("Graine", 4),
    ARBUSTE("Arbuste", 8),
    ARBRE("Arbre", 16),
    ARBRE_COUCHE_HAUT("Arbre couché vers le haut", 32),
    ARBRE_COUCHE_DROITE("Arbre couché vers la droite", 64),
    ARBRE_COUCHE_BAS("Arbre couché vers le bas", 128),
    ARBRE_COUCHE_GAUCHE("Arbre couché vers la gauche", 256);

    public static final int NOMBRE = values().length;

    private final String nom;
    private final int valeur;

    Piece(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    int valeur() {
        return valeur;
    }

    static Piece depuisValeur(int valeur) {
        switch (valeur) {
            case 1:
                return BLANC;
            case 2:
                return NOIR;
            case 4:
                return GRAINE;
            case 8:
                return ARBUSTE;
            case 16:
                return ARBRE;
            case 32:
                return ARBRE_COUCHE_HAUT;
            case 64:
                return ARBRE_COUCHE_DROITE;
            case 128:
                return ARBRE_COUCHE_BAS;
            case 256:
                return ARBRE_COUCHE_GAUCHE;
            default:
                throw new IllegalArgumentException("Aucune pièce correspondant à la valeur " + valeur);
        }
    }

    Pion toPion() {
        switch (this) {
            case BLANC:
                return Pion.BLANC;
            case NOIR:
                return Pion.NOIR;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
