package Modele;

class Tour {
    Niveau niveau;
    Case pion;
    Coup coup1, coup2;

    Tour(Case depart) {
        this.pion = depart;
    }

    void nouveauCoup(Case nouvelleCase) {
        if(coup1 == null){
            coup1 = new Coup( pion , nouvelleCase);
        }else{
            coup2 = new Coup( pion , nouvelleCase);
        }
    }
}
