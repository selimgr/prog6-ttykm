package Vue.JComposants;

import javax.swing.*;
import java.awt.*;

// 1 =

public class JPanelCustom extends JPanel {

    Image plateauPasse;
    Image plateauPresent;
    Image plateauFutur;
    Image current;

    public JPanelCustom(int numero){
        plateauPasse = new ImageIcon("/assets/Passé.png").getImage();
        plateauPresent = new ImageIcon("/assets/Présent.png").getImage();
        plateauFutur = new ImageIcon("/assets/Futur.png").getImage();
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
