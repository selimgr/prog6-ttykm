package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CTextField extends JTextField {

    private int radius = 0;

    public CTextField() {

        // On réinitialise tous les paramètres par défaut
        setOpaque(false);
        // Customise le style du bouton
        setBackground(Color.white);
        setForeground(Color.black);
        setFont(new Font("Arial", Font.PLAIN, 15));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Pour que ce soit bien smoooooooooooooooth
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

//        setForeground(getForeground());
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, radius, radius);

        super.paintComponent(g);
    }

}
