package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CTexteJeu extends JPanel {

    private int radius = 35;
    private JLabel text;

    public CTexteJeu(String texte) {
        super();

        text = new JLabel(texte);
        text.setFont(new Font("Arial", Font.BOLD, 14));

        setBorder(new EmptyBorder(7, 25, 7, 25));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));

        add(text);
    }

    public CTexteJeu() {
        this("");
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        if (text != null) text.setForeground(fg);
    }

    public void setText(String texte) {
        text.setText(texte);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Pour que ce soit bien smoooooooooooooooth
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, radius > 0 ? getWidth()-1 : getWidth(), radius > 0 ? getHeight()-1 : getHeight(), radius, radius);

    }
}

