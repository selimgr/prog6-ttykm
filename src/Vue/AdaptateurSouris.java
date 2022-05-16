package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AdaptateurSouris extends MouseAdapter {
    CollecteurEvenements controleur;
    String Objet;
    VueJeu vueJeu;

    AdaptateurSouris(CollecteurEvenements c,VueJeu vJeu, String nomObjet) {
        controleur = c;
        Objet = nomObjet;
        vueJeu = vJeu;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / vueJeu.getHeight();
        int c = e.getX() / vueJeu.getWidth();
        controleur.clicSouris(l, c, resolution_nom(Objet));
    }

    private int resolution_nom(String nom){
        switch (nom){
            case "plateauPasse" :
                return 0;
            case "plateauPresent" :
                return 1;
            case "plateauFutur" :
                return 2;
            default :
                throw new IllegalArgumentException("Aucun paramètre de ce nom");
        }
    }
}
