package Vue;

import Modele.Epoque;
import Vue.JComposants.JPanelCustom;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AdaptateurSouris extends MouseAdapter {
    CollecteurEvenements controleur;
    String Objet;
    JPanelCustom pane;
    static double BORDURE_PASSE_Y = 0.09102564;
    static double BORDURE_PRESENT_Y = 0.08678756;
    static double BORDURE_FUTUR_Y = 0.08903226;
    static double BORDURE_PASSE_X = 0.09137709;
    static double BORDURE_PRESENT_X = 0.08667529;
    static double BORDURE_FUTUR_X = 0.08914729;

    AdaptateurSouris(CollecteurEvenements c, JPanelCustom pane, String nomObjet) {
        controleur = c;
        Objet = nomObjet;
        this.pane = pane;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l;
        int c;
        Epoque epoque = resolution_nom(Objet);

        switch (epoque) {
            case PASSE:
                l = (int) ((e.getY() / (pane.getHeight() - 2 * BORDURE_PASSE_Y)) * 4);
                c = (int) ((e.getX() / (pane.getHeight() - 2 * BORDURE_PASSE_X)) * 4);
                break;
            case PRESENT:
                l = (int) ((e.getY() / (pane.getHeight() - 2 * BORDURE_PRESENT_Y)) * 4);
                c = (int) ((e.getX() / (pane.getHeight() - 2 * BORDURE_PRESENT_X)) * 4);
                break;
            default:
                l = (int) ((e.getY() / (pane.getHeight() - 2 * BORDURE_FUTUR_Y) * 4));
                c = (int) ((e.getX() / (pane.getHeight() - 2 * BORDURE_FUTUR_X)) * 4);
        }
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
