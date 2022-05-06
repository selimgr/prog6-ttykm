package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EcranMenuPrincipal extends Ecran {
    EcranMenuPrincipal(EcouteurInterface listener) {
        super(listener);

        JButton button = new JButton("Jeu");
        button.addActionListener(listener);
        add(button);
    }
}
