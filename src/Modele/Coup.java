package Modele;

import java.util.ArrayList;
import java.util.List;

public class Coup {
    List<Mouvement> mouvements;

    Coup(Case depart, Case arrivee) {
        this.mouvements = new ArrayList<>();
    }

    void ajouterMouvement(Case depart, Case arrivee) {
        mouvements.add(new Mouvement(depart , arrivee));
    }
}
