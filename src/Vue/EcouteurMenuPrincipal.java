package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EcouteurMenuPrincipal implements ActionListener {
    CollecteurEvenements controleur;

    EcouteurMenuPrincipal(CollecteurEvenements c) {
        controleur = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controleur.afficherMenuPrincipal();
    }
}
