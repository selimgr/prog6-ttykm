package Modele;

public class Mouvement extends Coup {
    private boolean voyageTemporelArriere;
    int dL;
    int dC;
    int dEpoque;

    Mouvement(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
        dL=dC=dEpoque=0;
    }

    static boolean estCorrect(int dL, int dC, int dEpoque) {
        return estDeplacement(dL, dC, dEpoque) || estVoyageTemporel(dL, dC, dEpoque);
    }

    static boolean estDeplacement(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dL + dC != 0 && dEpoque == 0;
    }

    static boolean estVoyageTemporel(int dL, int dC, int dEpoque) {
        return (dEpoque == 1 || dEpoque == -1) && dL == 0 && dC == 0;
    }

    @Override
    boolean creer(int destL, int destC, Epoque eDest) {
        verifierPremierCoupCree();

        dL = destL - lignePion();
        dC = destC - colonnePion();
        dEpoque = eDest.indice() - epoquePion().indice();

        if (joueur().pions() == Pion.BLANC && !plateau().aBlanc(lignePion(), colonnePion(), epoquePion()) ||
                joueur().pions() == Pion.NOIR && !plateau().aNoir(lignePion(), colonnePion(), epoquePion())) {
            return false;
        }

        if (estDeplacement(dL, dC, dEpoque)) {
            return !plateau().aObstacleMortel(destL, destC, epoquePion(), dL, dC) &&
                    creerDeplacementPion(lignePion(), colonnePion(), destL, destC);
        }
        else if (estVoyageTemporel(dL, dC, dEpoque)) {
            return plateau().estOccupable(lignePion(), colonnePion(), eDest) && creerVoyageTemporel(eDest);
        }
        else {
            return false;
        }
    }

    private boolean creerDeplacementPion(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;
        Piece pion = recupererPion(departL, departC, epoquePion());

        // Si le case d'arrivée est occupable, on déplace le pion dessus
        if (plateau().estOccupable(arriveeL, arriveeC, epoquePion())) {
            deplacerPion(departL, departC, arriveeL, arriveeC, pion);
        }
        else if (plateau().aPion(arriveeL, arriveeC, epoquePion())) {
            // Si la case d'arrivée a un pion identique à celui qui joue, on supprime les deux car un paradoxe temporel est créé
            if (pion == recupererPion(arriveeL, arriveeC, epoquePion())) {
                supprimer(arriveeL, arriveeC, epoquePion(), pion);
                supprimer(departL, departC, epoquePion(), pion);
            }
            // Sinon si les pions sont différents, on déplace les deux pions dans la même direction
            // (le pion de départ pousse le pion d'arrivée)
            else {
                if (!creerDeplacementPion(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC)) {
                    return false;
                }
                deplacerPion(departL, departC, arriveeL, arriveeC, pion);
            }
        }
        // Si la case d'arrivée a un obstacle, on supprime le pion car il est poussé sur cette obstacle et meurt
        else if (plateau().aObstacleMortel(arriveeL, arriveeC, epoquePion(), dL, dC)) {
            supprimer(departL, departC, epoquePion(), pion);
        }
        // Si la case d'arrivée a un arbre, on déplace l'arbre et le pion (le pion pousse l'arbre)
        else if (plateau().aArbre(arriveeL, arriveeC, epoquePion())) {
            if (!creerDeplacementArbre(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC)) {
                return false;
            }
            deplacerPion(departL, departC, arriveeL, arriveeC, pion);
        } else {
            throw new IllegalStateException("Impossible de créer le déplacement (" + departL + ", " + departC + ") -> (" +
                    arriveeL + ", " + arriveeC + ") dans le " + epoquePion() + " : déplacement du pion invalide");
        }
        return true;
    }

