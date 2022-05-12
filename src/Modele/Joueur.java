package Modele;

public class Joueur {
    private final String nom;
    private TypeJoueur type;
    private int plateau;
    private TypePion pions;
    private int nombrePionsReserve;
    private int nombreVictoires;

    Joueur(String nom, TypeJoueur type) {
        this.nom = nom;
        this.type = type;
        nombrePionsReserve = 4;
    }

    public String nom() {
        return nom;
    }

    public TypeJoueur type() {
        return type;
    }

    public int plateau() {
        return plateau;
    }

    void fixerPlateau(int plateau) {
        this.plateau = plateau;
    }

    public TypePion pions() {
        return pions;
    }

    void fixerPions(TypePion pions) {
        this.pions = pions;
    }

    int nombrePionsReserve() {
        return nombrePionsReserve;
    }

    void ajouterPionReserve() {
        nombrePionsReserve++;
    }

    void enleverPionReserve() {
        nombrePionsReserve--;
    }

    int nombreVictoires() {
        return nombreVictoires;
    }

    void ajouterVictoire() {
        nombreVictoires++;
    }

    void enleverVictoire() {
        nombreVictoires--;
    }
}
