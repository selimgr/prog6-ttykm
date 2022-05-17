package Modele;

public class Mouvement extends Coup {
    boolean voyageTemporelArriere;
    boolean graineRecuperee;
    int[] dBlancPlateau, dNoirPlateau;

    Mouvement(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
        dBlancPlateau = new int[Epoque.NOMBRE];
        dNoirPlateau = new int[Epoque.NOMBRE];
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
        int dL = destL - pionL;
        int dC = destC - pionC;
        int dEpoque = eDest.indice() - ePion.indice();

        if (estDeplacement(dL, dC, dEpoque)) {
            return !plateau.aObstacleMortel(destL, destC, ePion, dL, dC) &&
                    creerDeplacementPion(pionL, pionC, destL, destC);
        }
        else if (estVoyageTemporel(dL, dC, dEpoque)) {
            if (plateau.estOccupable(pionL, pionC, eDest)) {
                return false;
            }
            Piece pion = Piece.depuisValeur(plateau.contenu(pionL, pionC, ePion));
            ajouterPionPlateau(pionL, pionC, eDest, pion);

            if (dEpoque == 1) {
                supprimerPionPlateau(pionL, pionC, ePion, pion);
                return true;
            }
            voyageTemporelArriere = true;
            return joueur.nombrePionsReserve() > 0;
        }
        else {
            throw new IllegalArgumentException("Impossible de créer le mouvement : mouvement incorrect");
        }
    }

    private boolean creerDeplacementPion(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;
        if (!plateau.aPion(departL, departC, ePion)) {
            return false;
        }
        Piece pion = Piece.depuisValeur(plateau.contenu(departL, departC, ePion));

        if (plateau.estOccupable(arriveeL, arriveeC, ePion)) {
            deplacerPion(departL, departC, arriveeL, arriveeC, pion);
        }
        else if (plateau.aPion(arriveeL, arriveeC, ePion)) {
            if (pion == Piece.depuisValeur(plateau.contenu(arriveeL, arriveeC, ePion))) {
                supprimerPionPlateau(departL, departC, ePion, pion);
                supprimerPionPlateau(arriveeL, arriveeC, ePion, pion);
            } else {
                if (!creerDeplacementPion(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC)) {
                    return false;
                }
                deplacerPion(departL, departC, arriveeL, arriveeC, pion);
            }
        }
        else if (plateau.aObstacleMortel(arriveeL, arriveeC, ePion, dL, dC)) {
            supprimerPionPlateau(departL, departC, ePion, pion);
        }
        else if (plateau.aArbre(arriveeL, arriveeC, ePion)) {
            if (!creerDeplacementArbre(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC)) {
                return false;
            }
            deplacerPion(departL, departC, arriveeL, arriveeC, pion);
        } else {
            throw new IllegalStateException("Déplacement invalide");
        }
        return true;
    }

    private boolean creerDeplacementArbre(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;

        if (!plateau.aArbre(departL, departC, ePion)) {
            throw new IllegalStateException("Arbre attendu sur la case (" + departL + ", " + departC + ", " + ePion + ")");
        }
        if (plateau.aObstacleMortel(arriveeL, arriveeC, ePion, dL, dC)) {
            throw new IllegalStateException(
                    "Aucun obstacle mortel attendu après la case (" + arriveeL + ", " + arriveeC + ", " + ePion + ")"
            );
        }
        if (plateau.estVide(arriveeL, arriveeC, ePion)) {
            supprimer(departL, departC, ePion, Piece.ARBRE);
            ajouterArbreCouche(arriveeL, arriveeC, dL, dC);
        }
        else if (plateau.aGraine(arriveeL, arriveeC, ePion)) {
            supprimer(departL, departC, ePion, Piece.ARBRE);
            supprimer(arriveeL, arriveeC, ePion, Piece.GRAINE);
            graineRecuperee = true;
        }
        else if (plateau.aPion(arriveeL, arriveeC, ePion)) {
            supprimer(departL, departC, ePion, Piece.ARBRE);
            Piece pion = Piece.depuisValeur(plateau.contenu(arriveeL, arriveeC, ePion));
            supprimerPionPlateau(arriveeL, arriveeC, ePion, pion);
            ajouterArbreCouche(arriveeL, arriveeC, dL, dC);
        }
        else if (plateau.aArbre(arriveeL, arriveeC, ePion)) {
            if (!creerDeplacementArbre(arriveeL, arriveeC, arriveeL + dL, arriveeC + dC)) {
                return false;
            }
            supprimer(departL, departC, ePion, Piece.ARBRE);
            ajouterArbreCouche(arriveeL, arriveeC, dL, dC);
        }
        else {
            throw new IllegalStateException("Déplacement invalide");
        }
        return true;
    }

    private void deplacerPion(int departL, int departC, int arriveeL, int arriveeC, Piece pion) {
        supprimer(departL, departC, ePion, pion);
        ajouter(arriveeL, arriveeC, ePion, pion);
    }

    private void ajouterPionPlateau(int l, int c, Epoque e, Piece pion) {
        ajouter(l, c, e, pion);

        switch (pion) {
            case BLANC:
                dBlancPlateau[e.indice()]++;
            case NOIR:
                dNoirPlateau[e.indice()]++;
            default:
                throw new IllegalArgumentException("Pion attendu");
        }
    }

    private void supprimerPionPlateau(int l, int c, Epoque e, Piece pion) {
        supprimer(l, c, e, pion);

        switch (pion) {
            case BLANC:
                dBlancPlateau[e.indice()]--;
            case NOIR:
                dNoirPlateau[e.indice()]--;
            default:
                throw new IllegalArgumentException("Pion attendu");
        }
    }

    private void ajouterArbreCouche(int l, int c, int dL, int dC) {
        if (dL == -1 && dC == 0) {
            ajouter(l, c, ePion, Piece.ARBRE_COUCHE_HAUT);
        } else if (dL == 0 && dC == 1) {
            ajouter(l, c, ePion, Piece.ARBRE_COUCHE_DROITE);
        } else if (dL == 1 && dC == 0) {
            ajouter(l, c, ePion, Piece.ARBRE_COUCHE_BAS);
        } else if (dL == 0 && dC == -1) {
            ajouter(l, c, ePion, Piece.ARBRE_COUCHE_GAUCHE);
        } else {
            throw new IllegalArgumentException("Impossible d'ajouter l'arbre couché : direction incorrecte");
        }
    }

    @Override
    public void jouer() {
        super.jouer();

        if (voyageTemporelArriere) {
            joueur.enleverPionReserve();
        }
        if (graineRecuperee) {
            plateau.ajouterGraineReserve();
        }
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            if(dBlancPlateau[i]!=0) {
                plateau.modifierNombrePionPlateau(Piece.BLANC, Epoque.depuisIndice(i), dBlancPlateau[i]);
            }
            if(dNoirPlateau[i]!=0) {
                plateau.modifierNombrePionPlateau(Piece.NOIR, Epoque.depuisIndice(i), dNoirPlateau[i]);
            }
        }
    }

    @Override
    public void annuler() {
        super.annuler();

        if (voyageTemporelArriere) {
            joueur.ajouterPionReserve();
        }
        if (graineRecuperee) {
            plateau.enleverGraineReserve();
        }
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            plateau.modifierNombrePionPlateau(Piece.BLANC, Epoque.depuisIndice(i), -dBlancPlateau[i]);
            plateau.modifierNombrePionPlateau(Piece.NOIR, Epoque.depuisIndice(i), -dNoirPlateau[i]);
        }
    }
}
