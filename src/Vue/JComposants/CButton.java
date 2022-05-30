package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CButton extends JButton {

    private Color highlightBackgroundColor = new Color(51, 51, 51);
    private Color normalBackgroundColor = new Color(29, 29, 29);
    private Color pressedBackgroundColor = new Color(21, 21, 21);
//    private Color pressedBackgroundColor = new Color(241, 241, 241);
    private Color textColor = new Color(255, 255, 255);
    private Color pressedTextColor = new Color(255, 255, 255);
//    private Color pressedTextColor = new Color(0, 0, 0);
    private int radius = 35;

    public CButton(String label) {
        super(label);

        // On réinitialise tous les paramètres du bouton
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        // Customise le style du bouton
        setBackground(normalBackgroundColor);
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
            g2.setColor(pressedBackgroundColor);
            setForeground(pressedTextColor);
        } else {
            if (getModel().isRollover()) g2.setColor(highlightBackgroundColor); else g2.setColor(normalBackgroundColor);
            setForeground(textColor);
        }
        g2.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, radius, radius);

        super.paintComponent(g);
    }

    public CButton blanc() {
        textColor = Color.black;
        pressedTextColor = Color.black;

        normalBackgroundColor = Color.white;
        highlightBackgroundColor = new Color(241, 241, 241, 255);
        pressedBackgroundColor = new Color(208, 208, 208, 255);

        return this;
    }

    public CButton vert() {
        textColor = Color.white;
        pressedTextColor = Color.white;

        normalBackgroundColor = new Color(42, 187, 94, 255);
        highlightBackgroundColor = new Color(35, 162, 81, 255);
        pressedBackgroundColor = new Color(32, 138, 70, 255);

        return this;
    }

    public CButton big() {
        radius = 45;

        setFont(new Font("Arial", Font.BOLD, 24));
        setBorder(new EmptyBorder(10, 35, 10, 35));

        return this;
    }

}
