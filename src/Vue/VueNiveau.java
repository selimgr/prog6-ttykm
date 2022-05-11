package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

class VueNiveau extends JComponent implements Observateur {
    CollecteurEvenements controleur;

    VueNiveau(CollecteurEvenements c) {
        controleur = c;
//        c.jeu().ajouteObservateur(this);
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void miseAJour() {
        repaint();
    }
}
