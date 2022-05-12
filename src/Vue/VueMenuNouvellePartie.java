package Vue;

import Modele.TypeJoueur;
import Modele.TypePion;

import javax.swing.*;
import java.awt.*;

class VueMenuNouvellePartie extends JPanel {

    VueMenuNouvellePartie(CollecteurEvenements c) {
        setBackground(Color.GREEN);

        JButton boutonPartie = new JButton("Lancer la partie");
        boutonPartie.addActionListener((e) -> {
            //String nomJ1, TypeJoueur typeJ1, TypePion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, TypePion pionsJ2, int handicapJ2
            c.nouvellePartie("abc", TypeJoueur.HUMAIN, TypePion.NOIR,4,"def", TypeJoueur.IA_FACILE, TypePion.BLANC, 4);
            c.afficherJeu();
        });
        add(boutonPartie);

        JButton boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener((e) -> c.afficherMenuPrincipal());
        add(boutonRetour);
    }
}
