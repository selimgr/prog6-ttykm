package Vue.JComposants;

import Vue.Imager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CGraines extends JPanel {

    private final ImageIcon seed;
    private final JPanel p;
    private final int hgap = 5;

    public CGraines() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        seed = new ImageIcon(Imager.getScaledImage("assets/seed_.png", 32, 32));

        // --
        p = new JPanel(new GridLayout(1, 0, hgap, 0));
        p.setBackground(new Color(255, 255, 255, 218));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(p);
    }

    public void setSeeds(int nb) {
        p.removeAll();
        p.setLayout(new GridLayout(1, nb, hgap, 0));
        for (int i = 0; i < nb; i ++) p.add(new JLabel(seed));
        repaint();
    }
}
