package Controleur;

import Modele.Jeu;
import Modele.Joueur;
import Modele.Plateau;

public class IA_Moyen extends IA {
    ControleurMediateur ctrl;

    IA_Moyen(Jeu jeu, Joueur ia, Joueur adversaire) {

    }
    public int calcul(Plateau p, int horizon, int minmax) {
        return 1;
    }

    @Override
    public int fonctionApproximation(Plateau p) {
        return 0;
    }
}
