package Vue;

import javax.swing.*;
import java.awt.*;

public class Vues {
    JFrame frame;
    VueJeu vueJeu;

    final static String DEMARRAGE = "Démarrage";
    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_NOUVELLE_PARTIE = "Nouvelle Partie";
    final static String JEU = "Jeu";

    Vues(JFrame f) {
        frame = f;
    }

    void fixerVueJeu(VueJeu vue) {
        vueJeu = vue;
    }

    public void nouvellePartie() {
        if (vueJeu == null) {
            throw new IllegalStateException("Impossible de créer une nouvelle partie : vue du jeu non fixée");
        }
        vueJeu.nouvellePartie();
    }

    private void afficher(String nom) {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), nom);
    }

    public void afficherDemarrage() {
        afficher(DEMARRAGE);
    }

    public void afficherMenuPrincipal() {
        afficher(MENU_PRINCIPAL);
    }

    public void afficherMenuNouvellePartie() {
        afficher(MENU_NOUVELLE_PARTIE);
    }

    public void afficherJeu() {
        afficher(JEU);
    }
}
