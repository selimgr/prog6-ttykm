package Modele;

public class Recolte extends Coup {

    Recolte(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
    }

    static boolean estCorrecte(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dEpoque == 0;
    }

    @Override
    boolean creer(int destL, int destC, Epoque eDest) {
        int dL = destL - pionL;
        int dC = destC - pionC;
        int dEpoque = eDest.indice() - ePion.indice();

        if (!estCorrecte(dL, dC, dEpoque)) {
            throw new IllegalArgumentException("Impossible de créer la récolte : récolte incorrecte");
        }
        if (!plateau.aGraine(destL, destC, eDest)) {
            return false;
        }
        supprimer(destL, destC, eDest, Piece.GRAINE);

        if (eDest.indice() + 1 < Epoque.NOMBRE) {
            Epoque eSuivante = Epoque.depuisIndice(eDest.indice() + 1);

            if (plateau.aArbuste(destL, destC, eSuivante)) {
                supprimer(destL, destC, eSuivante, Piece.ARBUSTE);

                if (eSuivante.indice() + 1 < Epoque.NOMBRE) {
                    eSuivante = Epoque.depuisIndice(eSuivante.indice() + 1);

                    if (plateau.aArbre(destL, destC, eSuivante)) {
                        supprimer(destL, destC, eSuivante, Piece.ARBRE);
                    } else if (plateau.aArbreCouche(destL - 1, destC, eDest)) {
                        supprimer(destL, destC, eSuivante, Piece.ARBRE_COUCHE_HAUT);
                    } else if (plateau.aArbreCouche(destL, destC + 1, eDest)) {
                        supprimer(destL, destC, eSuivante, Piece.ARBRE_COUCHE_DROITE);
                    } else if (plateau.aArbreCouche(destL + 1, destC, eDest)) {
                        supprimer(destL, destC, eSuivante, Piece.ARBRE_COUCHE_BAS);
                    } else if (plateau.aArbreCouche(destL, destC - 1, eDest)) {
                        supprimer(destL, destC, eSuivante, Piece.ARBRE_COUCHE_GAUCHE);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void jouer() {
        super.jouer();
        plateau.ajouterGraineReserve();
    }

    @Override
    public void annuler() {
        super.annuler();
        plateau.enleverGraineReserve();
    }
}
