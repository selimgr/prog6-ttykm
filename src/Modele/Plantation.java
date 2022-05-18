package Modele;

import Controleur.Action;

public class Plantation extends Coup {

    Plantation(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
        a = Action.PLANTATION;

    }

    static boolean estCorrecte(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dEpoque == 0;
    }

    private boolean estPlantable(int l, int c, Epoque e) {
        return !plateau.aGraine(l, c, e) && !plateau.aArbuste(l, c, e) &&
                !plateau.aArbre(l, c, e) && !plateau.aArbreCouche(l, c, e);
    }

    @Override
    boolean creer(int destL, int destC, Epoque eDest) {
        int dL = destL - pionL;
        int dC = destC - pionC;
        int dEpoque = eDest.indice() - ePion.indice();

        if (!estCorrecte(dL, dC, dEpoque)) {
            throw new IllegalArgumentException("Impossible de créer la plantation : plantation incorrecte");
        }
        if (!estPlantable(destL, destC, eDest) || plateau.nombreGrainesReserve() == 0) {
            return false;
        }
        ajouter(destL, destC, eDest, Piece.GRAINE);

        if (eDest.indice() + 1 < Epoque.NOMBRE) {
            Epoque eSuivante = Epoque.depuisIndice(eDest.indice() + 1);

            if (estPlantable(destL, destC, eSuivante)) {
                ajouter(destL, destC, eSuivante, Piece.ARBUSTE);

                if (eSuivante.indice() + 1 < Epoque.NOMBRE) {
                    eSuivante = Epoque.depuisIndice(eSuivante.indice() + 1);

                    if (estPlantable(destL, destC, Epoque.depuisIndice(eDest.indice() + 2))) {
                        ajouter(destL, destC, eSuivante, Piece.ARBRE);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void jouer() {
        super.jouer();
        plateau.enleverGraineReserve();
    }

    @Override
    public void annuler() {
        super.annuler();
        plateau.ajouterGraineReserve();
    }
}
