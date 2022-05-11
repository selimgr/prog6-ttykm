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

    @Override
    public String toString() {
        return nom;
    }
}
