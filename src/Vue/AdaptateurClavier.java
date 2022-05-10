package Vue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class AdaptateurClavier extends KeyAdapter {
    CollecteurEvenements controleur;

    AdaptateurClavier(CollecteurEvenements c) {
        controleur = c;
    }

    @Override
    public void keyPressed(KeyEvent event) {

    }
}
