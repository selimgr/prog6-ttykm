package Controleur;

import Modele.Coup;
import Modele.Epoque;
import Modele.Joueur;
import Modele.Jeu;

import java.util.List;
import java.util.Random;

public class IA_Aleatoire implements IA {
    Jeu jeu;
    Joueur j;
    List<Coup> coups;
    Coup c1;
    Coup c2;


    IA_Aleatoire() {

    }
    public void calcul(){
        //recherche des coups jouables
        coups = jeu.plateau().casesJouables(j,false,0,0, null);
        Random r = new Random();
        int alea = r.nextInt(coups.size());
        c1 = coups.get(alea);
        // TODO :  Interface IA avec jeu  : à modifier pour passer par jeu ou controleur mediateur dans une fonction (+ IA abstract class ?)
        int lA = c1.lignePion()+ c1.deplacementLignePion();
        int cA = c1.deplacementColonnePion()+ c1.deplacementColonnePion();
        int eA = c1.epoquePion().indice() + c1.deplacementEpoquePion();
        interfaceIAJeu(c1,lA,cA,Epoque.depuisIndice(eA));
        // Second coup avec pion déjà choisi
        coups = jeu.plateau().casesJouables(j,true,lA,cA,Epoque.depuisIndice(eA));
        alea = r.nextInt(coups.size());
        lA = c2.lignePion()+ c2.deplacementLignePion();
        cA = c2.deplacementColonnePion()+ c2.deplacementColonnePion();
        eA = c2.epoquePion().indice() + c2.deplacementEpoquePion();
        interfaceIAJeu(c2,lA,cA,Epoque.depuisIndice(eA));
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
        jeu.changerFocus(Epoque.depuisIndice(alea));
    }

    private void interfaceIAJeu(Coup cp, int l, int c, Epoque e){
        switch (cp.getAction()){
            case MOUVEMENT -> jeu.jouerMouvement(cp.lignePion(),cp.colonnePion(),cp.epoquePion(),l,c,e);
            case RECOLTE -> jeu.jouerRecolte(cp.lignePion(),cp.colonnePion(),cp.epoquePion(),l,c,e);
            case PLANTATION -> jeu.jouerPlantation(cp.lignePion(),cp.colonnePion(),cp.epoquePion(),l,c,e);
        }
    }

}