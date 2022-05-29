package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CButton extends JButton {

    private Color highlightColor = new Color(51, 51, 51);
    private Color normalColor = new Color(29, 29, 29);
    private Color pressedColor = new Color(241, 241, 241);

    public CButton(String label) {
        super(label);

        // On réinitialise tous les paramètres du bouton
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        // Customise le style du bouton
        setBackground(normalColor);
        setForeground(Color.white);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBorder(new EmptyBorder(10, 25, 10, 25));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Pour que ce soit bien smoooooooooooooooth
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        if (getModel().isArmed()) {
            g2.setColor(pressedColor);
            setForeground(Color.black);
        } else {
            if (getModel().isRollover()) g2.setColor(highlightColor); else g2.setColor(getBackground());
            setForeground(Color.white);
        }
        g2.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, 35, 35);

        super.paintComponent(g);
    }

}
