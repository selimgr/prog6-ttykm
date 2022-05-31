package Vue;

import Patterns.Observateur;
import Vue.JComposants.CGraines;
import Vue.JComposants.CInfoJoueur;
import Vue.JComposants.CPlateau;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class VueNiveau extends JPanel implements Observateur {
    CollecteurEvenements controleur;
    CPlateau passe, present, futur;
    MatteBorder top, bottom;
    CInfoJoueur j1, j2;
    CGraines g;
    JLabel texteJeu;
    VueJeu parent;

    VueNiveau(CollecteurEvenements c, VueJeu p, CInfoJoueur j1, CInfoJoueur j2, CGraines g, JLabel texteJeu) {
        controleur = c;
        parent = p;
        passe = new CPlateau(1, controleur);
        present = new CPlateau(2, controleur);
        futur = new CPlateau(3, controleur);
        this.j1 = j1;
        this.j2 = j2;
        this.g = g;
        this.texteJeu = texteJeu;

        c.jeu().ajouteObservateur(passe);
        c.jeu().ajouteObservateur(present);
        c.jeu().ajouteObservateur(futur);

        setBackground(new Color(255, 255, 255));
//        setBorder(BorderFactory.createEmptyBorder(140, 100, 140, 100));
//        setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        setOpaque(false);
        setLayout(new GridLayout(1, 3, 10, 0));

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
        j1.setPions(controleur.jeu().joueurPionsBlancs().nombrePionsReserve());
        j2.setPions(controleur.jeu().joueurPionsNoirs().nombrePionsReserve());
        g.setSeeds(controleur.jeu().plateau().nombreGrainesReserve());
        texteJeu.setText("Au tour de " + controleur.jeu().joueurActuel().nom() + " de jouer !");

        if (controleur.jeu().partieTerminee()) {
            // Laissez le commentaire en hommage à Tom... #ripFinPartieEmoji
            //controleur.afficherMenuFin();
            parent.showEnd();
        }
    }
}
