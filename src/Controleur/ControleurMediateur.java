package Controleur;

import Global.Configuration;
import Global.Sauvegarde;
import Modele.*;
import Vue.CollecteurEvenements;
import Vue.Vues;

public class ControleurMediateur implements CollecteurEvenements {
    Vues vues;
    Jeu jeu;
    IA ia1;
    IA ia2;
    Animation animIA1, animIA2;
    Animation animDemarrage;

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
    public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, int handicapJ1,
                               String nomJ2, TypeJoueur typeJ2, int handicapJ2, int choixJoueurDebut) {
        verifierMediateurVues("Impossible de créer une nouvelle partie");
        jeu = new Jeu();
        jeu.nouveauJoueur(nomJ1, typeJ1, handicapJ1);
        jeu.nouveauJoueur(nomJ2, typeJ2, handicapJ2);
        if (choixJoueurDebut > 0) jeu.choixJoueurDebut(choixJoueurDebut);
        jeu.nouvellePartie();
        vues.nouvellePartie();
        initIA(typeJ1,typeJ2);
    }

    @Override
    public void partieSuivante() {
        verifierJeu("Impossible de passer à la partie suivante");
        jeu.nouvellePartie();
        vues.nouvellePartie();
        afficherJeu();
    }

    @Override
    public Jeu jeu() {
        verifierJeu("Impossible de renvoyer un jeu");
        return jeu;
    }

    @Override
    public void toClose() {
        vues.close();
        System.exit(0);
    }

    @Override
    public void afficherRegles() {
        vues.afficherR();
    }

    @Override
    public void selectionnerPlanterGraine() {
        if (!jeu().prochaineActionJouerCoup()) {
            return;
        }

        if (jeu().prochainCoupPlantation()) {
            jeu().selectionnerMouvement();
        } else {
            jeu().selectionnerPlantation();
        }
    }

    @Override
    public void selectionnerRecolterGraine() {
        if (!jeu().prochaineActionJouerCoup()) {
            return;
        }

        if (jeu.prochainCoupRecolte()) {
            jeu().selectionnerMouvement();
        } else {
            jeu().selectionnerRecolte();
        }
    }

    public void jouer(int l, int c, Epoque e) {
        //System.out.print("Jouer   :" + jeu.joueurActuel().nom() + " (l,c,e)=("+l+","+c+","+e+") ");
        jeu().jouer(l, c, e);
    }

    @Override
    public void annuler() {
        //System.out.print("Annuler : " + jeu().joueurActuel().nom() + " ");
        jeu().annuler();
    }

    @Override
    public void refaire() {
        jeu().refaire();
    }

    @Override
    public void clicSouris(int l, int c, Epoque e) {
        if (jeu().partieTerminee()) {
            return;
        }
        if (jeu().joueurActuel().estHumain()) {
            jouer(l, c, e);
        }
    }

    @Override
    public void toucheClavier(String touche) {
        if (jeu().partieTerminee()) {
            return;
        }
        switch (touche) {
            case "Annuler":
                annuler();
                break;
            case "Refaire":
                refaire();
                break;
            default:
                Configuration.instance().logger().info("Touche inconnue : " + touche);
        }
    }

    @Override
    public void temps() {
        if (animDemarrage == null) {
            int lenteurAnimation = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationDemarrage"));
            animDemarrage = new AnimationDemarrage(lenteurAnimation, this);
        }
        if (!animDemarrage.terminee()) {
            animDemarrage.temps();
            return;
        }
        if (jeu == null || jeu().partieTerminee() || jeu().joueurActuel().estHumain()) {
            return;
        }
        if (jeu().joueurActuel() == jeu().joueur1()) {
            animIA1.temps();
        } else {
            animIA2.temps();
        }
    }

    private void initIA(TypeJoueur typeJ1, TypeJoueur typeJ2){
        int lenteurAnimationIA = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationIA"));

        switch (typeJ1) {
            case IA_DIFFICILE:
                ia1 = new IA_Difficile(jeu(), jeu().joueur1(), jeu().joueur2(), this);
                break;
            case IA_MOYEN:
                ia1 = new IA_Moyen(jeu(), jeu().joueur1(), jeu().joueur2(), this);
                break;
            case IA_FACILE:
                ia1 = new IA_Facile(jeu(), jeu().joueur1(), jeu().joueur2(), this);
                break;
        }
        if (typeJ1 != TypeJoueur.HUMAIN) {
            animIA1 = new AnimationIA(lenteurAnimationIA, ia1);
        }

        switch (typeJ2) {
            case IA_DIFFICILE:
                ia2 = new IA_Difficile(jeu(),jeu().joueur2(), jeu().joueur1(),this);
                break;
            case IA_MOYEN:
                ia2 = new IA_Moyen(jeu(),jeu().joueur2(), jeu().joueur1(),this);
                break;
            case IA_FACILE:
                ia2 = new IA_Facile(jeu(),jeu().joueur2(), jeu().joueur1(), this);
                break;
        }
        if (typeJ2 != TypeJoueur.HUMAIN) {
            animIA2 = new AnimationIA(lenteurAnimationIA, ia2);
        }
    }

    @Override
    public boolean sauvegarderPartie() {
        return jeu().sauvegarder();
    }

    @Override
    public void chargerPartie(String nomSauvegarde) {
        jeu = Sauvegarde.charger(nomSauvegarde);
        initIA(jeu().joueur1().type(), jeu().joueur2().type());
        vues.nouvellePartie();
        afficherJeu();
    }
}
