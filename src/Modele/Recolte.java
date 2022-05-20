package Modele;

// TODO: A tester

public class Recolte extends Coup {

    Recolte(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
    }

    static boolean estCorrecte(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dEpoque == 0;
    }

    @Override
    boolean creer(int destL, int destC, Epoque eDest) {
        verifierPremierCoupCree();

        int dL = destL - pion().ligne();
        int dC = destC - pion().colonne();
        int dEpoque = eDest.indice() - pion().epoque().indice();

        if (!estCorrecte(dL, dC, dEpoque)) {
            throw new IllegalArgumentException("Impossible de créer la récolte : récolte incorrecte");
        }
        if (!plateau().aGraine(destL, destC, eDest)) {
            return false;
        }
        supprimer(Piece.GRAINE, destL, destC, eDest);

        if (eDest.indice() + 1 < Epoque.NOMBRE) {
            Epoque eSuivante = Epoque.depuisIndice(eDest.indice() + 1);

            if (plateau().aArbuste(destL, destC, eSuivante)) {
                supprimer(Piece.ARBUSTE, destL, destC, eSuivante);

                if (eSuivante.indice() + 1 < Epoque.NOMBRE) {
                    eSuivante = Epoque.depuisIndice(eSuivante.indice() + 1);

                    if (plateau().aArbre(destL, destC, eSuivante)) {
                        supprimer(Piece.ARBRE, destL, destC, eSuivante);
                    } else if (plateau().aArbreCouche(destL - 1, destC, eDest)) {
                        supprimer(Piece.ARBRE_COUCHE_HAUT, destL, destC, eSuivante);
                    } else if (plateau().aArbreCouche(destL, destC + 1, eDest)) {
                        supprimer(Piece.ARBRE_COUCHE_DROITE, destL, destC, eSuivante);
                    } else if (plateau().aArbreCouche(destL + 1, destC, eDest)) {
                        supprimer(Piece.ARBRE_COUCHE_BAS, destL, destC, eSuivante);
                    } else if (plateau().aArbreCouche(destL, destC - 1, eDest)) {
                        supprimer(Piece.ARBRE_COUCHE_GAUCHE, destL, destC, eSuivante);
                    }
                }
            }
        }
        return true;
    }
}
