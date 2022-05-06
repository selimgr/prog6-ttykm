package Vue.Ecrans;

import Modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Ecran extends JPanel implements ActionListener {
    JFrame frame;
    Container contentPane;

    public final static String DEMARRAGE = "Démarrage";
    public final static String MENU_PRINCIPAL = "Menu Principal";
    public final static String NOUVELLE_PARTIE = "Nouvelle Partie";
    public final static String JEU = "Jeu";

    Ecran(JFrame frame) {
        this.frame = frame;
        contentPane = frame.getContentPane();
    }

    public static void nouvelEcran(String nomEcran, JFrame frame) {
        Ecran ecran;

        switch (nomEcran) {
            case DEMARRAGE:
                ecran = new EcranDemarrage(frame);
                break;
            case MENU_PRINCIPAL:
                ecran = new EcranMenuPrincipal(frame);
                break;
            case NOUVELLE_PARTIE:
                ecran = new EcranNouvellePartie(frame);
                break;
            default:
                throw new IllegalArgumentException();
        }
        frame.getContentPane().add(ecran, nomEcran);
    }

    public static void nouvelEcran(String nomEcran, JFrame frame, Jeu jeu) {
        if (!nomEcran.equals(JEU)) {
            throw new IllegalArgumentException();
        }
        Ecran ecran = new EcranJeu(frame, jeu);
        frame.getContentPane().add(ecran, nomEcran);
    }

    void changerEcran(String nomEcran) {
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, nomEcran);
    }
}
