package Vue.Ecrans;

import Modele.Jeu;
import Modele.TypeJoueur;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EcranNouvellePartie extends Ecran {
    JButton boutonPartie, boutonRetour;

    public EcranNouvellePartie(JFrame frame) {
        super(frame);

        boutonPartie = new JButton("Lancer la partie");
        boutonPartie.addActionListener(this);
        add(boutonPartie);

        boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener(this);
        add(boutonRetour);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (boutonPartie.equals(source)) {
            Jeu j = new Jeu();
            j.nouveauJoueur("abc", TypeJoueur.HUMAIN);

            nouvelEcran(JEU, frame, j);
            changerEcran(JEU);
        }
        else if (boutonRetour.equals(source)) {
            changerEcran(MENU_PRINCIPAL);
        }
    }
}
