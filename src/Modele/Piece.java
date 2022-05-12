package Modele;

enum Piece {
    BLANC("Blanc", 1),
    NOIR("Noir", 2),
    GRAINE("Graine", 4),
    ARBUSTE("Arbuste", 8),
    ARBRE("Arbre", 16),
    ARBRE_COUCHE("Arbre couché", 32);

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
                return ARBRE_COUCHE;
            default:
                throw new IllegalArgumentException("Aucune pièce correspondant à cette valeur");
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
