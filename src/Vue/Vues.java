package Vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Vues {
    JFrame frame;
    VueJeu vueJeu;

    final static String DEMARRAGE = "Démarrage";
    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_SAISIES = "Nouvelle Partie";
    final static String MENU_PARTIES = "Charger Partie";
    final static String JEU = "Jeu";
    final static String MENU_FIN = "Fin";

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
        afficher(MENU_SAISIES);
    }

    public void afficherJeu() {
        afficher(JEU);
    }

    public void afficherMenuChargerPartie(){afficher(MENU_PARTIES);}

    public void afficherMenuFin(){afficher(MENU_FIN);}

    public void close() {
        frame.setVisible(true);
        frame.dispose();
    }

    public void afficherR(){
        try {
            File rules = new File("./resources/assets/regles_du_jeu_FR.pdf");
            Desktop.getDesktop().open(rules);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}