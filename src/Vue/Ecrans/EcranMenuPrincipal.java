package Vue.Ecrans;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EcranMenuPrincipal extends Ecran {
    JButton boutonPartie;

    public EcranMenuPrincipal(JFrame frame) {
        super(frame);

        boutonPartie = new JButton("Nouvelle Partie");
        boutonPartie.addActionListener(this);
        add(boutonPartie);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (boutonPartie.equals(e.getSource())) {
            changerEcran(NOUVELLE_PARTIE);
        }
    }
}
