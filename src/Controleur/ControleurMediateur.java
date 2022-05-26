package Controleur;

import Modele.*;
import Vue.CollecteurEvenements;
import Vue.Vues;

// TODO: A tester

// TODO: Compléter le controleur

public class ControleurMediateur implements CollecteurEvenements {
    Vues vues;
    Jeu jeu;
    IA ia1;
    IA ia2;

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
    public void afficherMenuChargerPartie() {
        verifierMediateurVues("Impossible d'afficher le menu des parties sauvegardées");
        vues.afficherMenuChargerPartie();
    }


    @Override
    public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, Pion pionsJ1, int handicapJ1,
                               String nomJ2, TypeJoueur typeJ2, Pion pionsJ2, int handicapJ2) {
        verifierMediateurVues("Impossible de créer une nouvelle partie");
        jeu = new Jeu();
        jeu.nouveauJoueur(nomJ1, typeJ1, pionsJ1 , handicapJ1);
        jeu.nouveauJoueur(nomJ2, typeJ2, pionsJ2 , handicapJ2);
        jeu.nouvellePartie();
        vues.nouvellePartie();
        initIA(typeJ1,typeJ2);
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
    public void afficherRegles() {
        vues.afficherR();
    }

    @Override
    public void jouer(int l, int c, Epoque e) {
        jeu.jouer(l, c, e);
    }

    @Override
    public void annuler() {
        jeu.annuler();
    }

    @Override
    public void selectionnerPlanterGraine() {
        jeu.selectionnerPlantation();
    }

    @Override
    public void selectionnerRecolterGraine() {
        jeu.selectionnerRecolte();
    }

    @Override
    public void toucheClavier(String touche) {
        switch (touche) {
            case "IA":
                jouerIA();
                break;
            default:
                System.out.println("Touche inconnue : " + touche);

        }
    }

    @Override
    public void temps() {
    }

    @Override
    public void jouerIA() {
        if (jeu.joueurActuel().type() != TypeJoueur.HUMAIN && jeu.prochaineActionSelectionPion() ) {
            if (jeu.joueurActuel() == jeu.joueur1()) ia1.jouer();
            else if (jeu.joueurActuel() == jeu.joueur2()) ia2.jouer();
        }
    }

    private void initIA(TypeJoueur typeJ1,TypeJoueur typeJ2){
        switch (typeJ1){
            case IA_DIFFICILE:
                ia1 = new IA_Difficile(jeu,jeu.joueur1(), jeu().joueur2(),this);break;
            case IA_MOYEN:
                ia1 = new IA_Moyen(jeu,jeu.joueur1(), jeu().joueur2(),this);break;
            case IA_FACILE:
                ia1 = new IA_Aleatoire(jeu,jeu.joueur1(),jeu.joueur2(),this);break;
        }
        switch (typeJ2){
            case IA_DIFFICILE:
                ia2 = new IA_Difficile(jeu,jeu.joueur2(), jeu().joueur1(),this);break;
            case IA_MOYEN:
                ia2 = new IA_Moyen(jeu,jeu.joueur2(), jeu().joueur1(),this);break;
            case IA_FACILE:
                ia2 = new IA_Aleatoire(jeu,jeu.joueur2(), jeu().joueur1(), this);break;
        }
    }
}
