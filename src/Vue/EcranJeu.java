package Vue;

import javax.swing.*;

public class EcranJeu extends Ecran {
    EcranJeu(EcouteurInterface listener) {
        super(listener);

        JButton button = new JButton("Menu Principal");
        button.addActionListener(listener);
        add(button);
    }
}
