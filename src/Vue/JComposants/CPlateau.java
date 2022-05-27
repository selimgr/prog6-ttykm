package Vue.JComposants;

import Modele.Epoque;
import Modele.Plateau;
import Patterns.Observateur;
import Vue.CollecteurEvenements;
import Vue.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

// 1 =

public class CPlateau extends JPanel implements Observateur {
    Image current;
    CollecteurEvenements c;
    int num;
    Image pionB = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/pionB.png"))).getImage();
    Image pionN = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/pionN.png"))).getImage();
    Image brillance = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Brillance.png"))).getImage();

    public CPlateau(int numero, CollecteurEvenements c){
        this.c = c;

        num = numero;

        switch(numero) {
            case 1:
                current = Theme.instance().plateau_passe_inactif();
                break;
            case 2:
                current = Theme.instance().plateau_present_inactif();
                break;
            case 3:
                current = Theme.instance().plateau_futur_inactif();
                break;
            default:
                System.err.println("Mauvais numéro de boutons");
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(current, 0, 0, getWidth(), getHeight(), null);
        switch (num){
            case 1:
                drawBrillance(g,Epoque.PASSE);
                drawPion(g,Epoque.PASSE);
                break;
            case 2:
                drawBrillance(g,Epoque.PRESENT);
                drawPion(g,Epoque.PRESENT);
                break;
            case 3:
                drawBrillance(g,Epoque.FUTUR);
                drawPion(g,Epoque.FUTUR);
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
