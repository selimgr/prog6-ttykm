package Modele;

// TODO: A tester
// TODO: Mette à jour la position du pion après un coup

class Tour {
    private Case pion;
    private Coup coup1, coup2;

    int lignePion() {
        return pion.ligne();
    }

    int colonnePion() {
        return pion.colonne();
    }

    Epoque epoquePion() {
        return pion.epoque();
    }

    boolean pionSelectione() {
        return pion != null;
    }

    boolean estCommence() {
        return coup1 != null;
    }

    boolean estTermine() {
        return pion != null && coup1 != null && coup2 != null;
    }

    private boolean pionsIdentiques(int l, int c, Epoque e) {
        return l == lignePion() && c == colonnePion() && e == epoquePion();
    }

    void selectionnerPion(int l, int c, Epoque e) {
        if (pionSelectione()) {
            throw new IllegalStateException("Impossible de sélectionner le pion : pion déjà sélectionné");
        }
        if (estCommence()) {
            throw new IllegalStateException("Impossible de sélectionner le pion : tour commencé");
        }
        pion = new Case(l, c, e);
    }

    boolean deselectionnerPion(int l, int c, Epoque e) {
        if (pionsIdentiques(l, c, e) && pionSelectione() && !estCommence()) {
            pion = null;
            return true;
        }
        return false;
    }

    boolean jouerCoup(Coup coup, int destL, int destC, Epoque eDest) {
        if (!pionSelectione()) {
            throw new IllegalStateException("Impossible de jouer un nouveau coup : aucun pion sélectionné");
        }
        if (estTermine()) {
            throw new IllegalStateException("Impossible de jouer un nouveau coup : tour terminé");
        }
        if (!pionsIdentiques(destL, destC, eDest) || !coup.creer(destL, destC, eDest)) {
            return false;
        }

        if (!estCommence()) {
            coup1 = coup;
        } else {
            coup2 = coup;
        }
        coup.jouer();
        return true;
    }

    boolean annulerCoup() {
        if (!estCommence()) {
            return false;
        }
        if (estTermine()) {
            coup2.annuler();
            coup2 = null;
        } else {
            coup1.annuler();
            coup1 = null;
            pion = null;
        }
        return true;
    }
}
