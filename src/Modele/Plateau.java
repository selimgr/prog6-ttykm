package Modele;

public class Plateau {
    private int[][][] contenu;
    public static final int TAILLE = 4;

    Plateau() {
        contenu = new int[Epoque.NOMBRE][TAILLE][TAILLE];

        // ajout des pions dans les coins
        ajouter(0, 0, Epoque.PASSE, Piece.BLANC);
        ajouter(TAILLE - 1, TAILLE - 1, Epoque.FUTUR, Piece.NOIR);
    }

    public boolean aMur(int l, int c) {
        return Math.min(l, c) < 0 && Math.max(l, c) >= TAILLE;
    }

    private void verifierCoordoneesCorrectes(int l, int c, Epoque e) {
        if (aMur(l, c) || e == null) {
            throw new IllegalArgumentException("Coordonnées (" + l + ", " + c + ", " + e + ") incorrectes");
        }
    }

    int contenu(int l, int c, Epoque e) {
        verifierCoordoneesCorrectes(l, c, e);
        return contenu[e.indice()][l][c];
    }

    boolean aPiece(int l, int c, Epoque e, Piece p) {
        return (contenu(l, c, e) & p.valeur()) != 0;
    }

    public boolean estVide(int l, int c, Epoque e) {
        return contenu(l, c, e) == 0;
    }

