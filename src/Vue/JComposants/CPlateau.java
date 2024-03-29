package Vue.JComposants;

import Modele.Epoque;
import Modele.Jeu;
import Modele.Plateau;
import Patterns.Observateur;
import Vue.CollecteurEvenements;
import Vue.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CPlateau extends JPanel implements Observateur {
    CollecteurEvenements controleur;
    int num;
    int bordureHaut, bordureGauche, hauteurCase, largeurCase;

    public CPlateau(int numero, CollecteurEvenements c) {
        controleur = c;
        num = numero;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculerDimensions();
        drawPlateau(g);

        switch (num) {
            case 1:
                drawContenu(g,Epoque.PASSE);
                drawFocus(g,Epoque.PASSE);
                drawBrillance(g,Epoque.PASSE);
                break;
            case 2:
                drawContenu(g,Epoque.PRESENT);
                drawFocus(g,Epoque.PRESENT);
                drawBrillance(g,Epoque.PRESENT);
                break;
            case 3:
                drawContenu(g,Epoque.FUTUR);
                drawFocus(g,Epoque.FUTUR);
                drawBrillance(g,Epoque.FUTUR);
                break;
        }
    }

    private void drawPlateau(Graphics g) {
        Image current;

        switch (num) {
            case 1:
                if (controleur.jeu().joueurActuel().aFocusPasse()) {
                    current = Theme.instance().plateau_passe_actif();
                } else {
                    current = Theme.instance().plateau_passe_inactif();
                }
                break;
            case 2:
                if (controleur.jeu().joueurActuel().aFocusPresent()) {
                    current = Theme.instance().plateau_present_actif();
                } else {
                    current = Theme.instance().plateau_present_inactif();
                }
                break;
            case 3:
                if (controleur.jeu().joueurActuel().aFocusFutur()) {
                    current = Theme.instance().plateau_futur_actif();
                } else {
                    current = Theme.instance().plateau_futur_inactif();
                }
                break;
            default:
                System.err.println("Mauvais numéro de boutons");
                return;
        }
        g.drawImage(current, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawContenu(Graphics g, Epoque e) {
        Jeu j = controleur.jeu();
        Plateau p = j.plateau();
        int x = bordureGauche;
        int y = bordureHaut;

        for (int l = 0; l < Plateau.TAILLE; l++) {
            for (int c = 0; c < Plateau.TAILLE; c++) {
                if (p.aGraine(l, c, e)) {
                    g.drawImage(Theme.instance().graine(), x, y, largeurCase, hauteurCase, this);
                }
                if (p.aBlanc(l, c, e)) {
                    if (j.prochaineActionSelectionPion() && j.joueurActuel().aPionsBlancs() && j.joueurActuel().focus() == e) {
                        g.drawImage(Theme.instance().blanc_actif(e), x, y, largeurCase, hauteurCase, this);
                    } else {
                        g.drawImage(Theme.instance().blanc_inactif(), x, y, largeurCase, hauteurCase, this);
                    }
                } else if (p.aNoir(l, c, e)) {
                    if (j.prochaineActionSelectionPion() && j.joueurActuel().aPionsNoirs() && j.joueurActuel().focus() == e) {
                        g.drawImage(Theme.instance().noir_actif(e), x, y, largeurCase, hauteurCase, this);
                    } else {
                        g.drawImage(Theme.instance().noir_inactif(), x, y, largeurCase, hauteurCase, this);
                    }
                } else if (p.aArbuste(l, c, e)) {
                    g.drawImage(Theme.instance().arbuste(), x, y, largeurCase, hauteurCase, this);
                } else if (p.aArbre(l, c, e)) {
                    g.drawImage(Theme.instance().arbre(), x, y, largeurCase, hauteurCase, this);
                } else if (p.aArbreCoucheVersLeHaut(l, c, e)) {
                    g.drawImage(Theme.instance().arbre_couche_haut(), x, y, largeurCase, hauteurCase, this);
                } else if (p.aArbreCoucheVersLaDroite(l, c, e)) {
                    g.drawImage(Theme.instance().arbre_couche_droite(), x, y, largeurCase, hauteurCase, this);
                } else if (p.aArbreCoucheVersLeBas(l, c, e)) {
                    g.drawImage(Theme.instance().arbre_couche_bas(), x, y, largeurCase, hauteurCase, this);
                } else if (p.aArbreCoucheVersLaGauche(l, c, e)) {
                    g.drawImage(Theme.instance().arbre_couche_gauche(), x, y, largeurCase, hauteurCase, this);
                }
                x += largeurCase;
            }
            y += hauteurCase;
            x = bordureGauche;
        }
    }

    public void drawFocus(Graphics g, Epoque e) {
        Jeu j = controleur.jeu();
        if(j.joueurPionsBlancs().focus() == e){
            g.drawImage(Theme.instance().focus_blanc(),0, 0, getWidth(), getHeight(), this);
        }
        if(j.joueurPionsNoirs().focus() == e){
            g.drawImage(Theme.instance().focus_noir(),0, 0, getWidth(), getHeight(), this);
        }
    }

    public void drawBrillance(Graphics g, Epoque e) {
        Jeu j = controleur.jeu();
        Plateau p = j.plateau();

        if (controleur.jeu().prochaineActionChangementFocus()) {
            return;
        }

        if (j.pionSelectionne() && j.pion() != null) {
            int x = bordureGauche + j.pion().colonne() * largeurCase;
            int y = bordureHaut + j.pion().ligne() * hauteurCase;

            if (j.pion().epoque() == e) {
                if (j.prochaineActionJouerCoup()) {
                    int l = j.pion().ligne();
                    int c = j.pion().colonne();

                    if (j.prochainCoupMouvement()) {
                        if (l + 1 < Plateau.TAILLE && (p.estOccupable(l + 1, c, e) || p.aPion(l + 1, c, e) ||
                                (p.aArbre(l + 1, c, e) && !p.aObstacleMortel(l + 1, c, e, 1, 0)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y + hauteurCase, largeurCase, hauteurCase, this);
                        }
                        if (l > 0 && (p.estOccupable(l - 1, c, e) || p.aPion(l - 1, c, e) ||
                                (p.aArbre(l - 1, c, e) && !p.aObstacleMortel(l - 1, c, e, -1, 0)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y - hauteurCase, largeurCase, hauteurCase, this);
                        }
                        if (c + 1 < Plateau.TAILLE && (p.estOccupable(l, c + 1, e) || p.aPion(l, c + 1, e) ||
                                (p.aArbre(l, c + 1, e) && !p.aObstacleMortel(l, c + 1, e, 0, 1)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x + largeurCase, y, largeurCase, hauteurCase, this);
                        }
                        if (c > 0 && (p.estOccupable(l, c - 1, e) || p.aPion(l, c - 1, e) ||
                                (p.aArbre(l, c - 1, e) && !p.aObstacleMortel(l, c - 1, e, 0, -1)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x - largeurCase, y, largeurCase, hauteurCase, this);
                        }
                    } else if (j.prochainCoupPlantation()) {
                        if (p.estVide(l, c, e) || (p.aPion(l, c, e) && !p.aGraine(l, c, e))) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y, largeurCase, hauteurCase, this);
                        }
                        if (l + 1 < Plateau.TAILLE && (p.estVide(l + 1, c, e) ||
                                (p.aPion(l + 1, c, e) && !p.aGraine(l + 1, c, e)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y + hauteurCase, largeurCase, hauteurCase, this);
                        }
                        if (l > 0 && (p.estVide(l - 1, c, e) || (p.aPion(l - 1, c, e) && !p.aGraine(l - 1, c, e)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y - hauteurCase, largeurCase, hauteurCase, this);
                        }
                        if (c + 1 < Plateau.TAILLE && (p.estVide(l, c + 1, e) ||
                                (p.aPion(l, c + 1, e) && !p.aGraine(l, c + 1, e)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x + largeurCase, y, largeurCase, hauteurCase, this);
                        }
                        if (c > 0 && (p.estVide(l, c - 1, e) || (p.aPion(l, c - 1, e) && !p.aGraine(l, c - 1, e)))) {
                            g.drawImage(Theme.instance().surbrillance(e), x - largeurCase, y, largeurCase, hauteurCase, this);
                        }
                    } else if (j.prochainCoupRecolte()) {
                        if (p.aGraine(l, c, e)) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y, largeurCase, hauteurCase, this);
                        }
                        if (l + 1 < Plateau.TAILLE && p.aGraine(l + 1, c, e)) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y + hauteurCase, largeurCase, hauteurCase, this);
                        }
                        if (l > 0 && p.aGraine(l - 1, c, e)) {
                            g.drawImage(Theme.instance().surbrillance(e), x, y - hauteurCase, largeurCase, hauteurCase, this);
                        }
                        if (c + 1 < Plateau.TAILLE && p.aGraine(l, c + 1, e)) {
                            g.drawImage(Theme.instance().surbrillance(e), x + largeurCase, y, largeurCase, hauteurCase, this);
                        }
                        if (c > 0 && p.aGraine(l, c - 1, e)) {
                            g.drawImage(Theme.instance().surbrillance(e), x - largeurCase, y, largeurCase, hauteurCase, this);
                        }
                    }
                }
            }

            if (j.joueurActuel().aPionsBlancs() && j.pion().epoque() == e) {
                g.drawImage(Theme.instance().blanc_selectionne(e), x, y, largeurCase, hauteurCase, this);
            } else if (j.joueurActuel().aPionsNoirs() && j.pion().epoque() == e) {
                g.drawImage(Theme.instance().noir_selectionne(e), x, y, largeurCase, hauteurCase, this);
            } else if (e.indice() == j.pion().epoque().indice() + 1 || e.indice() == j.pion().epoque().indice() - 1) {
                if (p.estOccupable(j.pion().ligne(), j.pion().colonne(), e) &&
                        j.prochaineActionJouerCoup() && j.prochainCoupMouvement()) {
                    g.drawImage(Theme.instance().surbrillance(e), x, y, largeurCase, hauteurCase, this);
                }
            }
        }
    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * getWidth() / (float) Theme.instance().largeurPlateau());
        hauteurCase = Math.round(Theme.instance().hauteurCase() * getHeight() / (float) Theme.instance().hauteurPlateau());
        largeurCase = Math.round(Theme.instance().largeurCase() * getWidth() / (float) Theme.instance().largeurPlateau());
    }

    @Override
    public void miseAJour() {
        repaint();
    }
}
