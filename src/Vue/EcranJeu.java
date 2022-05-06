package Vue;

import javax.swing.*;

public class EcranJeu extends Ecran {

    EcranJeu(EcouteurInterface listener, String name) {
        super(listener);

        JLabel test = new JLabel(name);
        JButton button = new JButton("Menu Principal");
        button.addActionListener(listener);
        add(button);
        add(test);
    }
}
