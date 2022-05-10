package Vue;

import javax.swing.*;
import java.awt.*;

public class Vues {
    JFrame frame;

    final static String DEMARRAGE = "Démarrage";
    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_NOUVELLE_PARTIE = "Nouvelle Partie";
    final static String JEU = "Jeu";

    Vues(JFrame f) {
        frame = f;
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
