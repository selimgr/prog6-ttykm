package Controleur;

import Modele.*;
import Vue.CollecteurEvenements;
import Vue.Vues;

// TODO: Compléter le controleur

public class ControleurMediateur implements CollecteurEvenements {
    Vues vues;
    Jeu jeu;
    int departL, departC;
    Epoque eDepart;
    Action action;
    IA ia;
    boolean attendAction1 = true;  //sorte d'AEFD
    boolean attendAction2 = false;
    boolean attendFocus =  false;   //


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
    public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, Pion pionsJ1, int handicapJ1, String nomJ2, TypeJoueur typeJ2, Pion pionsJ2, int handicapJ2) {
        verifierMediateurVues("Impossible de créer une nouvelle partie");
        jeu = new Jeu();
        jeu.nouveauJoueur(nomJ1, typeJ1, pionsJ1 , handicapJ1);
        jeu.nouveauJoueur(nomJ2, typeJ2, pionsJ2 , handicapJ2);
        jeu.nouvellePartie();
        vues.nouvellePartie();
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
    public void afficherRegles() {vues.afficherR();}

    @Override
    public void selectionnerPion(int l, int c, Epoque e) {
        departL = l;
        departC = c;
        eDepart = e;
        fixerAction(Action.MOUVEMENT);
    }

    @Override
    public void deplacer(int l, int c, Epoque e) {
        if (eDepart != null) {
            //System.out.println("je joue le mouvement " + departL +", "+departC+", "+eDepart+" vers "+l+", "+c+", "+e);
            jeu.jouerMouvement(departL, departC, eDepart, l, c, e);
        }
        eDepart = null;
    }

    @Override
    public void planterGraine(int l, int c) {
        if (eDepart != null) {
            jeu.jouerPlantation(departL, departC, eDepart, l, c, eDepart);
        }
        eDepart = null;
    }

    @Override
    public void recolterGraine(int l, int c) {
        if (eDepart != null) {
            jeu.jouerRecolte(departL, departC, eDepart, l, c, eDepart);
        }
        eDepart = null;
    }

    @Override
    public void fixerAction(Action a) {
        action = a;
    }

    @Override
    public void clicSouris(int l, int c, Epoque e) {
        if (attendAction1 || attendAction2) {
            if (eDepart == null && !jeu().plateau().aPion(l, c, e)) {
                return;
            }
            if (eDepart == null) {
                selectionnerPion(l, c, e);
                return;
            }

            switch (action) {
                case MOUVEMENT:
                    deplacer(l, c, e);
                    System.out.println("mouvement");
                    break;
                case PLANTATION:
                    planterGraine(l, c);
                    break;
                case RECOLTE:
                    recolterGraine(l, c);
                    break;
                default:
                    throw new IllegalStateException("Aucune action sélectionnée");
            }
        }else{ //attend focus
            jeu().changerFocus(e);
        }

            eDepart = null;
            action = null;
            vues.metAjour();

        if(attendAction1) { //AEFD
            attendAction1 = false;
            attendAction2 = true;
        } else {
            if (attendAction2) {
                attendAction2 = false;
                attendFocus = true;
            } else {
                if(attendFocus){
                    attendFocus = false;
                    attendAction1 = true;
                    //changer joueur
                }
            }
        }

    }

    @Override
    public void toucheClavier(String touche) {

    }

    @Override
    public void temps() {

    }
}