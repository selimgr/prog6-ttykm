package Modele;

// TODO: A tester

public class Plantation extends Coup {

    Plantation(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
    }

    static boolean estCorrecte(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dEpoque == 0;
    }

    private boolean estPlantable(int l, int c, Epoque e) {
        return plateau().estVide(l, c, e) || (plateau().aPion(l, c, e) && !plateau().aGraine(l, c, e));
    }

    @Override
    boolean creer(int destL, int destC, Epoque eDest) {
        verifierPremierCoupCree();

        int dL = destL - lignePion();
        int dC = destC - colonnePion();
        int dEpoque = eDest.indice() - epoquePion().indice();

        if (!estCorrecte(dL, dC, dEpoque) || !estPlantable(destL, destC, eDest) || plateau().nombreGrainesReserve() == 0) {
            return false;
        }
        ajouter(destL, destC, eDest, Piece.GRAINE);

        if (eDest.indice() + 1 < Epoque.NOMBRE) {
            Epoque eSuivante = Epoque.depuisIndice(eDest.indice() + 1);

            if (estPlantable(destL, destC, eSuivante)) {
                ajouter(destL, destC, eSuivante, Piece.ARBUSTE);

                if (eSuivante.indice() + 1 < Epoque.NOMBRE) {
                    eSuivante = Epoque.depuisIndice(eSuivante.indice() + 1);

                    if (estPlantable(destL, destC, eSuivante)) {
                        ajouter(destL, destC, eSuivante, Piece.ARBRE);
                    }
                }
            }
        }
        return true;
    }
}
