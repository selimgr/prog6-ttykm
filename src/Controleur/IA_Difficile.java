package Controleur;

import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class IA_Difficile extends IA {


    IA_Difficile(Jeu jeu, Joueur ia, Joueur adversaire, ControleurMediateur ctrl) {
        super(jeu,ia,adversaire,ctrl);
        horizon = 10;
        antiCycle = new HashMap<>();
        alphaBeta = new int[horizon];
    }
    public int fonctionApproximation(Plateau p){
        return p.coupJouables(ia) - p.coupJouables(adversaire);
    }



    // TODO : Modifier pour utiliser annulerCoup ou fixerPlateau


}
