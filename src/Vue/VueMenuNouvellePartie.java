package Vue;

import Modele.TypeJoueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VueMenuNouvellePartie extends JPanel {
    CollecteurEvenements controleur;

    VueMenuNouvellePartie(CollecteurEvenements c) {
        controleur = c;

        setBackground(Color.GREEN);

        JButton boutonPartie = new JButton("Lancer la partie");
        boutonPartie.addActionListener(new EcouteurNouvellePartie());
        add(boutonPartie);

        JButton boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener(new EcouteurMenuPrincipal(c));
        add(boutonRetour);
    }

    private class EcouteurNouvellePartie implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controleur.nouvellePartie("abc", TypeJoueur.HUMAIN, "def", TypeJoueur.IA_FACILE);
            controleur.afficherJeu();
        }
    }
}
