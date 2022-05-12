package Controleur;

import Modele.Jeu;
import Modele.TypeJoueur;
import Modele.TypePion;
import Vue.CollecteurEvenements;
import Vue.Vues;

public class ControleurMediateur implements CollecteurEvenements {
    Vues vues;
    Jeu jeu;

    @Override
    public void fixerMediateurVues(Vues v) {
        vues = v;
    }

    @Override
    public void afficherDemarrage() {
        if (vues == null) {
            throw new IllegalStateException("Impossible d'afficher le démarrage : médiateur de vues non fixé");
        }
        vues.afficherDemarrage();
    }

    @Override
    public void afficherMenuPrincipal() {
        if (vues == null) {
            throw new IllegalStateException("Impossible d'afficher le menu principal : médiateur de vues non fixé");
        }
        vues.afficherMenuPrincipal();
    }

    @Override
    public void afficherMenuNouvellePartie() {
        if (vues == null) {
            throw new IllegalStateException("Impossible d'afficher le menu nouvelle partie : médiateur de vues non fixé");
        }
        vues.afficherMenuNouvellePartie();
    }

    @Override
    public void afficherJeu() {
        if (vues == null) {
            throw new IllegalStateException("Impossible d'afficher le jeu : médiateur de vues non fixé");
        }
        vues.afficherJeu();
    }

    @Override
    public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, TypePion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, TypePion pionsJ2, int handicapJ2) {
        jeu = new Jeu();
        jeu.nouveauJoueur(nomJ1, typeJ1, pionsJ1 , handicapJ1);
        jeu.nouveauJoueur(nomJ2, typeJ2, pionsJ2 , handicapJ2);
        jeu.nouvellePartie();
    }

    @Override
    public void partieSuivante() {
        if (jeu == null) {
            throw new IllegalStateException("Impossible de passer à la partie suivante : aucune partie commencée");
        }
        jeu.nouvellePartie();
    }

    @Override
    public Jeu jeu() {
        if (jeu == null) {
            throw new IllegalStateException("Impossible de renvoyer un jeu : aucun jeu existant");
        }
        return jeu;
    }

    @Override
    public void clicSouris(int l, int c) {

    }

    @Override
    public void toucheClavier(String touche) {

    }

    @Override
    public void temps() {

    }
}
