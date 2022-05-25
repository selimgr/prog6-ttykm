import Controleur.ControleurMediateur;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        CollecteurEvenements controleur = new ControleurMediateur();
        InterfaceGraphique.demarrer(controleur);
    }
}
