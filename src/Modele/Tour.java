package Modele;

import static java.util.Objects.requireNonNull;

class Tour {
    private final Epoque focus;
    private Case pion;
    private Coup coup1, coup2;
    private int nombreCoupsRestants;

    Tour(Epoque focus) {
        requireNonNull(focus);
        this.focus = focus;
        nombreCoupsRestants = 2;
    }

    Epoque focus() {
        return focus;
    }

    private void verifierPionSelectionne(String message) {
        if (pion == null) {
            throw new IllegalStateException(message + " : pion non sélectionné");
        }
    }

    int lignePion() {
        verifierPionSelectionne("Impossible de renvoyer la ligne du pion");
        return pion.ligne();
    }

    int colonnePion() {
        verifierPionSelectionne("Impossible de renvoyer la colonne du pion");
        return pion.colonne();
    }

    Epoque epoquePion() {
        verifierPionSelectionne("Impossible de renvoyer l'époque du pion");
        return pion.epoque();
    }

    boolean pionSelectionne() {
        return pion != null;
    }

    int nombreCoupsRestants() {
        return nombreCoupsRestants;
    }

    void selectionnerPion(int l, int c, Epoque e) {
        if (pionSelectionne()) {
            throw new IllegalStateException("Impossible de sélectionner le pion : pion déjà sélectionné");
        }
        if (e != focus) {
            throw new IllegalArgumentException("Impossible de sélectionner le pion : époque différente du focus actuel");
        }
        pion = new Case(l, c, e);
    }

    boolean deselectionnerPion(int l, int c, Epoque e) {
        if (pionSelectionne() && l == lignePion() && c == colonnePion() && e == epoquePion() && nombreCoupsRestants == 2) {
            pion = null;
            return true;
        }
        return false;
    }

    boolean jouerCoup(Coup coup, int destL, int destC, Epoque eDest) {
        verifierPionSelectionne("Impossible de jouer un nouveau coup");

        if (nombreCoupsRestants == 0) {
            throw new IllegalStateException("Impossible de jouer un nouveau coup : tous les coups ont déjà été joués ce tour");
        }
        if (!coup.creer(destL, destC, eDest)) {
            return false;
        }

        if (nombreCoupsRestants == 2) {
            coup1 = coup;
        } else {
            coup2 = coup;
        }
        coup.jouer();
        pion = coup.pion();
        nombreCoupsRestants--;
        return true;
    }

    boolean annulerCoup() {
        switch (nombreCoupsRestants) {
            case 0:
                coup2.annuler();
                pion = coup2.pion();
                coup2 = null;
                nombreCoupsRestants++;
                break;
            case 1:
                coup1.annuler();
                pion = coup1.pion();
                coup1 = null;
                nombreCoupsRestants++;
                break;
            case 2:
                if (!pionSelectionne()) {
                    return false;
                }
                pion = null;
                break;
            default:
                throw new IllegalStateException("Impossible d'annuler un coup : nombre incorrect de coups joués");
        }
        return true;
    }
}
