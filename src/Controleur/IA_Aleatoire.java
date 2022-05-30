package Controleur;

import Modele.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IA_Aleatoire extends IA {
    List<Coup> coups;


    IA_Aleatoire(Jeu jeu, Joueur ia, Joueur adv, ControleurMediateur ctrl) {
        super(jeu,ia,adv,ctrl);
    }

    public int calcul(Plateau p, int horizon,int minmax){
        // Recherche des coups jouables
        int lA,cA,eA,alea;
        coups = ctrl.jeu().plateau().casesJouablesEpoque(ia,false,0,0, null);
        Random r = new Random();
        if (coups.size() == 0) {
            alea = r.nextInt(3);
            gestionFocus(alea);
            return 0;
        }
        alea = r.nextInt(coups.size());
        System.out.println(coups.size() + " !1! " + alea);
        c1 = coups.get(alea);
        System.out.println(( (Mouvement) c1).toString());
        if (ctrl.jeu().prochaineActionSelectionPion()) {
            ctrl.jouer(c1.depart().ligne(), c1.depart().colonne(), c1.depart().epoque()); // Selection
        }
        else {
            throw new IllegalStateException("IA ne doit pas avoir de pion préselectionné");
        }
        ctrl.jouer(c1.arrivee().ligne(), c1.arrivee().colonne(), c1.arrivee().epoque()); // coup 1
        // Second coup avec pion déjà choisi
        if (ctrl.jeu().plateau().aPion(c1.arrivee().ligne(), c1.arrivee().colonne(), c1.arrivee().epoque())) {
            coups = ctrl.jeu().plateau().casesJouablesEpoque(ia, true, c1.arrivee().ligne(), c1.arrivee().colonne(), c1.arrivee().epoque());
            if (coups.size() ==0 ) throw new IllegalStateException("coups est vide");
            alea = r.nextInt(coups.size());
            System.out.println(coups.size() + " !2! " + alea);
            c2 = coups.get(alea);
            System.out.print(( (Mouvement) c2).toString() + " -- ");
            //System.out.println(( (Mouvement) c2).arrivee().toString());
            ctrl.jouer(c2.arrivee().ligne(), c2.arrivee().colonne(), c2.arrivee().epoque()); // coup 2

        }
        //Changement de focus
        alea = r.nextInt(3);
        gestionFocus(alea);
        return 1;
    }

    void gestionFocus(int alea ){
        if (ia.focus().indice() == alea){
            if (alea == 1){
                alea = alea + 1;
            }
            else {
                alea = 1;
            }
        }
        System.out.println("Focus choisi = " +alea);
        ctrl.jouer(0,0,Epoque.depuisIndice(alea));
    }

    public int fonctionApproximation(Plateau p){
        return p.coupJouables(ia) - p.coupJouables(adversaire);
    }

    @Override
    void jouer()  {
        calcul(ctrl.jeu().plateau(),0,1);
    }


}