package Modele;

public class Joueur {
    String nom;
    TypeJoueur type;
    TypePlateau plateau;
    TypePion pions;
    int nombrePionsReserve;
    int nombreVictoires;

    Joueur(String nom, TypeJoueur type) {

    }

    void fixerPlateau(TypePlateau plateau) {

    }

    void fixerPions(TypePion pions) {

    }

    void fixerNombrePionsReserve(int nombrePionsReserve) {

    }

    public String nom() {
        return nom;
    }

    public TypeJoueur type() {
        return type;
    }

    public TypePlateau plateau() {
        return plateau;
    }

    public TypePion pions() {
        return pions;
    }

    int nombrePionsReserve() {
        return nombrePionsReserve;
    }

    int nombreVictoires() {
        return nombreVictoires;
    }

    void enleverPionReserve() {

    }

    void nouvelleVictoire() {

    }
}
