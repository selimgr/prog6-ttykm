package Vue;

import Vue.Ecrans.*;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
    CollecteurEvenements collecteur;
    JFrame frame;
    GraphicsEnvironment env;
    GraphicsDevice device;
    boolean maximized;
    int width, height;

    InterfaceGraphique(CollecteurEvenements c) {
        collecteur = c;
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

        width = dm.getWidth() / 2;
        height = dm.getHeight() / 2;

        CardLayout layout = new CardLayout();
        frame.setLayout(layout);

        // Ajout de nos composants de dessin dans la fenetre
        Ecran.nouvelEcran(Ecran.DEMARRAGE, frame);
        Ecran.nouvelEcran(Ecran.MENU_PRINCIPAL, frame);
        Ecran.nouvelEcran(Ecran.NOUVELLE_PARTIE, frame);

        // Ajout des listeners


        // Ajout du timer


        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et centre la fenêtre
        frame.setSize(width, height);
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
}
