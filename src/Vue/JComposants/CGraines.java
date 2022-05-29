package Vue.JComposants;

import Vue.Imager;

import javax.swing.*;
import java.awt.*;

public class CGraines extends JPanel {

    private final ImageIcon seed;
    private final int hgap = 5;

    public CGraines() {
        setOpaque(false);
        setBackground(null);
        setLayout(new GridLayout(1, 0, hgap, 0));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        seed = new ImageIcon(Imager.getScaledImage("assets/seed_.png", 32, 32));
    }

    public void setSeeds(int nb) {
        removeAll();
        setLayout(new GridLayout(1, nb, hgap, 0));
        for (int i = 0; i < nb; i ++) add(new JLabel(seed));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Pour que ce soit bien smoooooooooooooooth
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.setColor(new Color(101, 154, 99, 255));
        g2.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, 45, 45);

        super.paintComponent(g);
    }
}
