package Vue;

import Controleur.Action;
import Modele.Epoque;
import Modele.Jeu;
import Modele.TypeJoueur;
import Modele.Pion;

public interface CollecteurEvenements {
    void fixerMediateurVues(Vues v);

    void afficherDemarrage();

    void afficherMenuPrincipal();

    void afficherMenuNouvellePartie();

    void afficherJeu();

    void afficherMenuChargerPartie();

    void nouvellePartie(String nomJ1, TypeJoueur typeJ1, Pion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, Pion pionsJ2, int handicapJ2);

    void partieSuivante();

    Jeu jeu();

    void toClose();

    void afficherRegles();

    void selectionnerPion(int l, int c, Epoque e);

    void deplacer(int l, int c, Epoque e);

    void planterGraine(int l, int c);

    void recolterGraine(int l, int c);

    public void fixerAction(Action a);

    void clicSouris(int l, int c, Epoque e);

    void toucheClavier(String touche);

    void temps();
}
