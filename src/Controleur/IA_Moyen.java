package Controleur;

import Modele.Jeu;
import Modele.Joueur;
import Modele.Plateau;

import java.util.HashMap;
import java.util.Random;

public class IA_Moyen extends IA {
    ControleurMediateur ctrl;
    Random r;

    IA_Moyen(Jeu jeu, Joueur ia, Joueur adversaire, ControleurMediateur ctrl) {
        super(jeu, ia, adversaire, ctrl);
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
