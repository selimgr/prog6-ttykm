package Modele;



public class Joueur {
    String nom;
    TypeJoueur type;
    int plateauFocus; // 0=passé 1=present 2=futur
    TypePion pions;
    int nombrePionsReserve;
    int nombreVictoires;


    Joueur(String nom, TypeJoueur type, TypePion pions, int handicap) {
        this.nom = nom;
        this.type = type;

        fixerPions(pions);
        if(pions == TypePion.BLANC){
            fixerPlateauFocus(0); //blanc commence dans le passé
        }else{
            fixerPlateauFocus(2); //noir commence dans le futur
        }

        fixerNombrePionsReserve(handicap);
    }

    void fixerPlateauFocus(int plateau) {
        this.plateauFocus = plateau;
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
        return plateauFocus;
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
