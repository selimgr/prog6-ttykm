package Vue.JComposants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CInfoJoueur extends JPanel {

    private String nomJoueur;
    private int pionsJoueur;
    private int reverse;

    public CInfoJoueur(String nom, int pions, int reverse) {
        this.nomJoueur = nom;
        this.pionsJoueur = pions;
        this.reverse = reverse;

        setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel n = new JLabel(nom);
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

        ImageIcon pawnW = new ImageIcon(r_pawnW);
        // --

        JPanel p = new JPanel(new GridLayout(1, pions, 0, 0));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        for (int i = 0; i < pions; i ++) {
            JLabel pawn = new JLabel(pawnW);
//            pawn.setPreferredSize(new Dimension(24, 38));
            p.add(pawn);
        }

        add(reverse == 0 ? n : p);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(reverse == 0 ? p : n);
    }

}
