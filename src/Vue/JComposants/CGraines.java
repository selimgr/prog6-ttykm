package Vue.JComposants;

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

    public CGraines() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        BufferedImage r_seed;

        try {
            r_seed = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/seed.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AffineTransform xform =  AffineTransform.getScaleInstance(0.6, 0.6);
        r_seed = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR).filter(r_seed, null);
        seed = new ImageIcon(r_seed);
        // --
        p = new JPanel(new GridLayout(1, 0, 10, 0));
        p.setBackground(new Color(255, 255, 255, 218));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(p);
    }

    public void setSeeds(int nb) {
        p.removeAll();
        p.setLayout(new GridLayout(1, nb, 10, 0));
        for (int i = 0; i < nb; i ++) p.add(new JLabel(seed));
        repaint();
    }
}
