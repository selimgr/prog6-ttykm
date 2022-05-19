package Controleur;

import Modele.Coup;
import Modele.Epoque;
import Modele.Joueur;
import Modele.Plateau;

import java.util.List;
import java.util.Random;

public class IA_Facile extends IA {
    ControleurMediateur ctrl;
    Joueur j;
    List<Coup> coups;
    Coup c1;
    Coup c2;


    IA_Facile() {

    }
    public int calcul(Plateau p, int horizon,int minmax) {
        return 1;
    }

    @Override
    public int fonctionApproximation(Plateau p) {
        return 0;
    }
}
