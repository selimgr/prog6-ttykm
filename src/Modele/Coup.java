package Modele;

import java.util.ArrayDeque;
import java.util.Deque;

public class Coup {
    Deque<Mouvement> mouvements;

    Coup(Case depart, Case arrivee) {
        mouvements = new ArrayDeque<>();
        mouvements.push(new Mouvement(depart, arrivee));
    }

    void ajouterMouvement(Case depart, Case arrivee) {
        mouvements.push(new Mouvement(depart , arrivee));
    }

    void listeMouvementsCoup() {

    }
}


