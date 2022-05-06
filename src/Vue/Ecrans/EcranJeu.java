package Vue.Ecrans;

import Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EcranJeu extends Ecran {
    JButton boutonMenu;

    public EcranJeu(JFrame frame, Jeu j) {
        super(frame);

        JLabel test = new JLabel("Nom du joueur : " + j.joueur1().nom());
        add(test);
        boutonMenu = new JButton("Menu Principal");
        boutonMenu.addActionListener(this);
        add(boutonMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (boutonMenu.equals(e.getSource())) {
            changerEcran(MENU_PRINCIPAL);
        }
    }
}
