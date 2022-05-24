package Controleur;

import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class IA {
    Jeu jeu;
    Joueur ia;
    Joueur adversaire;
    //List<Coup> coups;
    HashMap<String,Integer> antiCycle;
    int horizon;
    ControleurMediateur ctrl;
    int[] alphaBeta;
    Coup c1;
    Coup c2;
    Epoque focusChangement;

    IA(Jeu jeu,Joueur ia, Joueur adversaire,ControleurMediateur ctrl) {
        this.jeu = jeu;
        this.ia =ia;
        this.adversaire =adversaire;
        c1=c2=null;
        this.ctrl =ctrl;
    }


    int calcul(Plateau p, int horizon,int minmax) {
        System.out.println(" --- Calcul IA ---");
        /* --------- Initialisation --------- */
        ArrayList<Coup> C2;
        Plateau copieP = p.copier();
        Plateau copie2;
        Plateau copie3;
        Joueur j = adversaire; if (minmax ==1){j = ia;}
        Coup premierCoup = null;
        Coup secondCoup = null;
        Epoque troisiemeCoup = null;
        int valeur = -100000*minmax;
        int valeur2,lA,cA;
        Case arr, dep;
        Epoque eA;
        ArrayList<Coup> C = p.casesJouablesEpoque(ia,false,0,0,null);
        Iterator<Coup> it = C.iterator();

        /* --------- Feuille --------- */
         int isF = isFeuille(horizon,copieP);
         if (isF != -1) return isF;

        /* --------- Cas général --------- */
        antiCycle.put(p.hash(),horizon);
        //Parcours de l'arbre ...
        while (it.hasNext()){
            Coup c = it.next();
            jeu.plateau().fixerPlateau(copieP);
            // Selectionner pion
            ctrl.jouer(c.depart().ligne(),c.depart().colonne(),c.depart().epoque());
            arr = c.arrivee();
            if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Premier coup
            ctrl.jouer(arr.ligne(),arr.colonne(),arr.epoque());
            C2 = p.casesJouablesEpoque(j,true,arr.ligne(),arr.colonne(),arr.epoque());
            Iterator<Coup> it2 = C2.iterator();
            copie2 = copieP.copier();
            while (it2.hasNext()) {
                jeu.plateau().fixerPlateau(copie2);
                Coup c2 = it2.next();
                arr = c2.arrivee();
                // Recolte ? Plantation ? Mouvement ?
                if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
                if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
                // Second coup
                ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
                copie3 = copie2.copier();
                for (int foc = 0; foc < 3; foc++) {
                    if (j.focus().indice() != foc){
                    jeu.plateau().fixerPlateau(copie3);
                    ctrl.jouer(0,0,Epoque.depuisIndice(foc));
                    System.out.println("Computing ...");
                    valeur2 = valeur;
                    valeur = Math.max(valeur * minmax, calcul(copieP, horizon - 1, minmax * -1) * minmax);
                    if (valeur2 < valeur || horizon == this.horizon && valeur2 == -10000) {
                        // TODO : Ajout d'aléatoire possible dans une certaine mesure
                        premierCoup = c;
                        secondCoup = c2;
                        troisiemeCoup = Epoque.depuisIndice(foc);
                        System.out.println(((Mouvement) premierCoup).toString() + "  :: " + ((Mouvement) secondCoup).toString() + " approx =" + valeur);
                        }
                    }
                }
            }
        }
        System.out.println("fin ...");
        this.c1 = premierCoup;
        this.c2 = secondCoup;
        focusChangement = troisiemeCoup;
        return valeur;
    }
    public abstract int fonctionApproximation(Plateau p);

    // TODO : Ajouter les bonnes heuristiques : voir document IA
    //public abstract void heuristic();

    private int isFeuille(int horizon,Plateau p){
        if (jeu.vainqueur() == ia){
            System.out.println("Configuration gagnante trouvée");
            return 1000;
        }
        if (jeu.vainqueur() == adversaire){
            System.out.println("Configuration perdante trouvée");
            return -1000;
        }
        if (horizon <= 0 ){
            System.out.println("Horizon atteint");
            return fonctionApproximation(p);
        }
        // On peux parcourir un plus grand horizon donc ce coup peut devenir plus nul ou plus intéressant
        if ( antiCycle.get(p.hash()) != null && antiCycle.get(p.hash()) >= horizon){
            System.out.println("Déjà vu");
            return 0;
        }
        return -1;
    }
    void jouer() {
        // Copie d'un plateau pour eviter les modifications
        Plateau p = jeu.plateau().copier();
        // Calcul du meilleur coup est stockage dans c1 et c2
        this.calcul(this.jeu.plateau(),horizon,1);
        // On remet le plateau tel qu'il était ( assurance d'un plateau identique)
        jeu.plateau().fixerPlateau(p);
        //DEBUG
        System.out.println("coup1 = " + this.c1.toString());
        System.out.println("coup2 = " + this.c2.toString());
        // Calcul du déplacement de c2
        int lA = c2.arrivee().ligne();
        int cA = c2.arrivee().colonne();
        Epoque eA = c2.arrivee().epoque();
        //On joue les 2 coups
        //Selection
        ctrl.jouer(c1.depart().ligne(), c1.depart().colonne(),c1.depart().epoque());
        // Coup 1
        ctrl.jouer(this.c2.depart().ligne(), this.c2.depart().colonne(), this.c2.depart().epoque());
        // Coup 2
        ctrl.jouer(lA,cA,eA);
        //TODO : Gestion du focus
        ctrl.jouer(0,0,focusChangement);
    }



}
