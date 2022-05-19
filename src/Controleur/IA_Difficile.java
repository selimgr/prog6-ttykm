package Controleur;

import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class IA_Difficile extends IA {


    IA_Difficile(Jeu jeu, Joueur ia, Joueur adversaire) {
        horizon = 300;
        this.ia = ia;
        this.adversaire = adversaire;
        this.jeu = jeu;
        antiCycle = new HashMap<>();
    }

    public int fonctionApproximation(Plateau p){
        return p.coupJouables(ia) - p.coupJouables(adversaire);
    }



    // TODO : Modifier pour utiliser annulerCoup ou fixerPlateau


}
