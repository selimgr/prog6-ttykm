package Controleur;

import Modele.Case;
import Modele.Epoque;
import Modele.Jeu;
import Modele.TypeJoueur;
import Modele.Pion;
import Vue.CollecteurEvenements;
import Vue.Vues;

// TODO: Compléter le controleur

public class ControleurMediateur implements CollecteurEvenements {
    Vues vues;
    Jeu jeu;
    Case caseDepart;
    Case caseArrivee;
    boolean caseSelectionne;

    @Override
    public void fixerMediateurVues(Vues v) {
        vues = v;
    }

    private void verifierMediateurVues(String message) {
        if (vues == null) {
            throw new IllegalStateException(message + " : médiateur de vues non fixé");
        }
    }

    private void verifierJeu(String message) {
        if (jeu == null) {
            throw new IllegalStateException(message + " : aucune partie commencée");
        }
    }

    @Override
    public void afficherDemarrage() {
        verifierMediateurVues("Impossible d'afficher le démarrage");
        vues.afficherDemarrage();
    }

    @Override
    public void afficherMenuPrincipal() {
        verifierMediateurVues("Impossible d'afficher le menu principal");
        vues.afficherMenuPrincipal();
    }

    @Override
    public void afficherMenuNouvellePartie() {
        verifierMediateurVues("Impossible d'afficher le menu nouvelle partie");
        vues.afficherMenuNouvellePartie();
    }

    @Override
    public void afficherJeu() {
        verifierMediateurVues("Impossible d'afficher le jeu");
        vues.afficherJeu();
    }

    @Override
    public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, Pion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, Pion pionsJ2, int handicapJ2) {
        verifierMediateurVues("Impossible de créer une nouvelle partie");
        jeu = new Jeu();
        jeu.nouveauJoueur(nomJ1, typeJ1, pionsJ1 , handicapJ1);
        jeu.nouveauJoueur(nomJ2, typeJ2, pionsJ2 , handicapJ2);
        jeu.nouvellePartie();
        vues.nouvellePartie();
        caseSelectionne = false;
    }

    @Override
    public void partieSuivante() {
        verifierJeu("Impossible de passer à la partie suivante");
        jeu.nouvellePartie();
    }

    @Override
    public Jeu jeu() {
        verifierJeu("Impossible de renvoyer un jeu");
        return jeu;
    }

    @Override
    public void toClose() {
        vues.close();
    }

    @Override
    public void clicSouris(int l, int c, Epoque e) {
        //Appeler affichage des feedforwards
        if (caseSelectionne) {
            caseArrivee = new Case(l, c, e);
            // Gérer différent cas ( mouvements, recolte, plantation, ...)
            jeu.jouerMouvement(caseDepart.ligne(),caseDepart.colonne(),caseDepart.epoque(),
                    caseArrivee.ligne(),caseArrivee.colonne(),caseArrivee.epoque());

            caseSelectionne = false;
        }
        else {
            caseDepart = new Case(l, c, e);
            caseSelectionne = true;
        }
    }

    @Override
    public void toucheClavier(String touche) {

    }

    @Override
    public void temps() {

    }
}