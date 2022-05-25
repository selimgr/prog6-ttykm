import Controleur.ControleurMediateur;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {
        }

        CollecteurEvenements controleur = new ControleurMediateur();
        InterfaceGraphique.demarrer(controleur);
    }
}