    private boolean creerDeplacementArbre(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;

        if (!plateau().aArbre(departL, departC, epoquePion())) {
            throw new IllegalStateException("Arbre attendu sur la case (" + departL + ", " + departC + ", " + epoquePion() + ")");
        }
        if (plateau().aObstacleMortel(arriveeL, arriveeC, epoquePion(), dL, dC)) {
            throw new IllegalStateException(
                    "Aucun obstacle mortel attendu après la case (" + arriveeL + ", " + arriveeC + ", " + epoquePion() + ")");
        }

        // Si la case d'arrivée est vide, on supprime l'arbre au départ et ajoute un arbre couché à l'arrivée (l'arbre est poussé)
        if (plateau().estOccupable(arriveeL, arriveeC, epoquePion())) {
            ajouterArbreCouche(arriveeL, arriveeC, dL, dC);

            // Si la case d'arrivée a une graine, on récupère la graine en faisant tomber l'arbre dessus
            if (plateau().aGraine(arriveeL, arriveeC, epoquePion())) {
                supprimer(arriveeL, arriveeC, epoquePion(), Piece.GRAINE);
            }
            supprimer(departL, departC, epoquePion(), Piece.ARBRE);
        }
        // Si la case d'arrivée a un pion, l'arbre tombe sur lui et le tue donc on le supprime ainsi que l'arbre de départ
        // et on ajoute un arbre couché à l'arrivée
        else if (plateau().aPion(arriveeL, arriveeC, epoquePion())) {
            ajouterArbreCouche(arriveeL, arriveeC, dL, dC);
            supprimer(arriveeL, arriveeC, epoquePion(), recupererPion(arriveeL, arriveeC, epoquePion()));
            supprimer(departL, departC, epoquePion(), Piece.ARBRE);
        }
        // Si la case d'arrivée a un arbre, on fait tomber l'arbre si c'est possible
        else if (plateau().aArbre(arriveeL, arriveeC, epoquePion())) {
            if (!creerDeplacementArbre(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC)) {
                return false;
            }
            ajouterArbreCouche(arriveeL, arriveeC, dL, dC);
            supprimer(departL, departC, epoquePion(), Piece.ARBRE);
        }
        else {
            throw new IllegalStateException("Impossible de créer le déplacement (" + departL + ", " + departC + ") -> (" +
                    arriveeL + ", " + arriveeC + ") dans le " + epoquePion() + " : déplacement de l'arbre invalide");
        }
        return true;
    }

    private boolean creerVoyageTemporel(Epoque eDest) {
        int dEpoque = eDest.indice() - epoquePion().indice();
        Piece pion = recupererPion(lignePion(), colonnePion(), epoquePion());

        // On ajoute le pion à la même position sur la nouvelle époque
        ajouter(lignePion(), colonnePion(), eDest, pion);

        // Si le voyage temporel est en avant, on supprime le pion de départ
        if (dEpoque == 1) {
            supprimer(lignePion(), colonnePion(), epoquePion(), pion);
            return true;
        }
        // Si le voyage temporel est en arrière, on laisse le pion de départ car il se duplique, on vérifie alors que
        // le nombre de pions dans la réserve du joueur est suffisant
        voyageTemporelArriere = true;
        return joueur().nombrePionsReserve() > 0;
    }

    private Piece recupererPion(int l, int c, Epoque e) {
        if (plateau().aBlanc(l, c, e)) {
            return Piece.BLANC;
        }
        if (plateau().aNoir(l, c, e)) {
            return Piece.NOIR;
        }
        throw new IllegalStateException("Pion attendu sur la case (" + l + ", " + c + ", " + e + ")");
    }

    private void deplacerPion(int departL, int departC, int arriveeL, int arriveeC, Piece pion) {
        ajouter(arriveeL, arriveeC, epoquePion(), pion);
        supprimer(departL, departC, epoquePion(), pion);
    }

    private void ajouterArbreCouche(int l, int c, int dL, int dC) {
        if (dL == -1 && dC == 0) {
            ajouter(l, c, epoquePion(), Piece.ARBRE_COUCHE_HAUT);
        } else if (dL == 0 && dC == 1) {
            ajouter(l, c, epoquePion(), Piece.ARBRE_COUCHE_DROITE);
        } else if (dL == 1 && dC == 0) {
            ajouter(l, c, epoquePion(), Piece.ARBRE_COUCHE_BAS);
        } else if (dL == 0 && dC == -1) {
            ajouter(l, c, epoquePion(), Piece.ARBRE_COUCHE_GAUCHE);
        } else {
            throw new IllegalArgumentException("Impossible d'ajouter l'arbre couché : direction incorrecte");
        }
    }

    @Override
    public void jouer() {
        super.jouer();

        if (voyageTemporelArriere) {
            joueur().enleverPionReserve();
        }
    }

    @Override
    public void annuler() {
        super.annuler();

        if (voyageTemporelArriere) {
            joueur().ajouterPionReserve();
        }
    }
}
