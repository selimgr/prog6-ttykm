import Controleur.ControleurMediateur;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /*try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
        CollecteurEvenements controleur = new ControleurMediateur();
        InterfaceGraphique.demarrer(controleur);
    }
}
