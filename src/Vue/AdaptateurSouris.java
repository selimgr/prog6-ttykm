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
        double bordureY, bordureX;
        double caseY, caseX;

        switch (epoque) {
            case PASSE:
                bordureY = BORDURE_PASSE_Y * pane.getHeight();
                caseY = (pane.getHeight() - BORDURE_PASSE_Y * 2) / 4;
                bordureX = BORDURE_PASSE_X * pane.getWidth();
                caseX = (pane.getWidth() - BORDURE_PASSE_X * 2) / 4;
                break;
            case PRESENT:
                bordureY = BORDURE_PRESENT_Y * pane.getHeight();
                caseY = (pane.getHeight() - BORDURE_PRESENT_Y * 2) / 4;
                bordureX = BORDURE_PRESENT_X * pane.getWidth();
                caseX = (pane.getWidth() - BORDURE_PRESENT_X * 2) / 4;
                break;
            default:
                bordureY = BORDURE_FUTUR_Y * pane.getHeight();
                caseY = (pane.getHeight() - BORDURE_FUTUR_Y * 2) / 4;
                bordureX = BORDURE_FUTUR_X * pane.getWidth();
                caseX = (pane.getWidth() - BORDURE_FUTUR_X * 2) / 4;
        }
        l = (int) ((e.getY() - bordureY) / caseY);
        c = (int) ((e.getX() - bordureX) / caseX);

        System.out.println("bordureY " + bordureY + ", bordureX = " + bordureX);
        System.out.println("caseY " + caseY + ", caseX = " + caseX);
        System.out.println("y = " + e.getY() + ", x = " + e.getX());
        System.out.println("l = " + l + ", c = " + c);
        controleur.jouer(l, c, epoque);
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
