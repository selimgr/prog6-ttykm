package Controleur;

import Modele.*;

import java.util.List;
import java.util.Random;

public class IA_Aleatoire extends IA {
    Jeu jeu;
    Joueur j;
    List<Coup> coups;
    Coup c1;
    Coup c2;

    IA_Aleatoire(Jeu jeu,Joueur j) {
        this.jeu = jeu;
        this.j =j;
    }

    public int calcul(Plateau p, int horizon,int minmax){
        //recherche des coups jouables
        int lA,cA,eA;
        coups = jeu.plateau().casesJouablesEpoque(j,false,0,0, null);
        Random r = new Random();
        int alea = r.nextInt(coups.size());
        c1 = coups.get(alea);
        ctrl.jouer(c1.depart().ligne(),c1.depart().colonne(),c1.depart().epoque());
        // TODO :  Interface IA avec jeu : à modifier pour passer par jeu ou controleur mediateur dans une fonction (+ IA abstract class ?)
        lA = c1.depart().ligne();
        cA = c1.depart().colonne();
        eA = c1.depart().epoque().indice();
        ctrl.jouer(lA,cA,Epoque.depuisIndice(eA));
        // Second coup avec pion déjà choisi
        coups = jeu.plateau().casesJouablesEpoque(j,true,lA,cA,Epoque.depuisIndice(eA));
        alea = r.nextInt(coups.size());
        c2 = coups.get(alea);
        lA = c2.depart().ligne();
        cA = c2.depart().colonne();
        eA = c2.depart().epoque().indice();
        ctrl.jouer(lA,cA,Epoque.depuisIndice(eA));
        //Changement de focus
        alea = r.nextInt(3);
        if (c2.depart().epoque().indice() == alea){
            if (alea == 1){
                alea = alea + 1;
            }
            else {
                alea = 1;
            }
        }
        switch (alea){
            case 0:
                ctrl.changerFocusPasse();break;
            case 1:
                ctrl.changerFocusPresent();break;
            case 2:
                ctrl.changerFocusFutur();break;
        }
        return 1;
    }

    public int fonctionApproximation(Plateau p){
        return p.coupJouables(ia) - p.coupJouables(adversaire);
    }



}