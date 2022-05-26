package Modele;

import static java.util.Objects.requireNonNull;

class Tour {
    private final Epoque focus;
    private Case pion;
    private Coup coup1, coup2;
    private Epoque prochainFocus;
    private boolean pionSelectionne;
    private int nombreCoupsRestants;
    private boolean focusChange;

    Tour(Epoque focus) {
        requireNonNull(focus, "L'époque du focus du joueur ne doit pas être null");
        this.focus = focus;
        nombreCoupsRestants = 2;
    }

    Epoque focus() {
        return focus;
    }

    private void verifierPionSelectionne(String message) {
        if (!pionSelectionne()) {
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

    Epoque prochainFocus() {
        if (!termine()) {
            throw new IllegalStateException(
                    "Impossible de renvoyer l'époque du prochain focus : changement de focus non effectué");
        }
        return prochainFocus;
    }

    boolean pionSelectionne() {
        return pionSelectionne;
    }

    int nombreCoupsRestants() {
        return nombreCoupsRestants;
    }

    boolean termine() {
        return focusChange;
    }

    boolean selectionnerPion(int l, int c, Epoque e) {
        if (pionSelectionne()) {
            throw new IllegalStateException("Impossible de sélectionner le pion : état du tour incorrect");
        }
        if (e == focus) {
            pion = new Case(l, c, e);
            pionSelectionne = true;
        }
        return pionSelectionne;
    }

    boolean deselectionnerPion(int l, int c, Epoque e) {
        if (pionSelectionne() && l == lignePion() && c == colonnePion() && e == epoquePion() &&
                nombreCoupsRestants == 2 || termine()) {
            pionSelectionne = false;
            return true;
        }
        return false;
    }

    boolean jouerCoup(Coup coup, int destL, int destC, Epoque eDest) {
        verifierPionSelectionne("Impossible de jouer un nouveau coup");

        if (nombreCoupsRestants == 0 || termine()) {
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

    boolean changerFocus(Epoque prochain) {
        requireNonNull(prochain, "L'époque du prochain focus du joueur ne doit pas être null");

        if (termine()) {
            throw new IllegalStateException("Impossible de changer le focus : tour terminé");
        }

        if (prochain != focus) {
            prochainFocus = prochain;
            focusChange = true;
        }
        return focusChange;
    }

    boolean annuler() {
        if (termine()) {
            focusChange = false;
            return true;
        }

        switch (nombreCoupsRestants) {
            case 0:
                coup2.annuler();
                pion = coup2.pion();
                nombreCoupsRestants++;
                break;
            case 1:
                coup1.annuler();
                pion = coup1.pion();
                nombreCoupsRestants++;
                break;
            case 2:
                if (!pionSelectionne()) {
                    return false;
                }
                pionSelectionne = false;
                break;
            default:
                throw new IllegalStateException("Impossible d'annuler un coup : nombre incorrect de coups joués");
        }
        return true;
    }

    boolean peutRefaire() {
        if (termine()) {
            return false;
        }
        if (!pionSelectionne()) {
            return pion != null || prochainFocus != null;
        }
        switch (nombreCoupsRestants) {
            case 0:
                return prochainFocus != null;
            case 1:
                return coup2 != null || prochainFocus != null;
            case 2:
                return coup1 != null || prochainFocus != null;
            default:
                throw new IllegalStateException("Impossible de refaire un coup : nombre incorrect de coups joués");
        }
    }

    boolean refaire() {
        if (!peutRefaire()) {
            return false;
        }

        if (!pionSelectionne()) {
            if (pion != null) {
                pionSelectionne = true;
            } else {
                focusChange = true;
            }
            return true;
        }
        switch (nombreCoupsRestants) {
            case 0:
                focusChange = true;
                break;
            case 1:
                if (coup2 != null) {
                    coup2.jouer();
                    pion = coup2.pion();
                    nombreCoupsRestants--;
                } else {
                    focusChange = true;
                }
                break;
            case 2:
                if (coup1 != null) {
                    coup1.jouer();
                    pion = coup1.pion();
                    nombreCoupsRestants--;
                } else {
                    focusChange = true;
                }
                break;
        }
        return true;
    }
}
