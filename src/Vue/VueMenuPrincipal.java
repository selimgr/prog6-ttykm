package Vue;

import javax.swing.*;
import java.awt.*;

class VueMenuPrincipal extends JPanel {

    VueMenuPrincipal(CollecteurEvenements c) {
        setBackground(Color.BLACK);

        JButton nouvellePartie = new JButton("Nouvelle Partie");
        nouvellePartie.addActionListener((e) -> c.afficherMenuNouvellePartie());
        add(nouvellePartie);

        JButton chargerPartie, regles, didacticiel, quitter, parametres;
    }
}
