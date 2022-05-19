package Vue.JComposants;

import Controleur.ControleurMediateur;
import Modele.Epoque;
import Modele.Jeu;
import Modele.Plateau;
import Patterns.Observateur;
import Vue.CollecteurEvenements;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

// 1 =

public class JPanelCustom extends JPanel implements Observateur {
    Image current;
    CollecteurEvenements c;
    int num;

    public JPanelCustom(int numero, CollecteurEvenements c){
        Image plateauPasse = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Passé.png"))).getImage();
        Image plateauPresent = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Présent.png"))).getImage();
        Image plateauFutur = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Futur.png"))).getImage();
        this.c = c;
        num = numero;
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
        c.jeu().ajouteObservateur(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(current, 0, 0, getWidth(), getHeight(), null);
        switch (num){
            case 1:
                drawPion(g,Epoque.PASSE);
                break;
            case 2:
                drawPion(g,Epoque.PRESENT);
                break;
            case 3:
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
                    System.out.println("A blanc offsetX = "+ offX+  "offset y = " + offY);
                    g.setColor(Color.green);
                    g.fillOval(l*multY+offY+multY/4, c*multX+offX+multX/4, multX/2, multY/2);
                }
                if (this.c.jeu().plateau().aNoir(l, c, e)) {
                    System.out.println("A noir offsetX = "+ offX+  "offset y = " + offY);
                    g.setColor(Color.black);
                    g.fillOval(l*multY+offY+multY/4, c*multX+offX+multX/4, multX/2, multY/2);
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
