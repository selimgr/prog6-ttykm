package Vue;

import Modele.TypeJoueur;

import javax.swing.*;
import java.awt.*;

class VueMenuNouvellePartie extends JPanel {

    VueMenuNouvellePartie(CollecteurEvenements c) {
        setBackground(Color.GREEN);

        JButton boutonPartie = new JButton("Lancer la partie");
        boutonPartie.addActionListener((e) -> {
            c.nouvellePartie("abc", TypeJoueur.HUMAIN, "def", TypeJoueur.IA_FACILE);
            c.afficherJeu();
        });
        add(boutonPartie);

        JButton boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener((e) -> c.afficherMenuPrincipal());
        add(boutonRetour);
    }
}
