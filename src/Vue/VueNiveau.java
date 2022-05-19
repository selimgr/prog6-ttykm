package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import Vue.JComposants.CPlateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class VueNiveau extends JPanel implements Observateur {
    CollecteurEvenements controleur;

    CPlateau passe;
    CPlateau present;
    CPlateau futur;

    VueNiveau(CollecteurEvenements c) {
        controleur = c;
        passe = new CPlateau(1, controleur);
        present = new CPlateau(2, controleur);
        futur = new CPlateau(3, controleur);

        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(140, 100, 140, 100));
        setOpaque(false);
        setLayout(new GridLayout(1, 3, 30, 0));

        JPanel plateau1 = new JPanel(new GridBagLayout());
        plateau1.setOpaque(false);
        passe.setMinimumSize(new Dimension(300, 300));
        plateau1.add(passe);

        // ----
        JPanel plateau2 = new JPanel(new GridBagLayout());
        plateau2.setOpaque(false);
        present.setPreferredSize(new Dimension(300, 300));
        plateau2.add(present);

        // ----
        JPanel plateau3 = new JPanel(new GridBagLayout());
        plateau3.setOpaque(false);
        futur.setPreferredSize(new Dimension(300, 300));
        plateau3.add(futur);

        // --
        add(plateau1);
        add(plateau2);
        add(plateau3);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            int size =  Math.min(plateau1.getWidth(), plateau1.getHeight());
            passe.setPreferredSize(new Dimension(size, size));
            present.setPreferredSize(new Dimension(size, size));
            futur.setPreferredSize(new Dimension(size, size));
            plateau1.revalidate();
            plateau2.revalidate();
            plateau3.revalidate();
            }
        });

        // -- Add Listener
        passe.addMouseListener(new AdaptateurSouris(c, passe , "plateauPasse"));
        present.addMouseListener(new AdaptateurSouris(c, present,"plateauPresent"));
        futur.addMouseListener(new AdaptateurSouris(c,futur,"plateauFutur"));
    }

    @Override
    public void repaint() {
        super.repaint();

        System.out.println("cc");
    }

    @Override
    public void miseAJour() {
        repaint();
    }
}
