package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

class VueNiveau extends JComponent implements Observateur {
    Jeu jeu;

    VueNiveau(Jeu j) {
        jeu = j;
        jeu.ajouteObservateur(this);
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void miseAJour() {
        repaint();
    }
}
