package Modele;

import java.util.ArrayList;
import java.util.List;

public class Coup {
    List<Mouvement> mouvements;
    Niveau niveau;


    Coup(Case depart, Case arrivee) {
        this.mouvements = new ArrayList<>();
        this.mouvements.add(new Mouvement(depart, arrivee));
    }

    void ajouterMouvement(Case depart, Case arrivee) {
        mouvements.add(new Mouvement(depart , arrivee));
    }


    void listeMouvementsCoup(){

        }
}


