package Vue;

import Controleur.Action;
import Modele.*;

public interface CollecteurEvenements {
    void fixerMediateurVues(Vues v);

    void afficherDemarrage();

    void afficherMenuPrincipal();

    void afficherMenuNouvellePartie();

    void afficherJeu();

    void nouvellePartie(String nomJ1, TypeJoueur typeJ1, Pion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, Pion pionsJ2, int handicapJ2);

    void partieSuivante();

    Jeu jeu();

    void toClose();

    void selectionnerPion(int l, int c, Epoque e);

    void deplacer(int l, int c, Epoque e, Plateau p);

    void planterGraine(int l, int c, Plateau p);

    void recolterGraine(int l, int c, Plateau p);

    public void fixerAction(Action a);

    void clicSouris(int l, int c, Epoque e);

    void toucheClavier(String touche);

    void temps();
}
