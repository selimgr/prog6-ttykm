package Vue.Ecrans;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EcranDemarrage extends Ecran {
    JButton boutonMenu;

    public EcranDemarrage(JFrame frame) {
        super(frame);

        boutonMenu = new JButton("Menu Principal");
        boutonMenu.addActionListener(this);
        add(boutonMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (boutonMenu.equals(e.getSource())) {
            changerEcran(MENU_PRINCIPAL);
        }
    }
}
