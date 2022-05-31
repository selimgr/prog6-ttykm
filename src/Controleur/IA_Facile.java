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
        horizon = 3;
        antiCycle = new HashMap<>();
        alphaBeta = new int[horizon];
        r = new Random();
    }
    @Override
    public int fonctionApproximation(Plateau p) {
        return r.nextInt(50);
    }
}
