package Modele;

import Global.Configuration;
import Global.Sauvegarde;
import Patterns.Observable;
import Vue.Imager;
import Vue.JComposants.CButton;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;

import static java.util.Objects.requireNonNull;

public class Jeu extends Observable implements Serializable {
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private int joueurActuel;
    private Tour tourActuel;
    private Historique historique;
    private TypeCoup prochainCoup;
    private final Random rand;
    private int choixJoueurDebut = -1;
    private Sauvegarde sauvegarde;

    public Jeu() {
        rand = new Random();
        joueurActuel = -1;
    }

    public void nouveauJoueur(String nom, TypeJoueur type, int handicap) {
        if (joueur1 == null) {
            joueur1 = new Joueur(nom, type, handicap);
        }
        else if (joueur2 == null) {
            joueur2 = new Joueur(nom, type, handicap);
            sauvegarde = new Sauvegarde(this);
        }
        else {
            throw new IllegalStateException("Impossible d'ajouter un nouveau joueur : tous les joueurs ont déjà été ajoutés");
        }
    }

    public void nouvellePartie() {
        if (joueur1 == null || joueur2 == null) {
            throw new IllegalStateException("Impossible de lancer une nouvelle partie : joueurs manquants");
        }
        // Si une partie est en cours mais n'est pas terminée, on choisit le joueur qui commence la nouvelle aléatoirement
        if (plateau != null && !partieTerminee()) {
            joueurActuel = -1;
        }

        // On choisit le joueur commençant la partie aléatoirement lorsqu'il s'agit de la première
        // Sinon le perdant de la partie précédente commence
        if (joueurActuel == -1) {
            joueurActuel = choixJoueurDebut == -1 ? rand.nextInt(2) : choixJoueurDebut;
        } else if (vainqueur() == joueur1) {
            joueurActuel = 1;
        } else {
            joueurActuel = 0;
        }

        // Par défaut le joueur qui commence a les pions blancs
        if (joueur1 == joueurActuel()) {
            joueur1.initialiserJoueur(Pion.BLANC);
            joueur2.initialiserJoueur(Pion.NOIR);
        } else {
            joueur2.initialiserJoueur(Pion.BLANC);
            joueur1.initialiserJoueur(Pion.NOIR);
        }

        plateau = new Plateau();
        historique = new Historique();
        tourActuel = historique.nouveauTour(joueurActuel().focus());
        metAJour();
    }

    public Plateau plateau() {
        requireNonNull(plateau, "Impossible de récupérer le plateau : aucune partie créée");
        return plateau;
    }

    public Joueur joueur1() {
        requireNonNull(joueur1, "Impossible de récupérer le joueur 1 : le joueur n'a pas été créé");
        return joueur1;
    }

    public Joueur joueur2() {
        requireNonNull(joueur2, "Impossible de récupérer le joueur 2 : le joueur n'a pas été créé");
        return joueur2;
    }

    public Joueur joueurPionsBlancs() {
        return joueur1.aPionsBlancs() ? joueur1 : joueur2;
    }

    public Joueur joueurPionsNoirs() {
        return joueur1.aPionsNoirs() ? joueur1 : joueur2;
    }

    public Joueur joueurActuel() {
        if (joueurActuel == 0) {
            return joueur1();
        }
        return joueur2();
    }

    public Joueur joueurSuivant() {
        if (joueurActuel == 0) {
            return joueur2();
        }
        return joueur1();
    }

    public void selectionnerMouvement() {
        prochainCoup = TypeCoup.MOUVEMENT;
        metAJour();
    }

    public void selectionnerPlantation() {
        prochainCoup = TypeCoup.PLANTATION;
        metAJour();
    }

    public void selectionnerRecolte() {
        prochainCoup = TypeCoup.RECOLTE;
        metAJour();
    }

    public void jouer(int l, int c, Epoque e) {
        if (partieTerminee()) {
            return;
        }

        if (prochaineActionSelectionPion()) {
            Configuration.instance().logger().info("jouer Selection   ");
            selectionnerPion(l, c, e);
        }
        else if (prochaineActionJouerCoup()) {
            Coup coup;

            switch (prochainCoup) {
                case MOUVEMENT:
                    if (tourActuel.deselectionnerPion(l, c, e)) {
                        metAJour();
                        return;
                    }
                    coup = new Mouvement(plateau, joueurActuel(), pion().ligne(), pion().colonne(), pion().epoque());
                    break;
                case PLANTATION:
                    coup = new Plantation(plateau, joueurActuel(), pion().ligne(), pion().colonne(), pion().epoque());
                    break;
                default:
                    coup = new Recolte(plateau, joueurActuel(), pion().ligne(), pion().colonne(), pion().epoque());
                    break;
            }
            jouerCoup(coup, l, c, e);
        }
        else {
            Configuration.instance().logger().info("        Jouer Focus  ");
            changerFocus(e);
        }
    }

    private void selectionnerPion(int l, int c, Epoque e) {
        if (((joueurActuel().aPionsBlancs() && plateau.aBlanc(l, c, e)) || (joueurActuel().aPionsNoirs() &&
                plateau.aNoir(l, c, e))) && tourActuel.selectionnerPion(l, c, e)) {
            historique.reinitialiserToursSuivants();
            selectionnerMouvement();
        }
    }

