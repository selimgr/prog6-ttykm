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
        coups = jeu.plateau().casesJouablesEpoque(ia,false,0,0, null);
        Random r = new Random();
        if (coups.size() == 0) {
            alea = r.nextInt(3);
            gestionFocus(alea);
            return 0;
        }
        alea = r.nextInt(coups.size());
        c1 = coups.get(alea);
        System.out.println(c1.toString());
        if (jeu.nombreCoupsRestantsTour() ==2 && !jeu.pionSelectionne()) {
            ctrl.jouer(c1.depart().ligne(), c1.depart().colonne(), c1.depart().epoque()); // Selection
        }
        else {
            throw new IllegalStateException("IA ne doit pas avoir de pion préselectionné");
        }
        if (jeu.nombreCoupsRestantsTour() == 2 && jeu.pionSelectionne()) {
            ctrl.jouer(c1.arrivee().ligne(), c1.arrivee().colonne(), c1.arrivee().epoque()); // coup 1
        }
        else {
            throw new IllegalStateException(("IA ne peut pas jouer le coup 1"));
        }
        // Second coup avec pion déjà choisi
        coups = jeu.plateau().casesJouablesEpoque(ia,true,c1.arrivee().ligne(),c1.arrivee().colonne(),c1.arrivee().epoque());
        alea = r.nextInt(coups.size());
        c2 = coups.get(alea);
        System.out.println(c2.toString());
        if (jeu.nombreCoupsRestantsTour() == 1 && jeu.pionSelectionne()) {
            ctrl.jouer(c2.arrivee().ligne(), c2.arrivee().colonne(), c2.arrivee().epoque()); // coup 2
        } else {
            throw new IllegalStateException("IA ne peut pas jour coup 2");
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
        calcul(jeu.plateau(),0,1);
    }


}