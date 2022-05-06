import Controleur.ControleurMediateur;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

public class Main {
    public static void main(String[] args) {
        CollecteurEvenements controleur = new ControleurMediateur();
        InterfaceGraphique.demarrer(controleur);
    }
}
