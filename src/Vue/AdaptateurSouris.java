package Vue;

import Modele.Epoque;
import Vue.JComposants.CPlateau;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AdaptateurSouris extends MouseAdapter {
    CollecteurEvenements controleur;
    String Objet;
    CPlateau pane;

    AdaptateurSouris(CollecteurEvenements c, CPlateau pane, String nomObjet) {
        controleur = c;
        Objet = nomObjet;
        this.pane = pane;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int bordureHaut = Math.round(Theme.instance().bordureHaut() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        int bordureGauche = Math.round(Theme.instance().bordureGauche() * pane.getWidth() / (float) Theme.instance().largeurPlateau());
        int bordureBas = Math.round(Theme.instance().bordureBas() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        int bordureDroite = Math.round(Theme.instance().bordureDroite() * pane.getWidth() / (float) Theme.instance().largeurPlateau());

        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > pane.getWidth() - bordureDroite || e.getY() > pane.getHeight() - bordureBas) {
            return;
        }
        int x = e.getX() - bordureGauche;
        int y = e.getY() - bordureHaut;
        int hauteur = pane.getHeight() - bordureHaut - bordureBas;
        int largeur = pane.getWidth() - bordureGauche - bordureDroite;

        int l = y * 4 / hauteur;
        int c = x * 4 / largeur;
        Epoque epoque = resolution_nom(Objet);

        controleur.clicSouris(l, c, epoque);
        System.out.println("l = " + l + ", c = " + c);
    }

    private Epoque resolution_nom(String nom){
        switch (nom){
            case "plateauPasse" :
                return Epoque.PASSE;
            case "plateauPresent" :
                return Epoque.PRESENT;
            case "plateauFutur" :
                return Epoque.FUTUR;
            default :
                throw new IllegalArgumentException("Aucun paramètre de ce nom");
        }
    }
}
