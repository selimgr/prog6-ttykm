package Modele;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public abstract class Coup {
    private final Plateau plateau;
    private final Joueur joueur;
    private final Case pion;
    private final Deque<Etat> etats;
    private boolean coupJoue;
    private int[] alphaBeta;

    Coup(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        plateau = p;
        joueur = j;
        pion = new Case(pionL, pionC, ePion);
        etats = new ArrayDeque<>();
        alphaBeta = new int[300];
    }

    Plateau plateau() {
        return plateau;
    }

    Joueur joueur() {
        return joueur;
    }

    public Case pion() {
        return pion;
    }

    private void verifierCoupCree(String message) {
        if (etats.isEmpty()) {
            throw new IllegalStateException(message + " : aucun coup créé");
        }
    }

    public boolean estMouvement() {
        verifierCoupCree("Impossible de vérifier si le coup est un mouvement");
        return etats.element().piece() == Piece.BLANC || etats.element().piece() == Piece.NOIR;
    }

    public boolean estPlantation() {
        verifierCoupCree("Impossible de vérifier si le coup est une plantation");
        return etats.element().piece() == Piece.GRAINE && etats.element().depart() == null;
    }

    public boolean estRecolte() {
        verifierCoupCree("Impossible de vérifier si le coup est une récolte");
        return etats.element().piece() == Piece.GRAINE && etats.element().arrivee() == null;
    }

    public Case depart() {
        return etats.element().depart();
    }

    public Case arrivee() {
        return etats.element().arrivee();
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

    protected void verifierAucunCoupCree() {
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

        // On parcourt les états dans le sens inverse
        Iterator<Etat> it = etats.descendingIterator();

        while (it.hasNext()) {
            Etat q = it.next();

            // On supprime la pièce sur la case de départ
            if (q.depart() != null) {
                plateau.supprimer(q.depart().ligne(), q.depart().colonne(), q.depart().epoque(), q.piece());
            }

            if (q.arrivee() != null) {
                // Si la pièce est un arbre, on ajoute l'arbre couché correspondant sur la case d'arrivée
                if (q.piece() == Piece.ARBRE && q.depart() != null) {
                    int dL = q.arrivee().ligne() - q.depart().ligne();
                    int dC = q.arrivee().colonne() - q.depart().colonne();
                    Piece p = Piece.directionArbreCouche(dL, dC);
                    plateau.ajouter(q.arrivee().ligne(), q.arrivee().colonne(), q.arrivee().epoque(), p);
                }
                // Sinon on ajoute la pièce
                else {
                    plateau.ajouter(q.arrivee().ligne(), q.arrivee().colonne(), q.arrivee().epoque(), q.piece());
                }
            }
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
            if (q.arrivee() != null) {
                // Si la pièce est un arbre, on supprime l'arbre couché correspondant sur la case d'arrivée
                if (q.piece() == Piece.ARBRE && q.depart() != null) {
                    int dL = q.arrivee().ligne() - q.depart().ligne();
                    int dC = q.arrivee().colonne() - q.depart().colonne();
                    Piece p = Piece.directionArbreCouche(dL, dC);
                    plateau.supprimer(q.arrivee().ligne(), q.arrivee().colonne(), q.arrivee().epoque(), p);
                }
                // Sinon on supprime la pièce
                else {
                    plateau.supprimer(q.arrivee().ligne(), q.arrivee().colonne(), q.arrivee().epoque(), q.piece());
                }
            }

            // On remet la pièce sur la case de départ
            if (q.depart() != null) {
                plateau.ajouter(q.depart().ligne(), q.depart().colonne(), q.depart().epoque(), q.piece());
            }
        }
    }
}
