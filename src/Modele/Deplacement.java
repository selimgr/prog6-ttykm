package Modele;

/**
 * Gérer ordres des mouvements
 */
public class Deplacement extends Coup {

    Deplacement(Plateau plateau) {
        super(plateau);
    }

    void ajout(int l, int c, Epoque e, Piece p) {
        ajouterEtat(l, c, e, plateau.contenu(l, c, e), plateau.ajout(l, c, e, p));
    }

    void suppression(int l, int c, Epoque e, Piece p) {
        ajouterEtat(l, c, e, plateau.contenu(l, c, e), plateau.suppression(l, c, e, p));
    }

    @Override
    public void jouer() {

    }

    @Override
    public void annuler() {

    }

}
