package Vue;

import Vue.JComposants.CInfoJoueur;
import Vue.JComposants.JPanelCustom;

import javax.imageio.ImageIO;
import  javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

class VueJeu extends JPanel {
    CollecteurEvenements controleur;
    VueNiveau vueNiveau;
    private  JPanel bottom;
    private  JPanelCustom futur;
    private  JLayeredPane jLayeredPane1;
    private  JPanel fond;
    private  JButton menu;
    private JPanelCustom passe;
    private  JPanel plateaux;
    private  JPanelCustom present;
    private  JPanel top;
    private CInfoJoueur j1;
    private CInfoJoueur j2;
    private int passeX ;
    private int passeY ;
    Image plateauPasse ;

    VueJeu(CollecteurEvenements c) {
        controleur = c;

        jLayeredPane1 = new JLayeredPane();
        plateaux = new  JPanel();
        passe = new JPanelCustom(1);
        present = new JPanelCustom(2);
        futur = new JPanelCustom(3);
        fond = new  JPanel();
        top = new  JPanel();
        menu = new  JButton();
        bottom = new  JPanel();

        setLayout(new OverlayLayout(this));

        // -- DESSIN DES PLATEAUX
        plateaux.setBackground(new Color(255, 255, 255));
        plateaux.setBorder(BorderFactory.createEmptyBorder(140, 100, 140, 100));
        plateaux.setOpaque(false);
        plateaux.setLayout(new GridLayout(1, 3, 30, 0));

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
        plateaux.add(plateau1);
        plateaux.add(plateau2);
        plateaux.add(plateau3);

        plateaux.addComponentListener(new ComponentAdapter() {
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

        add(plateaux);

        // -- DESSIN DERRIÈRE LES PLATEAUX

        fond.setLayout(new GridLayout(2, 0));

        // DESSIN DE LA PARTIE DU HAUT (bouton menu + info joueur 1)
        top.setBackground(new Color(23, 23, 23));
        top.setLayout(new BorderLayout());

        //--
        JPanel boutons = new JPanel();
        boutons.setOpaque(false);
        boutons.setLayout(new FlowLayout(FlowLayout.LEFT));
        menu.setBackground(new Color(51, 51, 51));
        menu.setBorder(BorderFactory.createEmptyBorder(12, 12, 1, 1));
        menu.setMargin(new Insets(10, 10, 2, 14));
        menu.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/white_burger.png"))).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
        boutons.add(menu);

        //--
        JPanel joueur1infos = new JPanel();
        joueur1infos.setLayout(new FlowLayout(FlowLayout.RIGHT));
        joueur1infos.setOpaque(false);
        joueur1infos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        j1 = new CInfoJoueur(0);
        joueur1infos.add(j1);
        //----
        top.add(boutons, BorderLayout.PAGE_START);
        top.add(joueur1infos, BorderLayout.CENTER);

        fond.add(top);

        //-----
        // DESSIN DE LA PARTIE DU BAS (boutons + infos joueur 2)
        bottom.setBackground(new Color(254, 125, 97));
        bottom.setLayout(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel bas = new JPanel();
        bas.setOpaque(false);
        bas.setLayout(new BorderLayout());
        // --
        JPanel joueur2infos = new JPanel();
        joueur2infos.setLayout(new FlowLayout(FlowLayout.LEFT));
        joueur2infos.setOpaque(false);

        j2 = new CInfoJoueur(1);
        joueur2infos.add(j2);
        // -
        JPanel controles = new JPanel();
        controles.setLayout(new FlowLayout(FlowLayout.RIGHT, -6, 0));
        controles.setOpaque(false);

        controles.add(new JButton("<"));
        controles.add(new JButton(">"));
        controles.add(new JButton("Fin tour"));

        bas.add(joueur2infos, BorderLayout.PAGE_START);
        bas.add(controles, BorderLayout.PAGE_END);
        // --
        bottom.add(bas, BorderLayout.PAGE_END);
        // --
        fond.add(bottom);

        add(fond);

        // -- Add Listener
        passe.addMouseListener(new AdaptateurSouris(c, this , "plateauPasse"));
        present.addMouseListener(new AdaptateurSouris(c, this,"plateauPresent"));
        futur.addMouseListener(new AdaptateurSouris(c,this,"plateauFutur"));
    }

    void nouvellePartie() {
        vueNiveau = new VueNiveau(controleur);
        controleur.jeu().ajouteObservateur(vueNiveau);

//        j1 = new CInfoJoueur(controleur.jeu().joueur1().nom(), 4, 0);
        j1.setName(controleur.jeu().joueur1().nom());
        j1.setPions(controleur.jeu().joueur1().nombrePionsReserve());
        j2.setName(controleur.jeu().joueur2().nom());
        j2.setPions(controleur.jeu().joueur2().nombrePionsReserve());
    }


    public int getTaille(){
        return passe.getHeight();
    }
}