    private void jouerCoup(Coup coup, int l, int c, Epoque e) {
        if (tourActuel.jouerCoup(coup, l, c, e)) {
            historique.reinitialiserToursSuivants();
            selectionnerMouvement();
        }
    }

    private void changerFocus(Epoque e) {
        if (!tourActuel.changerFocus(e)) {
            return;
        }
        joueurActuel().fixerFocus(e);

        if (partieTerminee()) {
            ajouterVictoire();
        } else {
            joueurActuel = (joueurActuel + 1) % 2;
            tourActuel = historique.nouveauTour(joueurActuel().focus());
        }
        historique.reinitialiserToursSuivants();
        metAJour();
    }

    private void ajouterVictoire() {
        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2) {
            if (joueur1.pions() == Pion.BLANC) {
                joueur2.ajouterVictoire();
            } else {
                joueur1.ajouterVictoire();
            }
        } else {
            if (joueur1.pions() == Pion.BLANC) {
                joueur1.ajouterVictoire();
            } else {
                joueur2.ajouterVictoire();
            }
        }
    }

    private void annulerAjoutVictoire() {
        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2) {
            if (joueur1.pions() == Pion.BLANC) {
                joueur2.enleverVictoire();
            } else {
                joueur1.enleverVictoire();
            }
        } else {
            if (joueur1.pions() == Pion.BLANC) {
                joueur1.enleverVictoire();
            } else {
                joueur2.enleverVictoire();
            }
        }
    }

    public void annuler() {
        if (!historique.peutAnnuler()) {
            return;
        }
        boolean partieTerminee = partieTerminee();

        if (!tourActuel.pionSelectionne()) {
            Configuration.instance().logger().info("        Annuler Focus  ");
            tourActuel = historique.tourPrecedent();
            joueurActuel = (joueurActuel + 1) % 2;
            joueurActuel().fixerFocus(tourActuel.focus());
        }
        if (tourActuel.annuler()) {
            if (partieTerminee) {
                joueurActuel().fixerFocus(tourActuel.focus());
                annulerAjoutVictoire();
            }
            selectionnerMouvement();
        }
    }

    public void refaire() {
        if (!historique.peutRefaire()) {
            return;
        }

        if (tourActuel.refaire()) {
            if (partieTerminee()) {
                joueurActuel().fixerFocus(tourActuel.prochainFocus());
                ajouterVictoire();
            } else if (tourActuel.termine()) {
                joueurActuel().fixerFocus(tourActuel.prochainFocus());
                joueurActuel = (joueurActuel + 1) % 2;
                tourActuel = historique.tourSuivant();
            }
            selectionnerMouvement();
        }
    }

    public boolean partieTerminee() {
        boolean ret=false;
        if (tourActuel.termine() && (plateau.nombrePlateauVide(Pion.BLANC) >= 2 || plateau.nombrePlateauVide(Pion.NOIR) >= 2)){
            ret=true;
        }
        return ret;
    }

    public Joueur vainqueur() {
        if (!partieTerminee()) {
            return null;
        }
        if (plateau.nombrePlateauVide(Pion.BLANC) >= 2) {
            return joueur1.pions() == Pion.NOIR ? joueur1 : joueur2;
        } else {
            return joueur1.pions() == Pion.BLANC ? joueur1 : joueur2;
        }
    }

    public boolean prochaineActionSelectionPion() {
        return !tourActuel.pionSelectionne() && !prochaineActionChangementFocus();
    }

    public boolean prochaineActionJouerCoup() {
        return tourActuel.pionSelectionne() && !prochaineActionChangementFocus();
    }

    public boolean prochaineActionChangementFocus() {
        int nombrePionPlateau = plateau.nombrePionPlateau(joueurActuel().pions(), joueurActuel().focus());

        return nombreCoupsRestantsTour() == 0 || (!pionSelectionne() && nombrePionPlateau == 0) ||
                (pionSelectionne() && pion() == null);
    }

    public boolean prochainCoupMouvement() {
        return prochainCoup == TypeCoup.MOUVEMENT;
    }

    public boolean prochainCoupPlantation() {
        return prochainCoup == TypeCoup.PLANTATION;
    }

    public boolean prochainCoupRecolte() {
        return prochainCoup == TypeCoup.RECOLTE;
    }

    public boolean pionSelectionne() {
        return tourActuel.pionSelectionne();
    }

    public int nombreCoupsRestantsTour(){
        return tourActuel.nombreCoupsRestants();
    }

    public void choixJoueurDebut(int numeroJoueur) {
        choixJoueurDebut = numeroJoueur % 2;
    }

    public Case pion() {
        return tourActuel.pion();
    }

    public void sauvegarder() {
        JButton button = new CButton("OK");
        button.addActionListener(e -> JOptionPane.getRootFrame().dispose());
        try {
            sauvegarde.enregistrer();

            JOptionPane.showOptionDialog(null,
                    "Sauvegarde réussie",
                    "Sauvegarde",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(Imager.getScaledImage("assets/ok.png", 24, 24)),
                    new JButton[]{button}, button);
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde: " + e);

            JOptionPane.showOptionDialog(null,
                    "Échec de la sauvegarde",
                    "Sauvegarde",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(Imager.getScaledImage("assets/err.png", 24, 24)),
                    new JButton[]{button}, button);
        }
    }
}
