package Vue;

import javax.swing.*;
import java.awt.*;

class VueDemarrage extends JPanel {

    VueDemarrage(CollecteurEvenements c) {
        setBackground(Color.MAGENTA);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));

        JLabel texte = new JLabel("bonjour bienvenu sur leu jeu");
        texte.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        add(texte);

        JButton boutonMenu = new JButton("Menu Principal");
        boutonMenu.addActionListener((e) -> c.afficherMenuPrincipal());
        add(boutonMenu);
    }
}
