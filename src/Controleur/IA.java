package Controleur;

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

    IA(Jeu jeu,Joueur ia, Joueur adversaire,ControleurMediateur ctrl) {
        //this.jeu = jeu;
        this.ia =ia;
        this.adversaire =adversaire;
        c1=c2=null;
        this.ctrl =ctrl;
    }


    int calcul(Plateau p, int horizon,int minmax) {
        System.out.println(" --- Calcul IA ---");
        System.out.println(" --- " + (this.horizon - horizon) +" ---");

        /* --------- Initialisation --------- */
        ArrayList<Coup> C2;
        Joueur j = adversaire; if (minmax ==1){j = ia;}
        int valeur;

        /* --------- Feuille --------- */
         int isF = isFeuille(horizon,ctrl.jeu().plateau());
         if (isF != -1) return isF;

        /* --------- Cas général --------- */
        antiCycle.put(p.hash(),horizon);
        valeur = coup1(j,minmax,horizon);
        System.out.println("fin ...");
        this.c1 = premierCoup;
        this.c2 = secondCoup;
        focusChangement = coupFocus;
        return valeur;
    }
    public abstract int fonctionApproximation(Plateau p);

    // TODO : Ajouter les bonnes heuristiques : voir document IA
    //public abstract void heuristic();

    private int isFeuille(int horizon,Plateau p){
        if (ctrl.jeu().vainqueur() == ia){
            System.out.println("Configuration gagnante trouvée");
            return 1000;
        }
        if (ctrl.jeu().vainqueur() == adversaire){
            System.out.println("Configuration perdante trouvée");
            return -1000;
        }
        if (horizon <= 0 ){
            System.out.println("Horizon atteint");
            return fonctionApproximation(p);
        }
        // On peux parcourir un plus grand horizon donc ce coup peut devenir plus nul ou plus intéressant
        if ( antiCycle.get(p.hash()) != null && antiCycle.get(p.hash()) >= horizon){
            System.out.println("Déjà vu     : " + p.hash());
            return 0;
        }
        return -1;
    }

    int coup1(Joueur j,int minmax,int horizon) {
        Plateau p = ctrl.jeu().plateau();
        ArrayList<Coup> C = p.casesJouablesEpoque(ia, false, 0, 0, null);
        Iterator<Coup> it = C.iterator();
        int valeur = -1000000000;
        while (it.hasNext()) {
            Coup c = it.next();
            // Selectionner pion
            if (!ctrl.jeu().prochaineActionSelectionPion())
                System.out.println("hdsj  " + ctrl.jeu().getNombreCoupsRestants());
            ctrl.jouer(c.depart().ligne(), c.depart().colonne(), c.depart().epoque());
            Case arr = c.arrivee();
            if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Premier coup
            ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
            System.out.println("Coup 1 joué : " + p.hash());
            valeur = coup2(arr, j,c,minmax,horizon);
        }
        ctrl.annuler();
        return valeur;
    }
    int coup2(Case arr, Joueur j, Coup c,int minmax,int horizon) {
        Plateau p = ctrl.jeu().plateau();
        ArrayList<Coup> C2 = p.casesJouablesEpoque(j, true, arr.ligne(), arr.colonne(), arr.epoque());
        Iterator<Coup> it2 = C2.iterator();
        int valeur = -1000000000;
        while (it2.hasNext()) {
            Coup c2 = it2.next();
            arr = c2.arrivee();
            // Recolte ? Plantation ? Mouvement ?
            if (c.estPlantation()) ctrl.selectionnerPlanterGraine();
            if (c.estRecolte()) ctrl.selectionnerRecolterGraine();
            // Second coup
            ctrl.jouer(arr.ligne(), arr.colonne(), arr.epoque());
            System.out.println("Coup 2 joué : " + p.hash());
            valeur = choixFocus(arr, j,c,c2,minmax,horizon);
        }
        ctrl.annuler();
        return valeur;
    }

    int choixFocus(Case arr, Joueur j, Coup c,Coup c2,int minmax,int horizon){
        Plateau p = ctrl.jeu().plateau();
        Epoque focusJ = j.focus();
        int valeur = -1000000000*minmax;
        int valeur2;
        for (int foc = 0; foc < 3; foc++) {
            if (j.focus().indice() != foc){
                //ctrl.jeu().plateau().fixerPlateau(copie3);
                ctrl.jouer(0,0,Epoque.depuisIndice(foc));
                System.out.println("Coup 3 joué : " + p.hash());
                System.out.println("Focus Actuel : " + focusJ);
                System.out.println("Focus changé : " + Epoque.depuisIndice(foc));
                valeur2 = valeur;
                valeur = Math.max(valeur * minmax, calcul(ctrl.jeu().plateau(), horizon - 1, minmax * -1) * minmax);
                if (valeur2 < valeur || horizon == this.horizon && valeur2 == -10000) {
                    // TODO : Ajout d'aléatoire possible dans une certaine mesure
                    premierCoup = c;
                    secondCoup = c2;
                    coupFocus = Epoque.depuisIndice(foc);
                    System.out.println(((Mouvement) premierCoup).toString() + "  :: " + ((Mouvement) secondCoup).toString() + " approx =" + valeur);
                }
                j.fixerFocus(focusJ);
            }
        }
        return valeur;
    }
    void jouer() {
        // Copie d'un plateau pour eviter les modifications
        Plateau p = ctrl.jeu().plateau().copier();

        // Calcul du meilleur coup est stockage dans c1 et c2
        this.calcul(ctrl.jeu().plateau(),horizon,1);
        // On remet le plateau tel qu'il était ( assurance d'un plateau identique)
        //ctrl.jeu().plateau().fixerPlateau(p);
        //DEBUG
        System.out.println("coup1 = " + this.c1.toString());
        System.out.println("coup2 = " + this.c2.toString());
        System.out.println("Epoque changment  " + focusChangement);
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
        //focus
        ctrl.jouer(0,0,focusChangement);
    }
}
