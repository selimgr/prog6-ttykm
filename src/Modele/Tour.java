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

    Case pion() {
        return pion;
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
            coup1 = null;
            coup2 = null;
            focusChange = false;
        }
        return pionSelectionne;
    }

    boolean deselectionnerPion(int l, int c, Epoque e) {
        if (pionSelectionne() && l == pion.ligne() && c == pion.colonne() && e == pion.epoque() &&
                nombreCoupsRestants == 2 || termine()) {
            pionSelectionne = false;
            return true;
        }
        return false;
    }

    boolean jouerCoup(Coup coup, int destL, int destC, Epoque eDest) {
        if (!pionSelectionne()) {
            throw new IllegalStateException("Impossible de jouer un nouveau coup : pion non sélectionné");
        }
        if (nombreCoupsRestants == 0 || termine()) {
            throw new IllegalStateException("Impossible de jouer un nouveau coup : tous les coups ont déjà été joués ce tour");
        }

        if (!coup.creer(destL, destC, eDest)) {
            return false;
        }

        if (nombreCoupsRestants == 2) {
            System.out.println("Jouer coup 1  ");
            coup1 = coup;
            coup2 = null;
        } else {
            System.out.println("    Jouer coup 2  ");

            coup2 = coup;
        }
        coup.jouer();
        pion = coup.pion();
        nombreCoupsRestants--;
        prochainFocus = null;
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
                System.out.println("    Annuler coup 2  ");
                coup2.annuler();
                pion = coup2.pion();
                nombreCoupsRestants++;
                break;
            case 1:
                System.out.println("Annuler coup 1  ");
                coup1.annuler();
                pion = coup1.pion();
                nombreCoupsRestants++;
                break;
            case 2:
                if (!pionSelectionne()) {
                    return false;
                }
                System.out.println("Annuler Selection");
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
