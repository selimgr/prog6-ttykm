package Vue;

import javax.swing.*;
import java.awt.*;

class VueMenuPrincipal extends JPanel {
    CollecteurEvenements controleur;

    VueMenuPrincipal(CollecteurEvenements c) {
        controleur = c;

        setBackground(Color.BLACK);

        JButton nouvellePartie = new JButton("Nouvelle Partie");
        nouvellePartie.addActionListener((e) -> controleur.afficherMenuNouvellePartie());
        add(nouvellePartie);

        JButton chargerPartie, regles, didacticiel, quitter, parametres;
    }
}
