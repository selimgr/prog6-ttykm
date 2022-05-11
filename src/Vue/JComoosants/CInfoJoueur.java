package Vue.JComoosants;

import javax.swing.*;
import java.awt.*;

public class CInfoJoueur extends JPanel {

    public static int JOUEUR_NOIR = 0;
    public static int JOUEUR_BLANC = 1;

    private String nomJoueur;
    private int pionsJoueur;
    private int couleur;

    public CInfoJoueur(String nom, int pions, int couleur) {
        this.nomJoueur = nom;
        this.pionsJoueur = pions;
        this.couleur = couleur;

        setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel n = new JLabel(nom);
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 16));

        // --
        Image r_pawnW = new ImageIcon(getClass().getResource("/assets/pawn.png")).getImage();
        r_pawnW = r_pawnW.getScaledInstance(24, 38, Image.SCALE_SMOOTH);

        ImageIcon pawnW = new ImageIcon(r_pawnW);
        // --

        JPanel p = new JPanel(new GridLayout(1, pions, 0, 0));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        for (int i = 0; i < pions; i ++) {
            JLabel pawn = new JLabel(pawnW);
            pawn.setPreferredSize(new Dimension(24, 38));
            p.add(pawn);
        }

        add(couleur == JOUEUR_NOIR ? n : p);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(couleur == JOUEUR_NOIR ? p : n);
    }

}
