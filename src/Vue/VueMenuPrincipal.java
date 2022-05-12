package Vue;

import javax.swing.*;
import java.awt.*;

class VueMenuPrincipal extends JPanel {

    Component logoB;
    int logoHeight;

    VueMenuPrincipal(CollecteurEvenements c) {
        setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        JButton nouvellePartie = new JButton("Nouvelle Partie");
        nouvellePartie.addActionListener((e) -> {
            c.afficherMenuNouvellePartie();
        });
        nouvellePartie.setAlignmentX(CENTER_ALIGNMENT);
        add(nouvellePartie);

        JButton chargerPartie, regles, didacticiel, quitter, parametres;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image t = new ImageIcon(getClass().getResource("/assets/topbanner.png")).getImage();

        int width = (int) (getWidth() * 1.7);
        int height = (t.getHeight(null) * width) / t.getWidth(null);

        g.drawImage(t, 0, 0, width, height, null);

        Image logo = new ImageIcon(getClass().getResource("/assets/logo.png")).getImage();

        width = (int) (getWidth() * 0.2);
        logoHeight = (logo.getHeight(null) * width) / logo.getWidth(null);

        int x = (int) ((getWidth() - width) / 2) - 5;

        g.drawImage(logo, x, 20, width, logoHeight, null);
        setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
    }
}
