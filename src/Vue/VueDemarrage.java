package Vue;

import javax.swing.*;
import java.awt.*;

class VueDemarrage extends JPanel {

    VueDemarrage(CollecteurEvenements c) {
        setBackground(Color.BLUE);

        JButton boutonMenu = new JButton("Menu Principal");
        boutonMenu.addActionListener((e) -> c.afficherMenuPrincipal());
        add(boutonMenu);
    }
}