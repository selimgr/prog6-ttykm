package Modele;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public abstract class Coup {
    private final Plateau plateau;
    private final Joueur joueur;
    private final int pionL, pionC;
    private final Epoque ePion;
    private final Deque<Etat> etats;
    private boolean coupJoue;

    Coup(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        plateau = p;
        joueur = j;
        this.pionL = pionL;
        this.pionC = pionC;
        this.ePion = ePion;
        etats = new ArrayDeque<>();
    }

    Plateau plateau() {
        return plateau;
    }

    Joueur joueur() {
        return joueur;
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

    protected void ajouter(int l, int c, Epoque e, Piece pion) {
        etats.push(new Etat(l, c, e, null, pion));
    }

    protected void supprimer(int l, int c, Epoque e, Piece pion) {
        etats.push(new Etat(l, c, e, pion, null));
    }

    protected void verifierPremierCoupCree() {
        if (!etats.isEmpty()) {
            throw new IllegalStateException("Impossible de créer un nouveau coup : un coup a déjà été créé");
        }
    }

    abstract boolean creer(int destL, int destC, Epoque eDest);

    public void jouer() {
        if (etats.isEmpty()) {
            throw new IllegalStateException("Impossible de jouer le coup : aucun coup créé");
        }
        if (coupJoue) {
            throw new IllegalStateException("Impossible de jouer le coup : coup déjà joué");
        }
        coupJoue = true;

        for (Etat q : etats) {
            plateau.supprimer(q.ligne(), q.colonne(), q.epoque(), q.pieceAvant());
            plateau.ajouter(q.ligne(), q.colonne(), q.epoque(), q.pieceApres());
        }
    }

    public void annuler() {
        if (etats.isEmpty()) {
            throw new IllegalStateException("Impossible d'annuler le coup : aucun coup créé");
        }
        if (!coupJoue) {
            throw new IllegalStateException("Impossible d'annuler le coup : le coup n'a pas encore été joué");
        }
        coupJoue = false;

        Iterator<Etat> it = etats.descendingIterator();

        while (it.hasNext()) {
            Etat q = it.next();
            plateau.supprimer(q.ligne(), q.colonne(), q.epoque(), q.pieceApres());
            plateau.ajouter(q.ligne(), q.colonne(), q.epoque(), q.pieceAvant());
        }
    }
}
