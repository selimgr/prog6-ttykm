package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

class VueMenuPrincipal extends JPanel {

    int logoHeight;
    Image t;
    Image logo;
    int called = 0;

    VueMenuPrincipal(CollecteurEvenements c) {
        setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Chargement des assets
        t = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/topbanner.png"))).getImage();
        logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/logo.png"))).getImage();

        JButton nouvellePartie = new JButton("Nouvelle Partie");
        nouvellePartie.addActionListener((e) -> {
            c.afficherMenuNouvellePartie();
        });
        add(nouvellePartie);

        JButton chargerPartie = new JButton("Charger Partie");
        chargerPartie.addActionListener((e) -> {
            //System.out.println("Je capte pas pq il m'affiche pas la fenêtre");
            c.afficherMenuChargerPartie();
        });
        add(chargerPartie);

        add(new JButton("Règles"));
        add(Box.createRigidArea(new Dimension(10, 30)));
        add(new JButton("Paramètres"));

        JButton quitter = new JButton("Quitter");
        quitter.addActionListener((e) -> {
            c.toClose();
        });
        add(quitter);

        for (Component ca : getComponents()) {
            if (ca.getClass().getName().contains("Button"))
                ((JButton)ca).setAlignmentX(CENTER_ALIGNMENT);
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            }
        });

        JButton regles, didacticiel, parametres;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = new Color(255, 140, 85);
        Color color2 = new Color(255, 120, 105);
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

        int width = (int) (getWidth() * 1.7);
        int height = (t.getHeight(null) * width) / t.getWidth(null);

        g.drawImage(t, 0, 0, width, height, null);
        // --

        width = (int) (getWidth() * 0.25);
        logoHeight = (logo.getHeight(null) * width) / logo.getWidth(null);

        int x = (int) ((getWidth() - width) / 2) - 5;

        g.drawImage(logo, x, 35, width, logoHeight, null);
        if (called == 0) {
            setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            called = 1;
        }
    }
}
