package Vue.JComposants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CInfoJoueur extends JPanel {

    private JLabel n;
    private JPanel p;
    private ImageIcon pawnW;

    public CInfoJoueur(int reverse) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        n = new JLabel("");
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 16));

        // --
        BufferedImage r_pawnW = null;

        try {
            r_pawnW = ImageIO.read(getClass().getResourceAsStream("/assets/pawn.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AffineTransform xform =  AffineTransform.getScaleInstance(0.5, 0.5);
        r_pawnW = new AffineTransformOp(xform, AffineTransformOp.TYPE_BICUBIC).filter(r_pawnW, null);
        // r_pawnW = r_pawnW.getScaledInstance(24, 38, Image.SCALE_SMOOTH);
        pawnW = new ImageIcon(r_pawnW);
        // --
        p = new JPanel(new GridLayout(1, 0, 0, 0));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(reverse == 0 ? n : p);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(reverse == 0 ? p : n);
    }

    public void setName(String nom) {
        n.setText(nom);
        repaint();
    }

    public void setPions(int nb) {
        p.removeAll();
        p.setLayout(new GridLayout(1, nb, 0, 0));
        for (int i = 0; i < nb; i ++) {
            JLabel pawn = new JLabel(pawnW);
            p.add(pawn);
        }
        repaint();
    }

}
