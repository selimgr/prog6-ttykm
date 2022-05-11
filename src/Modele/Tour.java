package Modele;

class Tour {
    Niveau niveau;
    Case pion;
    int nbCoups;

    Tour(Niveau niv) {
        niveau = niv;
        nbCoups =0;
    }

   /* void nouveauCoup(Case nouvelleCase) {
        if(coup1 == null){
            coup1 = new Coup( pion , nouvelleCase);
        }else{
            coup2 = new Coup( pion , nouvelleCase);
        }
    }*/

    void jouerCoup(Case depart, Case arrivee){
        if (niveau.estJouable(depart,arrivee)){
            niveau.jouerCoup(depart, arrivee);
            nbCoups++;
        }
    }
    boolean changerJoueur(){
        return nbCoups>=2;
    }
    void changerTour(){
        nbCoups=0;
    }




}
