package Vue;

import Modele.Epoque;

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