    public boolean aBlanc(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.BLANC);
    }

    public boolean aNoir(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.NOIR);
    }

    public boolean aPion(int l, int c, Epoque e) {
        return aBlanc(l, c, e) || aNoir(l, c, e);
    }

    public boolean aGraine(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.GRAINE);
    }

    public boolean aArbuste(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBUSTE);
    }

    public boolean aArbre(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE);
    }

    public boolean aArbreCouche(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE_COUCHE);
    }

    public boolean estOccupable(int l, int c, Epoque e) {
        return estVide(l, c, e) || aGraine(l, c, e);
    }

    public boolean estDeplacementCorrect(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dL + dC != 0 && dEpoque == 0;
    }

    public boolean estChangementPlateauCorrect(int dL, int dC,int dEpoque) {
        return (dEpoque == 1 || dEpoque == -1) && dL == 0 && dC == 0;
    }

    public boolean aObstacleMortel(int l, int c, Epoque e, int dL, int dC) {
        if (!estDeplacementCorrect(dL, dC, 0)) {
            throw new IllegalArgumentException("Déplacement incorrect : " + dL + ", " + dC);
        }

        if (aMur(l, c) || aArbuste(l, c, e) || aArbreCouche(l, c, e)) {
            return true;
        }
        if (aArbre(l, c, e)) {
            return aObstacleMortel(l + dL, c + dC, e, dL, dC);
        }
        return false;
    }

    boolean creerDeplacementArbre(Coup coup, int departL, int departC, int arriveeL, int arriveeC, Epoque e) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;

        if (!aArbre(departL, departC, e)) {
            throw new IllegalStateException("Arbre attendu sur la case (" + departL + ", " + departC + ", " + e + ")");
        }
        if (aObstacleMortel(arriveeL, arriveeC, e, dL, dC)) {
            throw new IllegalStateException(
                    "Aucun obstacle mortel attendu après la case (" + arriveeL + ", " + arriveeC + ", " + e + ")"
            );
        }
        if (estVide(arriveeL, arriveeC, e)) {
            coup.suppression(departL, departC, e, Piece.ARBRE);
            coup.ajout(arriveeL, arriveeC, e, Piece.ARBRE_COUCHE);
        }
        else if (aGraine(arriveeL, arriveeC, e) || (aPion(arriveeL, arriveeC, e))) {
            coup.suppression(departL, departC, e, Piece.ARBRE);
            coup.suppression(arriveeL, arriveeC, e, Piece.BLANC);
            coup.suppression(arriveeL, arriveeC, e, Piece.NOIR);
            coup.suppression(arriveeL, arriveeC, e, Piece.GRAINE);
            coup.ajout(arriveeL, arriveeC, e, Piece.ARBRE_COUCHE);
        }
        else if (aArbre(arriveeL, arriveeC, e)) {
            creerDeplacementArbre(coup, arriveeL, arriveeC, arriveeL + dL, arriveeC + dC, e);
            coup.suppression(departL, departC, e, Piece.ARBRE);
            coup.ajout(arriveeL, arriveeC, e, Piece.ARBRE_COUCHE);
        }
        else {
            throw new IllegalStateException("Déplacement invalide");
        }
        return true;
    }

    boolean creerDeplacementPion(Coup coup, int departL, int departC, int arriveeL, int arriveeC, Epoque e) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;
        Piece pion = Piece.depuisValeur(contenu(departL, departC, e));

        if (!aPion(departL, departC, e)) {
            return false;
        }
        if (estOccupable(arriveeL, arriveeC, e)) {
            coup.suppression(departL, departC, e, pion);
            coup.ajout(arriveeL, arriveeC, e, pion);
        }
        else if (aPion(arriveeL, arriveeC, e)) {
            if (pion == Piece.depuisValeur(contenu(arriveeL, arriveeC, e))) {
                coup.suppression(departL, departC, e, pion);
                coup.suppression(arriveeL, arriveeC, e, pion);
            } else {
                creerDeplacementPion(coup, arriveeL, arriveeC, arriveeL + dL, arriveeC + dC, e);
                coup.suppression(departL, departC, e, pion);
                coup.ajout(arriveeL, arriveeC, e, pion);
            }
        }
        else if (aObstacleMortel(arriveeL, arriveeC, e, dL, dC)) {
            coup.suppression(departL, departC, e, pion);
        }
        else if (aArbre(arriveeL, arriveeC, e)) {
            if (!creerDeplacementArbre(coup, arriveeL, arriveeC, arriveeL + dL, arriveeC + dC, e)) {
                return false;
            }
            coup.suppression(departL, departC, e, pion);
            coup.ajout(arriveeL, arriveeC, e, pion);
        } else {
            throw new IllegalStateException("Déplacement invalide");
        }
        return true;
    }

    boolean creerDeplacement(Coup coup, int departL, int departC, int arriveeL, int arriveeC, Epoque e) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;

        if (!estDeplacementCorrect(dL, dC, 0) || aObstacleMortel(arriveeL, arriveeC, e, dL, dC)) {
            return false;
        }
        return creerDeplacementPion(coup, departL, departC, arriveeL, arriveeC, e);
    }

    boolean creerChangementPlateau(Coup coup, int l, int c, Epoque eDepart, Epoque eArrivee) {
        int dEpoque = eDepart.indice() - eArrivee.indice();

        if (estOccupable(l, c, eArrivee)) {
            return false;
        }
        Piece pion = Piece.depuisValeur(contenu(l, c, eDepart));
        coup.ajout(l,c,eArrivee, pion);

        if (dEpoque == 1) {
            coup.suppression(l, c, eDepart, pion);
        }
        return estChangementPlateauCorrect(0, 0, dEpoque);
    }

    void jouerCoup(Coup c) {
        c.jouer();
    }

    void annulerCoup(Coup c) {
        c.annuler();
    }

    int ajout(int l, int c, Epoque e, Piece p) {
        if (aPiece(l, c, e, p) || (!estOccupable(l, c, e))) {
            throw new IllegalStateException(
                    "Impossible d'ajouter la pièce " + p + " sur la case (" + l + ", " + c + ", " + e + ")"
            );
        }
        return contenu(l, c, e) | p.valeur();
    }

    void ajouter(int l, int c, Epoque e, Piece p) {
        contenu[e.indice()][l][c] = ajout(l, c, e, p);
    }

    void fixerCase(int l, int c, Epoque e, int contenu) {
        this.contenu[e.indice()][l][c] = contenu;
    }

    int suppression(int l, int c, Epoque e, Piece p) {
        if (!aPiece(l, c, e, p)) {
            throw new IllegalStateException(
                    "Impossible de supprimer la pièce " + p + " de la case (" + l + ", " + c + ", " + e + ") : pièce absente"
            );
        }
        return contenu(l, c, e) & ~p.valeur();
    }

    void supprimer(int l, int c, Epoque e, Piece p) {
        contenu[e.indice()][l][c] = suppression(l, c, e, p);
    }

    // TODO : Implémenter casesJouables
    // casesJouables(int l, int c)

    // Utile pour l'IA
    public Plateau copie(Plateau n) {
        Plateau retour = new Plateau();

        for (int i = 0; i < Epoque.NOMBRE; i++)
            for (int j = 0; j < TAILLE; j++)
                for (int k = 0; k < TAILLE; k++)
                    retour.contenu[i][j][k] = n.contenu(j, k, Epoque.depuisIndice(i));
        return retour;
    }
}
