package Vue;

import javax.swing.*;

class VueJeu extends JPanel {
    CollecteurEvenements controleur;
    VueNiveau vueNiveau;

    VueJeu(CollecteurEvenements c) {
        controleur = c;
    }

    void nouvellePartie() {
        vueNiveau = new VueNiveau(controleur);
        controleur.jeu().ajouteObservateur(vueNiveau);
    }
}
