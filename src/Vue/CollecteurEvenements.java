package Vue;

import Modele.Epoque;
import Modele.Jeu;
import Modele.TypeJoueur;

public interface CollecteurEvenements {
    void fixerMediateurVues(Vues v);

    void afficherDemarrage();

    void afficherMenuPrincipal();

    void afficherMenuNouvellePartie();

    void afficherJeu();

    void afficherMenuChargerPartie();

    void nouvellePartie(String nomJ1, TypeJoueur typeJ1, int handicapJ1,
                        String nomJ2, TypeJoueur typeJ2, int handicapJ2, int choixJoueurDebut);

    void partieSuivante();

    Jeu jeu();

    void toClose();

    void afficherRegles();

    void jouer(int l, int c, Epoque e);

    void annuler();

    void refaire();

    void selectionnerPlanterGraine();

    void selectionnerRecolterGraine();

    void clicSouris(int l, int c, Epoque e);

    void toucheClavier(String touche);

    void temps();

    boolean sauvegarderPartie();

    void chargerPartie(String nomSauvegarde);
}
