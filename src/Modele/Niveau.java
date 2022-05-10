package Modele;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;

class Niveau {
    final static int NOMBRE_PLATEAUX = 3;
    final static int PASSE =0 ;
    final static int PRESENT = 1;
    final static int FUTUR = 1;
    Plateau[] plateaux;

    Niveau() {

    }

    Coup jouerCoup(Case depart, Case arrivee, Niveau niveau) {
        return null;
    }

    void annulerCoup(Coup coup) {

    }

    Plateau plateau(TypePlateau p) {
        return plateaux[p.ordinal()];
    }

    List<Coup> addCoups(int i, int j, TypePlateau p,Joueur J ,Plateau pla, List<Coup> Coups) {
        Case dep = new Case(i,j,p,pla.contenu(i,j));
        // BAS
        Case arr1 = new Case(i+1,j,p,pla.contenu(i+1,j));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr1));
        //HAUT
        Case arr2 = new Case(i-1,j,p,pla.contenu(i-1,j));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr1));
        //DROITE
        Case arr3 = new Case(i,j+1,p,pla.contenu(i,j+1));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr3));
        //GAUCHE
        Case arr4 = new Case(i,j-1,p,pla.contenu(i,j-1));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
        //PASSE
        Case arr5 = new Case(i,j,TypePlateau.PASSE,plateaux[PASSE].contenu(i,j));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
        //PRESENT
        Case arr6 = new Case(i,j,TypePlateau.PRESENT,plateaux[PRESENT].contenu(i,j));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
        //FUTUR
        Case arr7 = new Case(i,j,TypePlateau.FUTUR,plateaux[FUTUR].contenu(i,j));
        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));

        return Coups;
    }
    List<Coup> coupsLegaux(Joueur J, TypePlateau p) {
        Plateau pla = this.plateau(p);
        List<Coup> Coups = new ArrayList<>();
        switch (J.pions()) {
            case BLANC:
                for(int i =0; i < 4; i++){
                    for (int j = 0; j< 4; j++){
                        if (pla.aBlanc(i,j)) {
                            Coups = addCoups(i,j,p,J,pla,Coups);
                        }
                    }
                }
                break;
            case NOIR:
                for(int i =0; i < 4; i++){
                    for (int j = 0; j< 4; j++){
                        if (pla.aNoir(i,j)) {
                            Coups = addCoups(i,j,p,J,pla,Coups);
                        }
                    }
                }
                break;
            }
        return Coups;
    }

    boolean estCoupLegal(Case depart, Case arrivee,Joueur J){
        Plateau pArr = this.plateau(arrivee.plateau());
        Plateau pDep = this.plateau(depart.plateau());
        if (pDep.estCaseCorrecte(depart.ligne(),depart.colonne()) && pArr.estCaseCorrecte(depart.ligne(),depart.colonne())){
            // Déplacements dans le même plateau
            if (arrivee.plateau().ordinal() == depart.plateau().ordinal()){
                return pDep.verifierDeplacementCorrect(depart.ligne() + (arrivee.ligne() -depart.ligne()),
                        depart.colonne() + (arrivee.colonne() -depart.colonne()));
            } else { // Changement de plateau
                //Passé
                if (arrivee.plateau().ordinal() - depart.plateau().ordinal() == -1){
                    if (arrivee.contenu() == 0 && J.nombrePionsReserve() > 0) return true;
                    return false;
                }
                //Futur
                if (arrivee.plateau().ordinal() - depart.plateau().ordinal() == 1){
                    if (arrivee.contenu() == 0) return true;
                    return false;
                }
            }
        }
        return false;
    }
}
/**
 * boolean verifierCaseCorrecte(int l, int c)
 * boolean verifierDeplacementCorrect(int dL, int dC)
 */