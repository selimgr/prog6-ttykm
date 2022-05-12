package Modele;

class Tour {
    Plateau plateau;
    Case pion;
    int nbCoups;

    Tour(Plateau pla) {
        plateau = pla;
        nbCoups =0;
    }

   /* void nouveauCoup(Case nouvelleCase) {
        if(coup1 == null){
            coup1 = new Coup( pion , nouvelleCase);
        }else{
            coup2 = new Coup( pion , nouvelleCase);
        }
    }*/

    void jouerCoup(int lDepart,int cDepart,int lArrivee, int cArrivee, Epoque eDep, Epoque eArr){
        Coup c = plateau.creerChangementPlateau(lDepart,cDepart,eDep,eArr);
        if ( c != null) {
            plateau.jouerCoup(c);
            nbCoups ++;
        }
        else {
            c = plateau.creerDeplacement(lDepart,cDepart,lArrivee,cArrivee,eDep);
            if ( c != null) {
                plateau.jouerCoup(c);
                nbCoups ++;
            }
        }
    }
    boolean changerJoueur(){
        return nbCoups>=2;
    }
    void changerTour(){
        nbCoups=0;
    }




}
