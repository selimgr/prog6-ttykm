package Vue;

import javax.swing.*;

public class EcranDemarrage extends Ecran {
    EcranDemarrage(EcouteurInterface listener) {
        super(listener);

        JButton button1 = new JButton("Menu Principal");
        button1.addActionListener(listener);
        add(button1);

        JButton button2 = new JButton("Jeu");
        button2.addActionListener(listener);
        add(button2);
    }
}
