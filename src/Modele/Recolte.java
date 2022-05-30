package Modele;

public class Recolte extends Coup {
    private Case graine;

    Recolte(Plateau p, Joueur j, int pionL, int pionC, Epoque ePion) {
        super(p, j, pionL, pionC, ePion);
    }

    static boolean estCorrecte(int dL, int dC, int dEpoque) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dEpoque == 0;
    }

    @Override
    public Case depart() {
        verifierCoupCree("Impossible de récupérer la case de départ");
        return graine;
    }

    @Override
    public Case arrivee() {
        verifierCoupCree("Impossible de récupérer la case d'arrivée");
        return null;
    }

    @Override
    public boolean creer(int destL, int destC, Epoque eDest) {
        verifierAucunCoupCree();

        int dL = destL - pion().ligne();
        int dC = destC - pion().colonne();
        int dEpoque = eDest.indice() - pion().epoque().indice();

        if (!estCorrecte(dL, dC, dEpoque) || !plateau().aGraine(destL, destC, eDest)) {
            return false;
        }
        graine = new Case(destL, destC, eDest);
        supprimer(Piece.GRAINE, destL, destC, eDest);

        if (eDest.indice() + 1 < Epoque.NOMBRE) {
            Epoque eSuivante = Epoque.depuisIndice(eDest.indice() + 1);

            if (plateau().aArbuste(destL, destC, eSuivante)) {
                supprimer(Piece.ARBUSTE, destL, destC, eSuivante);

                if (eSuivante.indice() + 1 < Epoque.NOMBRE) {
                    eSuivante = Epoque.depuisIndice(eSuivante.indice() + 1);

                    if (plateau().aArbre(destL, destC, eSuivante)) {
                        supprimer(Piece.ARBRE, destL, destC, eSuivante);
                    }
                    else if (destL > 0 && plateau().aArbreCoucheVersLeHaut(destL - 1, destC, eSuivante)) {
                        supprimer(Piece.ARBRE_COUCHE_HAUT, destL - 1, destC, eSuivante);
                    }
                    else if (destC < Plateau.TAILLE - 1 && plateau().aArbreCoucheVersLaDroite(destL, destC + 1, eSuivante)) {
                        supprimer(Piece.ARBRE_COUCHE_DROITE, destL, destC + 1, eSuivante);
                    }
                    else if (destL < Plateau.TAILLE - 1 && plateau().aArbreCoucheVersLeBas(destL + 1, destC, eSuivante)) {
                        supprimer(Piece.ARBRE_COUCHE_BAS, destL + 1, destC, eSuivante);
                    }
                    else if (destC > 0 && plateau().aArbreCoucheVersLaGauche(destL, destC - 1, eSuivante)) {
                        supprimer(Piece.ARBRE_COUCHE_GAUCHE, destL, destC - 1, eSuivante);
                    }
                }
            }
        }
        return true;
    }
}
