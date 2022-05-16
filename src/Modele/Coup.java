package Modele;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public abstract class Coup {
    protected final Plateau plateau;
    protected final Joueur joueur;
    protected final int pionL, pionC;
    protected final Epoque ePion;
    private Deque<Etat> etats;

    Coup(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        plateau = p;
        joueur = j;
        this.pionL = pionL;
        this.pionC = pionC;
        this.ePion = ePion;
        etats = new ArrayDeque<>();
    }

    int lignePion() {
        return pionL;
    }

    int colonnePion() {
        return pionC;
    }

    Epoque epoquePion() {
        return ePion;
    }

    private void ajouterEtat(int l, int c, Epoque e, int contenuAvant, int contenuApres) {
        etats.push(new Etat(l, c, e, contenuAvant, contenuApres));
    }
    
    protected void ajouter(int l, int c, Epoque e, Piece p) {
        ajouterEtat(l, c, e, plateau.contenu(l, c, e), plateau.ajout(l, c, e, p));
    }

    protected void supprimer(int l, int c, Epoque e, Piece p) {
        ajouterEtat(l, c, e, plateau.contenu(l, c, e), plateau.suppression(l, c, e, p));
    }

    abstract boolean creer(int destL, int destC, Epoque eDest);

    public void jouer() {
        Iterator<Etat> it = etats.iterator();

        while (it.hasNext()) {
            Etat q = it.next();

            if (plateau.contenu(q.ligne(), q.colonne(), q.epoque()) != q.contenuAvant()) {
                throw new IllegalStateException("Etat du plateau incorrect");
            }
            plateau.fixerCase(q.ligne(), q.colonne(), q.epoque(), q.contenuApres());
        }
    }

    public void annuler() {
        Iterator<Etat> it = etats.descendingIterator();

        while (it.hasNext()) {
            Etat q = it.next();

            if (plateau.contenu(q.ligne(), q.colonne(), q.epoque()) != q.contenuApres()) {
                throw new IllegalStateException("Etat du plateau incorrect");
            }
            plateau.fixerCase(q.ligne(), q.colonne(), q.epoque(), q.contenuAvant());
        }
    }
}
