package Vue;

import Modele.Jeu;
import Modele.TypeJoueur;
import Modele.Pion;

public interface CollecteurEvenements {
    void fixerMediateurVues(Vues v);

    void afficherDemarrage();

    void afficherMenuPrincipal();

    void afficherMenuNouvellePartie();

    void afficherJeu();

    void nouvellePartie(String nomJ1, TypeJoueur typeJ1, Pion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, Pion pionsJ2, int handicapJ2);

    void partieSuivante();

    Jeu jeu();

    void clicSouris(int l, int c);

    void toucheClavier(String touche);

    void temps();
}
