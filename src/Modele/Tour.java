package Modele;

class Tour {
    private Case pion;
    private Coup coup1, coup2;

    int lignePion() {
        if (pion == null) {
            throw new IllegalStateException("Impossible de renvoyer la ligne du pion : pion non sélectionné");
        }
        return pion.ligne();
    }

    int colonnePion() {
        if (pion == null) {
            throw new IllegalStateException("Impossible de renvoyer la colonne du pion : pion non sélectionné");
        }
        return pion.colonne();
    }

    Epoque epoquePion() {
        if (pion == null) {
            throw new IllegalStateException("Impossible de renvoyer l'époque du pion : pion non sélectionné");
        }
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

    void selectionnerPion(int l, int c, Epoque e) {
        if (pionSelectione()) {
            throw new IllegalStateException("Impossible de sélectionner le pion : pion déjà sélectionné");
        }
        pion = new Case(l, c, e);
    }

    boolean deselectionnerPion(int l, int c, Epoque e) {
        if (pionSelectione() && l == lignePion() && c == colonnePion() && e == epoquePion() && !estCommence()) {
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
        if (!coup.creer(destL, destC, eDest)) {
            return false;
        }

        if (!estCommence()) {
            coup1 = coup;
        } else {
            coup2 = coup;
        }
        coup.jouer();
        pion = coup.pion();
        return true;
    }

    boolean annulerCoup() {
        if (!estCommence()) {
            if (pionSelectione()) {
                pion = null;
                return true;
            }
            return false;
        }
        if (estTermine()) {
            coup2.annuler();
            pion = coup2.pion();
            coup2 = null;
        } else {
            coup1.annuler();
            pion = coup1.pion();
            coup1 = null;
        }
        return true;
    }
}
