package Controleur;

import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    IA(Jeu jeu,Joueur ia, Joueur adversaire,ControleurMediateur ctrl) {
        //this.jeu = jeu;
        this.ia =ia;
        this.adversaire =adversaire;
        c1=c2=null;
        this.ctrl =ctrl;
    }


    int calcul(Plateau p, int horizon,int minmax) {
        //System.out.println(" --- Calcul IA ---");
        //System.out.println(" --- {" + horizon +"} ---");

        /* --------- Initialisation --------- */
        ArrayList<Coup> C2;
        Joueur j = ctrl.jeu().joueurActuel();
        //System.out.println(j.toString());
        int valeur;

        /* --------- Feuille --------- */
         int isF = isFeuille(horizon,ctrl.jeu().plateau());
         if (isF != 7777777) return isF;

        /* --------- Cas général --------- */
        antiCycle.put(p.hash(),horizon);
        valeur = coup1(j,minmax,horizon);
        //System.out.println("fin ...");
        this.c1 = premierCoup;
        this.c2 = secondCoup;
        focusChangement = coupFocus;
        return valeur;
    }
    public abstract int fonctionApproximation(Plateau p);

    // TODO : Ajouter les bonnes heuristiques : voir document IA
    //public abstract void heuristic();

    private int isFeuille(int horizon,Plateau p){
        if (ctrl.jeu().plateau().nombrePlateauVide(adversaire.pions()) >=2){
            //System.out.println("Configuration gagnante trouvée");
            return 1000;
        }
        if (ctrl.jeu().plateau().nombrePlateauVide(ia.pions()) >=2){
            //System.out.println("Configuration perdante trouvée");
            return -1000;
        }
        if (horizon <= 0 ){
            //System.out.println("Horizon atteint");
            return fonctionApproximation(p);
        }
        // On peux parcourir un plus grand horizon donc ce coup peut devenir plus nul ou plus intéressant
        if ( antiCycle.get(p.hash()) != null && antiCycle.get(p.hash()) >= horizon){
            //System.out.println("Déjà vu     : " + p.hash());
            return -1000;
        }
        return 7777777;
    }

    int coup1(Joueur j,int minmax,int horizon) {
        Plateau p = ctrl.jeu().plateau();
        // Mauvais focus de départ ?
        ArrayList<Coup> C = ctrl.jeu().plateau().casesJouablesEpoque(j, false, 0, 0, null);
        Iterator<Coup> it = C.iterator();
        System.out.println("=========================== "+j.toString()+"==========================");
        int valeur = -1000000000;
        if (!it.hasNext()) choixFocus(null,j,null,null,minmax,horizon);
        while (it.hasNext()) {
            Coup c = it.next();
            System.out.println("it coup 1 : "+c.depart().toString());
            // Selectionner pio*
            System.out.print("JOUER SEL:");ctrl.jouer(c.depart().ligne(), c.depart().colonne(), c.depart().epoque());
            Case arr = c.arrivee();
            if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Premier coup
            List<Case> pions = ctrl.jeu().plateau().chercherPions(j,j.focus());
            System.out.print("JOUER C1 :");ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
            //System.out.println("Coup 1 joué : " + p.hash());
            valeur = coup2(arr, j,c,minmax,horizon);
            System.out.print("Annuler C1 : "); ctrl.annuler(); // Annuler coup 1
            System.out.print("Annuler Selection : "); ctrl.annuler(); // Deselection pion
        }
        C = null;
        return valeur;
    }
    int coup2(Case arr, Joueur j, Coup c,int minmax,int horizon) {
        Plateau p = ctrl.jeu().plateau();
        ArrayList<Coup> C2 = p.casesJouablesEpoque(j, true, arr.ligne(), arr.colonne(), arr.epoque());
        Iterator<Coup> it2 = C2.iterator();
        int valeur = -1000000000;
        if (!it2.hasNext()) choixFocus(null,j,c,null,minmax,horizon);
        while (it2.hasNext()) {
            Coup c2 = it2.next();
            arr = c2.arrivee();
            // Recolte ? Plantation ? Mouvement ?
            if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Second coup
            System.out.print("JOUER C2 :"); ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
            //System.out.println("Coup 2 joué : " + p.hash());
            valeur = choixFocus(arr, j,c,c2,minmax,horizon);
            System.out.print("Annuler C2 :"); ctrl.annuler();
        }

        return valeur;
    }

    int choixFocus(Case arr, Joueur j, Coup c,Coup c2,int minmax,int horizon){
        if (c2 == null) System.out.print("FOCUS COUP MANQUANT --- ");
        Plateau p = ctrl.jeu().plateau();
        Epoque focusJ = j.focus();
        int valeur = -1000000000;
        int valeur2;
        for (int foc = 0; foc < 3; foc++) {
            if (j.focus().indice() != foc){
                System.out.print("JOUER FOC :"); ctrl.jouer(0,0,Epoque.depuisIndice(foc));
                //System.out.println("Coup 3 joué : " + p.hash());
                //System.out.println("Focus Actuel : " + focusJ);
                //System.out.println("Focus changé : " + Epoque.depuisIndice(foc));
                valeur2 = valeur;
                valeur = Math.max(valeur, calcul(ctrl.jeu().plateau(), horizon - 1, minmax * -1) * minmax);
                if (valeur2 < valeur) {
                    // TODO : Ajout d'aléatoire possible dans une certaine mesure
                    premierCoup = c;
                    secondCoup = c2;
                    coupFocus = Epoque.depuisIndice(foc);
                    //System.out.println(((Mouvement) premierCoup).toString() + "  :: " + ((Mouvement) secondCoup).toString() + " approx =" + valeur);
                }
                System.out.print("Annuler Foc :"); ctrl.annuler();
            }
        }
        return valeur;
    }
    void jouer() {
        // Copie d'un plateau pour eviter les modifications
        Plateau p = ctrl.jeu().plateau().copier();
        // Calcul du meilleur coup est stockage dans c1 et c2
        this.calcul(ctrl.jeu().plateau(),horizon,1);
        //DEBUG
        System.out.println();
        //On joue les 2 coups
        //Selection
        System.out.println(ia.toString());
        System.out.println(ctrl.jeu().joueurActuel().toString());
        if (ctrl.jeu().prochaineActionJouerCoup()) {
            System.out.printf("coup1 = %s%n", c1);
            ctrl.jouer(c1.depart().ligne(), c1.depart().colonne(), c1.depart().epoque());
            // Coup 1
            ctrl.jouer(c1.arrivee().ligne(), c1.arrivee().colonne(), c1.arrivee().epoque());
        }
        // Coup 2
        if (ctrl.jeu().prochaineActionJouerCoup()) {
            ctrl.jouer(c2.arrivee().ligne(), c2.arrivee().colonne(), c2.arrivee().epoque());
            System.out.println("coup2 = " + c2.toString());
        }
        //focus
        ctrl.jouer(0,0,focusChangement);
        System.out.println("Epoque changement  " + focusChangement);
        System.out.println(ctrl.jeu().joueurActuel().toString());
    }
}
