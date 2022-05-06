package Vue;

import Vue.Ecrans.EcranJeu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    EcranJeu ecranJeu;
    CollecteurEvenements controleur;

    AdaptateurSouris(EcranJeu ecran, CollecteurEvenements c) {
        ecranJeu = ecran;
        controleur = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
}
