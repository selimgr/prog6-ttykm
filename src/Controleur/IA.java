package Controleur;

import Global.Configuration;
import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class IA {
    Joueur ia;
    Joueur adversaire;
    //List<Coup> coups;
    HashMap<String,Integer> antiCycle;
    int horizon;
    ControleurMediateur ctrl;
    int[] alphaBeta;
    Coup c1,c2,premierCoup,secondCoup;
    Epoque focusChangement,coupFocus;
    int minmax;
    boolean nouveauTour;

    IA(Jeu jeu,Joueur ia, Joueur adversaire,ControleurMediateur ctrl) {
        this.ia =ia;
        this.adversaire =adversaire;
        c1=c2=null;
        this.ctrl =ctrl;
        minmax = 1;
        nouveauTour = true;
    }


    int calcul(Plateau p, int horizon,int minmax) {
        this.minmax = minmax;
        /* --------- Initialisation --------- */
        Joueur j = ctrl.jeu().joueurActuel();
        int valeur;

        /* --------- Feuille --------- */
        int isF = isFeuille(horizon,ctrl.jeu().plateau());
        if (isF != 7777777) return isF;

        /* --------- Cas général --------- */
        //antiCycle.put(p.hash(),horizon);
        valeur = coup1(j,minmax,horizon);
        c1 = premierCoup;
        c2 = secondCoup;
        focusChangement = coupFocus;
        return valeur;
    }
    public abstract int fonctionApproximation(Plateau p);

    // TODO : Ajouter les bonnes heuristiques : voir document IA
    //public abstract void heuristic();

    int isFeuille(int horizon,Plateau p){
        if (ctrl.jeu().plateau().nombrePlateauVide(adversaire.pions()) >=2){
            int r = 1;
            if (ctrl.jeu().joueurActuel() == ia) r =-1;
            return 1000*r;
        }
        if (ctrl.jeu().plateau().nombrePlateauVide(ia.pions()) >=2){
            int r = -1;
            if (ctrl.jeu().joueurActuel() == ia) r = 1;
            return -1000*r;
        }
        if (horizon <= 0 ){
            return fonctionApproximation(p);
        }
        // On peux parcourir un plus grand horizon donc ce coup peut devenir plus nul ou plus intéressant
        if ( antiCycle.get(p.hash()) != null && antiCycle.get(p.hash()) >= horizon){
            return fonctionApproximation(p);
        }
        return 7777777;
    }

    int coup1(Joueur j,int minmax,int horizon) {
        ArrayList<Coup> C = ctrl.jeu().plateau().casesJouablesEpoque(j, false, 0, 0, null);
        Iterator<Coup> it = C.iterator();
        int valeur = -1000000000*minmax;
        if (!it.hasNext()) choixFocus(null,j,null,null,valeur,horizon);
        while (it.hasNext()) {
            Coup c = it.next();
            // Selectionner pion
            //System.out.print("JOUER SEL:");
            ctrl.jouer(c.depart().ligne(), c.depart().colonne(), c.depart().epoque());
            //System.out.print("it coup 1 : " + c.depart().toString() + " -> ");
            // Le pion ne se suicide pas
            //System.out.println(c.arrivee().toString());
            Case arr = c.arrivee();
            //if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            //if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Premier coup
            //System.out.print("JOUER C1 :");
            ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
            //System.out.println("Coup 1 joué : " + ctrl.jeu().plateau().hash());
            valeur = coup2(arr, j,c,valeur,horizon);
            //System.out.print("Annuler C1 ? : ");
            ctrl.annuler(); // Annuler coup 1
            //System.out.print("Annuler Selection ? : ");
            ctrl.annuler(); // Deselection pion
            //System.out.println("Apres tour :\n" +ctrl.jeu().plateau().hash2());

        }
        return valeur;
    }
    int coup2(Case arr, Joueur j, Coup c,int valeur,int horizon) {
        Plateau p = ctrl.jeu().plateau();
        ArrayList<Coup> C2 = p.casesJouablesEpoque(j, true, arr.ligne(), arr.colonne(), arr.epoque());
        Iterator<Coup> it2 = C2.iterator();
        if (!it2.hasNext()) choixFocus(arr,j,c,null,valeur,horizon);
        while (it2.hasNext()) {
            Coup c2 = it2.next();
            arr = c2.arrivee();
            //System.out.print("it coup 2 : " + c.depart().toString() + " -> ");
            //System.out.println(c2.arrivee().toString());
            // Recolte ? Plantation ? Mouvement ?
            //if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            //if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Second coup
            Configuration.instance().logger().info("JOUER C2 :");
            ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
            valeur = choixFocus(arr, j, c, c2, valeur, horizon);
            Configuration.instance().logger().info("Annuler C2 ? :");
            ctrl.annuler();
        }
        return valeur;
    }

    int choixFocus(Case arr, Joueur j, Coup c,Coup c2,int valeur,int horizon){
        //if (c2 == null) System.out.println("FOCUS COUP MANQUANT ---\n" +ctrl.jeu().plateau().hash2());
        int valeur2;
        for (int foc = 0; foc < 3; foc++) {
            if (j.focus().indice() != foc){
                //System.out.print("JOUER FOC :");
                Epoque prec = ctrl.jeu().joueurActuel().focus();
                ctrl.jeu().joueurActuel().fixerFocus(Epoque.depuisIndice(foc));
                valeur2 = valeur;
                if (j == ia         ) valeur = Math.max(valeur, calcul(ctrl.jeu().plateau(), horizon - 1, minmax * -1));
                if (j == adversaire ) valeur = Math.min(valeur, calcul(ctrl.jeu().plateau(), horizon - 1, minmax * -1));
                if (horizon == this.horizon && valeur2 < valeur ) {
                    isFeuille(horizon,ctrl.jeu().plateau());
                    // TODO : Ajout d'aléatoire possible dans une certaine mesure
                    premierCoup = c;
                    secondCoup = c2;
                    coupFocus = Epoque.depuisIndice(foc);
                }
                //System.out.print("Annuler Foc ? :");
                ctrl.jeu().joueurActuel().fixerFocus(prec);
            }
        }
        return valeur;
    }

    void jouer() {
        // Calcul du meilleur coup et stockage dans c1 et c2
        if (nouveauTour) {
            this.calcul(ctrl.jeu().plateau(), horizon, 1);
            nouveauTour = false;
        }
        if (ctrl.jeu().prochaineActionSelectionPion()) {
            //Selection
            ctrl.jouer(c1.depart().ligne(), c1.depart().colonne(), c1.depart().epoque());
        }
        else if (ctrl.jeu().prochaineActionJouerCoup() && ctrl.jeu().nombreCoupsRestantsTour() == 2) {
            // Coup 1
            ctrl.jouer(c1.arrivee().ligne(), c1.arrivee().colonne(), c1.arrivee().epoque());
            // Coup 2
        } else if (ctrl.jeu().prochaineActionJouerCoup()) {
            ctrl.jouer(c2.arrivee().ligne(), c2.arrivee().colonne(), c2.arrivee().epoque());
        }
        //focus
        else if (ctrl.jeu().prochaineActionChangementFocus()) {
            ctrl.jouer(0, 0, focusChangement);
            nouveauTour = true;
        }
    }
}
