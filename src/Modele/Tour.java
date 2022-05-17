package Modele;

import java.util.NoSuchElementException;

class Tour {
    Coup coup1, coup2;
    Case pion;

    boolean estVide() {
        return coup1 == null && coup2 == null;
    }

    boolean estTermine() {
        return ((coup1 != null) && (coup2 != null));
    }

    boolean jouerCoup(Coup coup, int destL, int destC, Epoque eDest) {
        if (estTermine()) {
            throw new IllegalStateException("Impossible de jouer un nouveau coup : tour terminé");
        }
        if (!coup.creer(destL, destC, eDest)) {
            return false;
        }
        if (estVide()) {
            pion = new Case(coup.lignePion(), coup.colonnePion(), coup.epoquePion());
            coup1 = coup;
        } else {
            if (pion.ligne() != coup.lignePion() || pion.colonne() != coup.colonnePion() ||
                    pion.epoque() != coup.epoquePion()) {
                return false;
            }
            coup2 = coup;
        }
        coup.jouer();
        return true;
    }

    void annulerCoup() {
        if (estVide()) {
            throw new NoSuchElementException("Impossible d'annuler un coup : aucun coup joué ce tour");
        }
        if (estTermine()) {
            coup2.annuler();
            coup2 = null;
        } else {
            coup1.annuler();
            coup1 = null;
            pion = null;
        }
    }
}
