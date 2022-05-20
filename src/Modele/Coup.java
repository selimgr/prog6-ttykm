package Modele;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public abstract class Coup {
    private final Plateau plateau;
    private final Joueur joueur;
    private Case pion;
    private final Deque<Etat> etats;
    private boolean coupJoue;

    Coup(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        plateau = p;
        joueur = j;
        pion = new Case(pionL, pionC, ePion);
        etats = new ArrayDeque<>();
    }

    Plateau plateau() {
        return plateau;
    }

    Joueur joueur() {
        return joueur;
    }

    Case pion() {
        return pion;
    }

    protected void deplacer(Piece p, int departL, int departC, Epoque eDepart, int arriveeL, int arriveeC, Epoque eArrivee) {
        etats.add(new Etat(p, new Case(departL, departC, eDepart), new Case(arriveeL, arriveeC, eArrivee)));
    }

    protected void ajouter(Piece p, int l, int c, Epoque e) {
        etats.add(new Etat(p, null, new Case(l, c, e)));
    }

    protected void supprimer(Piece p, int l, int c, Epoque e) {
        etats.add(new Etat(p, new Case(l, c, e), null));
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

        Iterator<Etat> it = etats.descendingIterator();

        while (it.hasNext()) {
            Etat q = it.next();

            if (q.avant() != null) {
                plateau.supprimer(q.avant().ligne(), q.avant().colonne(), q.avant().epoque(), q.piece());
            }

            if (q.apres() != null) {
                if (q.piece() == Piece.ARBRE && q.avant() != null) {
                    int dL = q.apres().ligne() - q.avant().ligne();
                    int dC = q.apres().colonne() - q.avant().colonne();
                    Piece p = Piece.directionArbreCouche(dL, dC);
                    plateau.ajouter(q.apres().ligne(), q.apres().colonne(), q.apres().epoque(), p);
                } else {
                    plateau.ajouter(q.apres().ligne(), q.apres().colonne(), q.apres().epoque(), q.piece());
                }
            }
        }
        // On change la position du pion
        if (etats.element().piece() == joueur.pions().toPiece() && (pion == etats.element().avant() ||
                (pion != null && pion.equals(etats.element().avant())))) {
            pion = etats.element().apres();
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

        for (Etat q : etats) {
            if (q.apres() != null) {
                plateau.supprimer(q.apres().ligne(), q.apres().colonne(), q.apres().epoque(), q.piece());
            }
            if (q.avant() != null) {
                plateau.ajouter(q.avant().ligne(), q.avant().colonne(), q.avant().epoque(), q.piece());
            }
        }
        // On change remet la position de départ du pion
        if (etats.element().piece() == joueur.pions().toPiece() && (pion == etats.element().apres() ||
                (pion != null && pion.equals(etats.element().apres())))) {
            pion = etats.element().avant();
        }
    }
}
