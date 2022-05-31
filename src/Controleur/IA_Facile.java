package Controleur;

import Modele.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class IA_Facile extends IA {
    ControleurMediateur ctrl;
    Joueur j;
    List<Coup> coups;
    Coup c1;
    Coup c2;
    Random r;



    IA_Facile(Jeu jeu, Joueur ia, Joueur adversaire,ControleurMediateur ctrl) {
        super(jeu,ia,adversaire,ctrl);
        horizon = 1;
        antiCycle = new HashMap<>();
        alphaBeta = new int[horizon];
        r = new Random();
    }
    @Override
    public int fonctionApproximation(Plateau p) {
        return r.nextInt(50);
    }

    @Override
    int isFeuille(int horizon, Plateau p) {
        if (p.nombrePlateauVide(adversaire.pions()) >=2){
            return r.nextInt(70);
        }
        if (p.nombrePlateauVide(ia.pions()) >=2){
            return r.nextInt(70);

        }
        if (horizon <= 0 ){
            return fonctionApproximation(p);
        }
        // On peux parcourir un plus grand horizon donc ce coup peut devenir plus nul ou plus intéressant
        if ( antiCycle.get(p.hash()) != null && antiCycle.get(p.hash()) >= horizon){
            return fonctionApproximation(p);
        }
        return 7777777;
    }
}
