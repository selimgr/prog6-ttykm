package Vue;

import Modele.Pion;
import Patterns.Observateur;
import Vue.JComposants.CPlateau;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// TODO: Résoudre le bug d'affichage du focus quand on crée une nouvelle partie après une première partie terminée

class VueNiveau extends JPanel implements Observateur {
    CollecteurEvenements controleur;
    CPlateau passe, present, futur;
    MatteBorder top, bottom;

    VueNiveau(CollecteurEvenements c) {
        controleur = c;
        passe = new CPlateau(1, controleur);
        present = new CPlateau(2, controleur);
        futur = new CPlateau(3, controleur);
        c.jeu().ajouteObservateur(passe);
        c.jeu().ajouteObservateur(present);
        c.jeu().ajouteObservateur(futur);

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

        top = BorderFactory.createMatteBorder(10, 0, 0, 0, Color.WHITE);
        bottom = BorderFactory.createMatteBorder(0, 0, 10, 0, Color.BLACK);
    }

    @Override
    public void miseAJour() {
        CompoundBorder passe_focus = null;
        CompoundBorder present_focus = null;
        CompoundBorder futur_focus = null;

        boolean[][] focus = {
            {false, false},
            {false, false},
            {false, false}
        };

        focus[controleur.jeu().joueur1().focus().indice()][controleur.jeu().joueur1().pions().valeur() - 1] = true;
        focus[controleur.jeu().joueur2().focus().indice()][controleur.jeu().joueur2().pions().valeur() - 1] = true;

        for (int p = 0; p < 3; p++) {
            boolean[] pp = focus[p];

            CompoundBorder b = new CompoundBorder(
                (pp[0] ? top : null),
                (pp[1] ? bottom : null)
            );

            switch (p) {
                case 0:
                    passe_focus = b;
                    break;
                case 1:
                    present_focus = b;
                    break;
                case 2:
                    futur_focus = b;
                    break;
            }
        }

        passe.setBorder(passe_focus);
        present.setBorder(present_focus);
        futur.setBorder(futur_focus);
    }
}
