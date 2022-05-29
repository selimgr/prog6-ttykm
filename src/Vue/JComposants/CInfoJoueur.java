package Vue.JComposants;

import Vue.Imager;

import javax.swing.*;
import java.awt.*;

public class CInfoJoueur extends JPanel {

    private final JLabel n;
    private final JPanel p;
    private final ImageIcon pawnW;
    private final int hgap = 5;

    public CInfoJoueur(boolean reverse) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        n = new JLabel("");
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 16));

        // --
        pawnW = new ImageIcon(Imager.getScaledImage(reverse ? "assets/pionB.png" : "assets/pionN.png", 25, 30));
        // --
        p = new JPanel(new GridLayout(1, 0, hgap, 0));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(!reverse ? n : p);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(!reverse ? p : n);
    }

    public void setName(String nom) {
        n.setText(nom);
    }

    public void setPions(int nb) {
        p.removeAll();
        p.setLayout(new GridLayout(1, nb, hgap, 0));
        for (int i = 0; i < nb; i ++) p.add(new JLabel(pawnW));
    }

}
