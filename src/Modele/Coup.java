package Modele;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Coup {
    Plateau plateau;
    Deque<Etat> etats;

    Coup(Plateau p) {
        plateau = p;
        etats = new ArrayDeque<>();
    }

    void ajouterEtat(int l, int c, Epoque e, int contenuAvant, int contenuApres) {
        etats.push(new Etat(l, c, e, contenuAvant, contenuApres));
    }

    public void jouer();

    public void annuler();
}
