package Vue;

import Vue.JComposants.CButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        t = Imager.getImageBuffer("assets/topbanner.png");
        logo = Imager.getImageBuffer("assets/logo.png");

        JButton nouvellePartie = new CButton("Nouvelle Partie");
        nouvellePartie.addActionListener((e) -> {
            c.afficherMenuNouvellePartie();
        });

        JButton chargerPartie = new CButton("Charger Partie");
        chargerPartie.addActionListener((e) -> {
            c.afficherMenuChargerPartie();
        });

        JButton regles = new CButton("Règles");
        regles.addActionListener((e) -> {
            c.afficherRegles();
        });
        JButton didacticiel = new CButton("Didacticiel");

        JButton quitter = new CButton("Quitter");
        quitter.addActionListener((e) -> {
            c.toClose();
        });

        Component[] components = {
                nouvellePartie,
                chargerPartie,
                regles,
                Box.createRigidArea(new Dimension(10, 30)),
                didacticiel,
                quitter
        };

        for (Component component : components) {
            if (component.getClass().getName().contains("Button"))
                ((JButton)component).setAlignmentX(CENTER_ALIGNMENT);
            add(component);
            if (!component.getClass().getName().contains("Box"))
                add(Box.createRigidArea(new Dimension(0, 5)));
        }
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            }
        });

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
