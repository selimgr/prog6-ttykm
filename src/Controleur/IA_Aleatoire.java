package Controleur;

import Modele.*;

import java.util.List;
import java.util.Random;

public class IA_Aleatoire extends IA {
    List<Coup> coups;


    IA_Aleatoire(Jeu jeu, Joueur ia, Joueur adv, ControleurMediateur ctrl) {
        super(jeu,ia,adv,ctrl);
    }

    public int calcul(Plateau p, int horizon,int minmax){
        //recherche des coups jouables
        int lA,cA,eA;
        coups = jeu.plateau().casesJouablesEpoque(ia,false,0,0, null);
        Random r = new Random();
        int alea = r.nextInt(coups.size());
        c1 = coups.get(alea);
        lA = c1.depart().ligne();
        cA = c1.depart().colonne();
        eA = c1.depart().epoque().indice();
        Plateau p1 = p;
        ctrl.jouer(c1.depart().ligne(),c1.depart().colonne(),c1.depart().epoque());
        // TODO :  Interface IA avec jeu : à modifier pour passer par jeu ou controleur mediateur dans une fonction (+ IA abstract class ?)
        lA = c1.depart().ligne();
        cA = c1.depart().colonne();
        eA = c1.depart().epoque().indice();
        ctrl.jouer(lA,cA,Epoque.depuisIndice(eA));
        // Second coup avec pion déjà choisi
        coups = jeu.plateau().casesJouablesEpoque(ia,true,lA,cA,Epoque.depuisIndice(eA));
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
        ctrl.jouer(0,0,Epoque.depuisIndice(alea));
        return 1;
    }

    public int fonctionApproximation(Plateau p){
        return p.coupJouables(ia) - p.coupJouables(adversaire);
    }

    @Override
    void jouer() {
        calcul(jeu.plateau(),0,1);
    }


}