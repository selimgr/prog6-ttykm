package Modele;

public class Mouvement extends Coup {
    private final Case departPion;
    private Case arriveePion;
    private boolean positionPionChangee;
    private boolean voyageTemporelArriere;

    Mouvement(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
        departPion = pion();
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
    Case pion() {
        if (positionPionChangee) {
            return arriveePion;
        }
        return super.pion();
    }

    @Override
    public Case depart() {
        verifierCoupCree("Impossible de récupérer la case de départ");
        return departPion;
    }

    @Override
    public Case arrivee() {
        verifierCoupCree("Impossible de récupérer la case d'arrivée");
        return arriveePion;
    }

    @Override
    boolean creer(int destL, int destC, Epoque eDest) {
        verifierAucunCoupCree();

        int dL = destL - pion().ligne();
        int dC = destC - pion().colonne();
        int dEpoque = eDest.indice() - pion().epoque().indice();

        if (Plateau.aMur(destL, destC) || (
                (joueur().pions() == Pion.BLANC && !plateau().aBlanc(pion().ligne(), pion().colonne(), pion().epoque())) ||
                        (joueur().pions() == Pion.NOIR && !plateau().aNoir(pion().ligne(), pion().colonne(), pion().epoque())))) {
            return false;
        }
        arriveePion = new Case(destL, destC, eDest);

        if (estDeplacement(dL, dC, dEpoque)) {
            return !plateau().aObstacleMortel(destL, destC, pion().epoque(), dL, dC) &&
                    creerDeplacementPion(pion().ligne(), pion().colonne(), destL, destC);
        }
        if (estVoyageTemporel(dL, dC, dEpoque)) {
            return plateau().estOccupable(pion().ligne(), pion().colonne(), eDest) && creerVoyageTemporel(eDest);
        }
        return false;
    }

    private boolean creerDeplacementPion(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;
        Piece pion = recupererPion(departL, departC, pion().epoque());

        // Si la case d'arrivée a un obstacle, on supprime le pion car il est poussé sur cette obstacle et meurt
        if (plateau().aObstacleMortel(arriveeL, arriveeC, pion().epoque(), dL, dC)) {
            supprimer(pion, departL, departC, pion().epoque());
        }
        // Si le case d'arrivée est occupable, on déplace le pion dessus
        else if (plateau().estOccupable(arriveeL, arriveeC, pion().epoque())) {
            deplacer(pion, departL, departC, pion().epoque(), arriveeL, arriveeC, pion().epoque());
        }
        else if (plateau().aPion(arriveeL, arriveeC, pion().epoque())) {
            // Si les pions sont différents, on déplace les deux pions dans la même direction
            // (le pion de départ pousse le pion d'arrivée)
            if (pion != recupererPion(arriveeL, arriveeC, pion().epoque())) {
                deplacer(pion, departL, departC, pion().epoque(), arriveeL, arriveeC, pion().epoque());
                return creerDeplacementPion(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC);
            }
            // Sinon la case d'arrivée a un pion identique à celui qui joue,
            // on supprime les deux car un paradoxe temporel est créé
            supprimer(pion, departL, departC, pion().epoque());
            supprimer(pion, arriveeL, arriveeC, pion().epoque());
        }
        // Si la case d'arrivée a un arbre, on déplace l'arbre et le pion (le pion pousse l'arbre)
        else if (plateau().aArbre(arriveeL, arriveeC, pion().epoque())) {
            deplacer(pion, departL, departC, pion().epoque(), arriveeL, arriveeC, pion().epoque());
            return creerDeplacementArbre(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC);
        }
        else {
            throw new IllegalStateException("Impossible de créer le déplacement (" + departL + ", " + departC + ") -> (" +
                    arriveeL + ", " + arriveeC + ") dans le " + pion().epoque() + " : déplacement du pion invalide");
        }
        return true;
    }

    private boolean creerDeplacementArbre(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;

        if (!plateau().aArbre(departL, departC, pion().epoque())) {
            throw new IllegalStateException("Arbre attendu sur la case (" + departL + ", " + departC + ", " + pion().epoque() + ")");
        }
        if (plateau().aObstacleMortel(arriveeL, arriveeC, pion().epoque(), dL, dC)) {
            throw new IllegalStateException(
                    "Aucun obstacle mortel attendu après la case (" + arriveeL + ", " + arriveeC + ", " + pion().epoque() + ")");
        }

        // Si la case d'arrivée est vide, a une graine ou a un pion, on déplace l'arbre (on le fait tomber)
        if (plateau().estOccupable(arriveeL, arriveeC, pion().epoque()) || plateau().aPion(arriveeL, arriveeC, pion().epoque())) {
            deplacer(Piece.ARBRE, departL, departC, pion().epoque(), arriveeL, arriveeC, pion().epoque());

            // Si la case d'arrivée a une graine, on récupère la graine en faisant tomber l'arbre dessus
            if (plateau().aGraine(arriveeL, arriveeC, pion().epoque())) {
                supprimer(Piece.GRAINE, arriveeL, arriveeC, pion().epoque());
            }
            // Si la case d'arrivée a un pion, l'arbre tombe sur lui et le tue donc on le supprime
            if (plateau().aPion(arriveeL, arriveeC, pion().epoque())) {
                supprimer(recupererPion(arriveeL, arriveeC, pion().epoque()), arriveeL, arriveeC, pion().epoque());
            }
        }
        // Si la case d'arrivée a un arbre, on fait tomber l'arbre si c'est possible
        else if (plateau().aArbre(arriveeL, arriveeC, pion().epoque())) {
            deplacer(Piece.ARBRE, departL, departC, pion().epoque(), arriveeL, arriveeC, pion().epoque());
            return creerDeplacementArbre(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC);
        }
        else {
            throw new IllegalStateException("Impossible de créer le déplacement (" + departL + ", " + departC + ") -> (" +
                    arriveeL + ", " + arriveeC + ") dans le " + pion().epoque() + " : déplacement de l'arbre invalide");
        }
        return true;
    }

    private boolean creerVoyageTemporel(Epoque eDest) {
        int dEpoque = eDest.indice() - pion().epoque().indice();
        Piece pion = recupererPion(pion().ligne(), pion().colonne(), pion().epoque());

        // Si le voyage temporel est en arrière, on ajoute le pion de départ car il se duplique, on vérifie alors que
        // le nombre de pions dans la réserve du joueur est suffisant
        if (dEpoque == -1) {
            ajouter(pion, pion().ligne(), pion().colonne(), pion().epoque());

            if (joueur().nombrePionsReserve() <= 0) {
                return false;
            }
            voyageTemporelArriere = true;
        }

        // On déplace le pion à la même position sur la nouvelle époque
        deplacer(pion, pion().ligne(), pion().colonne(), pion().epoque(), pion().ligne(), pion().colonne(), eDest);
        return true;
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

    @Override
    public void jouer() {
        super.jouer();

        if (voyageTemporelArriere) {
            joueur().enleverPionReserve();
        }
        // On change la position du pion
        positionPionChangee = true;
    }

    @Override
    public void annuler() {
        super.annuler();

        if (voyageTemporelArriere) {
            joueur().ajouterPionReserve();
        }
        // On change remet la position de départ du pion
        positionPionChangee = false;
    }
}
