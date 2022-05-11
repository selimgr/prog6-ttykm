package Vue;

import javax.swing.*;

class VueJeu extends JPanel {
    VueNiveau vueNiveau;

    VueJeu(CollecteurEvenements c) {
        vueNiveau = new VueNiveau(c);
    }
}
