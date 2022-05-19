package Controleur;

import Modele.Plateau;

public class IA_Moyen extends IA {
    ControleurMediateur ctrl;

    IA_Moyen() {

    }
    public int calcul(Plateau p, int horizon, int minmax) {
        return 1;
    }

    @Override
    public int fonctionApproximation(Plateau p) {
        return 0;
    }
}
