package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AdaptateurSouris extends MouseAdapter {
    CollecteurEvenements controleur;

    AdaptateurSouris(CollecteurEvenements c) {
        controleur = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
}
