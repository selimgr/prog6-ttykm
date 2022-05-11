package Modele;

public class Joueur {
    String nom;
    TypeJoueur type;
    int plateau;
    TypePion pions;
    int nombrePionsReserve;
    int nombreVictoires;

    Joueur(String nom, TypeJoueur type) {
        this.nom = nom;
        this.type = type;
    }

    void fixerPlateau(int plateau) {
        this.plateau = plateau;
    }

    void fixerPions(TypePion pions) {
        this.pions = pions;
    }

    void fixerNombrePionsReserve(int nombrePionsReserve) {
        this.nombrePionsReserve = nombrePionsReserve;
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
        try{
            if(nombrePionsReserve > 0 ){
                nombrePionsReserve--;
            }else{
                System.out.println("Tentative d'enlever un pion ; plus de pions dispo!"); //log message d'erreur?
            }
        }catch(Exception e){
            System.out.println("nombre de pions invalides ! " + e);
        }
    }

    void nouvelleVictoire() {
        try{
            if( this.nombreVictoires >= 0 ){
                this.nombreVictoires++;
            }else{
                System.out.println("nombreVictoires invalide");
            }
        }catch(Exception e){
            System.out.println("nombreVictoires invalide" + e);
        }

    }
}
