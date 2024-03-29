package Vue;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
    CollecteurEvenements controleur;
    Vues vues;
    JFrame frame;
    GraphicsEnvironment env;
    GraphicsDevice device;
    boolean maximized;

    InterfaceGraphique(CollecteurEvenements c) {
        controleur = c;
    }

    public static void demarrer(CollecteurEvenements c) {
        SwingUtilities.invokeLater(new InterfaceGraphique(c));
    }

    @Override
    public void run() {
        // Nouvelle fenêtre
        frame = new JFrame("That Time You Killed Me");

        // On récupère des informations sur l'écran
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();
        DisplayMode dm = device.getDisplayMode();

        // TODO: Gérer la taille de la fenêtre dans default.cfg
        int width = dm.getWidth() / 4 * 3;
        int height = dm.getHeight() / 4 * 3;

        // On fixe le layout du conteneur contenant les différentes vues de la fenêtre
        frame.getContentPane().setLayout(new CardLayout());

        // Ajout de nos vues dans la fenêtre
        vues = new Vues(frame);

        ajouterVue(Vues.DEMARRAGE);
        ajouterVue(Vues.MENU_SAISIES);
        ajouterVue(Vues.MENU_PRINCIPAL);
        ajouterVue(Vues.MENU_PARTIES);
        ajouterVue(Vues.JEU);

        controleur.fixerMediateurVues(vues);

        // Ajout des listeners


        // Ajout du timer
        Timer time = new Timer(16, new AdaptateurTemps(controleur));
        time.start();

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et centre la fenêtre
        frame.setSize(width, height);
        frame.setMinimumSize(new Dimension(1040, 666));
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public void toggleFullscreen() {
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
    }

    void ajouterVue(String nom) {
        JPanel vue;

        switch (nom) {
            case Vues.DEMARRAGE:
                vue = new VueDemarrage();
                break;
            case Vues.MENU_PRINCIPAL:
                vue = new VueMenuPrincipal(controleur);
                break;
            case Vues.MENU_SAISIES:
                vue = new VueMenuSaisies(controleur);
                break;
            case Vues.JEU:
                vue = new VueJeu(controleur);
                vues.fixerVueJeu((VueJeu) vue);
                break;
            case Vues.MENU_PARTIES:
                vue = new VueMenuParties(controleur);
                break;
            default:
                throw new IllegalArgumentException("Nom de vue incorrect : " + nom);
        }
        frame.getContentPane().add(vue, nom);
    }
}