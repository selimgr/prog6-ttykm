package Vue.JComposants;

import javax.swing.*;
import java.awt.*;

// 1 =

public class JPanelCustom extends JPanel {
    Image current;

    public JPanelCustom(int numero){
        Image plateauPasse = new ImageIcon("/assets/Passé.png").getImage();
        Image plateauPresent = new ImageIcon("/assets/Présent.png").getImage();
        Image plateauFutur = new ImageIcon("/assets/Futur.png").getImage();
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(current, 0, 0, null);
    }
}
