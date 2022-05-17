package Controleur;

import Modele.Pion;

import java.util.Random;

public class IA_Aleatoire implements IA{
    ControleurMediateur ctrl;
    Random r = new Random();
    Pion pions;

    IA_Aleatoire(ControleurMediateur c){
        this.ctrl = c;
        pions = ctrl.jeu.joueurActuel().pions(); //quels pions pour l'ia // il faut initialiser l'ia quand c'est son tour
    }

    void JouerTour(){
        //choisir coup légal
        // TODO : Implémenter casesJouables + coup jouables?
        // casesJouables(int l, int c)
        //effecteur coup
        //x2
        //ctrl.jeu.jouerTour();

    }
}