package Modele;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Coup {
    Plateau plateau;
    Deque<Etat> etats;

    Coup(Plateau p) {
        plateau = p;
        etats = new ArrayDeque<>();
    }

    void ajouterEtat(int l, int c, Epoque e, int contenuAvant, int contenuApres) {
        etats.push(new Etat(l, c, e, contenuAvant, contenuApres));
    }

    void ajout(int l, int c, Epoque e, Piece p) {
        ajouterEtat(l, c, e, plateau.contenu(l, c, e), plateau.ajout(l, c, e, p));
    }

    void suppression(int l, int c, Epoque e, Piece p) {
        ajouterEtat(l, c, e, plateau.contenu(l, c, e), plateau.suppression(l, c, e, p));
    }

    public void jouer() {
        Iterator<Etat> it = etats.iterator();

        while (it.hasNext()) {
            Etat q = it.next();

            if (plateau.contenu(q.ligne(), q.colonne(), q.epoque()) != q.contenuAvant()) {
                throw new IllegalStateException("Etat du plateau incorrect");
            }
            plateau.modifierCase(q.ligne(),q.colonne(),q.epoque(),q.contenuApres());
        }
    }

    public void annuler() {
        Iterator<Etat> it = etats.descendingIterator();

        while (it.hasNext()){
            Etat q = it.next();

            if (plateau.contenu(q.ligne(), q.colonne(), q.epoque()) != q.contenuApres()) {
                throw new IllegalStateException("Etat du plateau incorrect");
            }
            plateau.modifierCase(q.ligne(),q.colonne(),q.epoque(),q.contenuAvant());
        }
}
