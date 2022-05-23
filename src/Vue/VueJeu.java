package Vue;

import Modele.TypeJoueur;
import Vue.JComposants.CInfoJoueur;
import Vue.JComposants.CPlateau;

import  javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import static java.awt.GridBagConstraints.*;

class VueJeu extends JPanel {
    CollecteurEvenements controleur;
    VueNiveau vueNiveau;
    private  JPanel bottom;
    private CPlateau futur;
    private  JLayeredPane jLayeredPane1;
    private  JPanel fond;
    private  JButton menu;
    private CPlateau passe;
    private  JPanel plateaux;
    private CPlateau present;
    private  JPanel top;
    private CInfoJoueur j1;
    private CInfoJoueur j2;

    VueJeu(CollecteurEvenements c) {
        controleur = c;
        setFocusable(true);
        this.addKeyListener(new AdaptateurClavier(controleur));
        jLayeredPane1 = new JLayeredPane();
        passe = new CPlateau(1, controleur);
        present = new CPlateau(2, controleur);
        futur = new CPlateau(3, controleur);
        fond = new  JPanel();
        top = new  JPanel();
        menu = new  JButton();
        bottom = new  JPanel();

        setLayout(new OverlayLayout(this));

        // -- DESSIN DERRIÈRE LES PLATEAUX
        fond.setLayout(new GridLayout(2, 0));

        // DESSIN DE LA PARTIE DU HAUT (bouton menu + info joueur 1)
        top.setBackground(new Color(23, 23, 23));
        top.setLayout(new BorderLayout());

        //--
        JPanel boutons = new JPanel();
        boutons.setOpaque(false);
        boutons.setLayout(new FlowLayout(FlowLayout.LEFT));
        menu.setBackground(new Color(0, 0, 0));
        menu.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        menu.setMargin(new Insets(10, 10, 2, 14));

        // Menu Paramètres
        JMenuBar jBar = new JMenuBar();
        jBar.setBackground(new Color(0, 0, 0));
        jBar.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBar.setMargin(new Insets(10, 10, 2, 14));
        JMenu jm = new JMenu();
        jm.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/white_burger.png"))).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
        JCheckBoxMenuItem itemMusique = new JCheckBoxMenuItem();
        itemMusique.setText("Musique");
        itemMusique.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Musique.png"))).getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
        JMenuItem item2 = new JMenuItem();
        item2.setText("Sauvegarder");
        JMenuItem item3 = new JMenuItem();
        item3.setText("Menu Principal");
        item3.addActionListener((e) -> {
            c.afficherMenuPrincipal();
        });
        JMenuItem item4 = new JMenuItem();
        item4.setText("Quitter");
        item4.addActionListener((e) -> {
            c.toClose();
        });
        jm.add(itemMusique); jm.add(item2);
        jm.add(item3); jm.add(item4);
        jBar.add(jm);

        JMenu tutoriel = new JMenu();
        //Affichage des règles pendant le jeu
        tutoriel.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent menuEvent) {
                c.afficherRegles();
            }
            @Override
            public void menuDeselected(MenuEvent menuEvent) {}
            @Override
            public void menuCanceled(MenuEvent menuEvent) {}
        });
        tutoriel.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Point-d'interrogation.jpg"))).getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
        jBar.add(tutoriel);

        menu.add(jBar);

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

        // TODO : Ajout du stock de graine
        // Sachant qu'il y a le focus entre les plateaux et les boutons graines
        JPanel grainesButtons = new JPanel();
        grainesButtons.setLayout(new GridBagLayout());
        grainesButtons.setBackground(new Color(254, 125, 97));
        // --
        //setEnabled à modifer à l'avenir en fonction de l'action possible par le joueur courant
        JButton recolter = new JButton("Recolter une graine");
        recolter.addActionListener((e) -> c.selectionnerRecolterGraine());
        recolter.setEnabled(false);

        JButton planter = new JButton("Planter une graine");
        planter.addActionListener((e) -> c.selectionnerPlanterGraine());
        planter.setEnabled(true);

        grainesButtons.add(planter);
        grainesButtons.add(recolter);

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

        bas.add(grainesButtons, BorderLayout.PAGE_START);
        bas.add(joueur2infos);
        bas.add(controles, BorderLayout.PAGE_END);
        // --
        bottom.add(bas, BorderLayout.PAGE_END);
        // --
        fond.add(bottom);
    }

    void nouvellePartie() {
        vueNiveau = new VueNiveau(controleur);
        controleur.jeu().ajouteObservateur(vueNiveau);

        add(vueNiveau);
        add(fond);

        j1.setName((controleur.jeu().joueur1().estHumain() ? "" : "IA : ") + controleur.jeu().joueur1().nom());
        j1.setPions(controleur.jeu().joueur1().nombrePionsReserve());

        j2.setName((controleur.jeu().joueur2().estHumain() ? "" : "IA : ") + controleur.jeu().joueur2().nom());
        j2.setPions(controleur.jeu().joueur2().nombrePionsReserve());

        vueNiveau.miseAJour();
        JOptionPane.showMessageDialog(null, "C'est " + controleur.jeu().joueurActuel().nom() + " qui commence (PIONS " + controleur.jeu().joueurActuel().pions().toString() + ")");
    }
}
