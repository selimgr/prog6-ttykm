package Vue.JComposants;

import Controleur.ControleurMediateur;
import Modele.Epoque;
import Modele.Jeu;
import Modele.Plateau;
import Patterns.Observateur;
import Vue.CollecteurEvenements;
import Vue.Imager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.awt.AlphaComposite;

// 1 =

public class CPlateau extends JPanel implements Observateur {
    Image current;
    CollecteurEvenements c;
    int num;
    Image pionB = Imager.getImageBuffer("/assets/pionB.png");
    Image pionN = Imager.getImageBuffer("/assets/pionN.png");
    Image brillance = Imager.getImageBuffer("/assets/Brillance.png");
    Image graine = Imager.getImageBuffer("/assets/seed_.png");
    Image arbuste = Imager.getImageBuffer("/assets/shrub.png");
    Image arbre = Imager.getImageBuffer("/assets/tree.png");

    public CPlateau(int numero, CollecteurEvenements c){
        Image plateauPasse = Imager.getImageBuffer("/assets/Passé.png");
        Image plateauPresent = Imager.getImageBuffer("/assets/Présent.png");
        Image plateauFutur = Imager.getImageBuffer("/assets/Futur.png");

        this.c = c;
        this.num = numero;

        switch(numero) {
            case 1:
                current = plateauPasse;
                break;
            case 2:
                current = plateauPresent;
                break;
            case 3:
                current = plateauFutur;
                break;
            default:
                System.err.println("Mauvais numéro de boutons");
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(current, 0, 0, getWidth(), getHeight(), null);

        switch (num){
            case 1:
                drawBrillance(g2d, Epoque.PASSE);
                drawPion(g2d, Epoque.PASSE);
                break;
            case 2:
                drawBrillance(g2d, Epoque.PRESENT);
                drawPion(g2d, Epoque.PRESENT);
                break;
            case 3:
                drawBrillance(g2d, Epoque.FUTUR);
                drawPion(g2d, Epoque.FUTUR);
                break;
        }
    }

    private void drawPion (Graphics g, Epoque e){
        int offX = getOffsetX();
        int offY = getOffsetY();
        int multX = (this.getWidth() - 2*offX)/4;
        int multY = (this.getHeight() - 2*offY)/4;

        for (int l = 0; l < Plateau.TAILLE; l++) {
            for (int c = 0; c < Plateau.TAILLE; c++) {
                if (this.c.jeu().plateau().aBlanc(l, c, e)) {
                    g.drawImage(pionB, c*multX+offX+multX/4, l*multY+offY+multY/4, multX/2, multY/2,this );
                }
                if (this.c.jeu().plateau().aNoir(l, c, e)) {
                    g.drawImage(pionN, c*multX+offX+multX/4, l*multY+offY+multY/4, multX/2, multY/2,this );
                }

                if (this.c.jeu().plateau().aGraine(l, c, e)) {
                    g.drawImage(graine, (c*multX+offX+multX/4)-5, (l*multY+offY+multY/4)-6, (int) (multX*0.7), (int) (multY*0.7),this );
                }

                if (this.c.jeu().plateau().aArbuste(l, c, e)) {
                    g.drawImage(arbuste, (c*multX+offX+multX/4)-5, (l*multY+offY+multY/4)-8, (int) (multX*0.7), (int) (multY*0.7),this );
                }

                if (this.c.jeu().plateau().aArbre(l, c, e)) {
                    g.drawImage(arbre, (c*multX+offX+multX/4)-7, (l*multY+offY+multY/4)-8, (int) (multX*0.7), (int) (multY*0.7),this );
                }
            }
        }
    }

    public void drawBrillance(Graphics g, Epoque e){
        int offX = getOffsetX();
        int offY = getOffsetY();
        int multX = (this.getWidth() - 2*offX)/4;
        int multY = (this.getHeight() - 2*offY)/4;
        for(int i=0; i<3 ; i++){
            for(int j=0; j<Plateau.TAILLE ; j++ ){
                for(int k=0; k<Plateau.TAILLE ; k++){
                    if(this.c.jeu().plateau().aBrillance(j,k,e)){
                        g.drawImage(brillance,k*multX+offX, j*multY+offY, multX, multY, this);
                    }
                }
            }
        }

    }

    private int getOffsetX(){
        return (int) 70*this.getWidth()/775;
    }
    private int getOffsetY(){
        return 70*this.getHeight()/770;
    }

    @Override
    public void miseAJour() {
        repaint();
    }
}
