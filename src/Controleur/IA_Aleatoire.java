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
        coups = jeu.plateau().casesJouablesEpoque(j,false,0,0, null);
        Random r = new Random();
        int alea = r.nextInt(coups.size());
        c1 = coups.get(alea);
        ctrl.jouer(c1.lignePion(),c1.colonnePion(),c1.epoquePion());
        // TODO :  Interface IA avec jeu : à modifier pour passer par jeu ou controleur mediateur dans une fonction (+ IA abstract class ?)
        int lA = c1.lignePion()+ c1.deplacementLignePion();
        int cA = c1.deplacementColonnePion()+ c1.deplacementColonnePion();
        int eA = c1.epoquePion().indice() + c1.deplacementEpoquePion();
        ctrl.jouer(lA,cA,Epoque.depuisIndice(eA));
        // Second coup avec pion déjà choisi
        coups = jeu.plateau().casesJouablesEpoque(j,true,lA,cA,Epoque.depuisIndice(eA));
        alea = r.nextInt(coups.size());
        c2 = coups.get(alea);
        lA = c2.lignePion()+ c2.deplacementLignePion();
        cA = c2.deplacementColonnePion()+ c2.deplacementColonnePion();
        eA = c2.epoquePion().indice() + c2.deplacementEpoquePion();
        ctrl.jouer(lA,cA,Epoque.depuisIndice(eA));
        //Changement de focus
        alea = r.nextInt(3);
        if (c2.epoquePion().indice() == alea){
            if (alea == 1){
                alea = alea + 1;
            }
            else {
                alea = 1;
            }
        }
        //jeu.changerFocus(Epoque.depuisIndice(alea));
        return 1;
    }

    public int fonctionApproximation(Plateau p){
        return p.coupJouables(ia) - p.coupJouables(adversaire);
    }



}