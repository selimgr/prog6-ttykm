package Vue;

import Vue.JComposants.CGraines;
import Vue.JComposants.CInfoJoueur;

import  javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static java.awt.GridBagConstraints.*;

class VueJeu extends JPanel {
    CollecteurEvenements controleur;
    VueNiveau vueNiveau;
    private final CInfoJoueur j1;
    private final CInfoJoueur j2;
    private final CGraines seeds;
    private JLabel texteJeu;
    private final JPanel backgroundTop, backgroundBottom;
    private JFrame topFrame;

    private JPanel mainPanel;

    VueJeu(CollecteurEvenements c) {
        controleur = c;

        j1 = new CInfoJoueur(0);
        j2 = new CInfoJoueur(1);
        seeds = new CGraines();

        setLayout(new OverlayLayout(this));

        // Pour les moitiés de couleurs uniquement
        JPanel background = new JPanel(new GridLayout(2, 0));
        backgroundTop = new JPanel(new BorderLayout());
        backgroundTop.setBackground(new Color(23, 23, 23));
        backgroundBottom = new JPanel(new BorderLayout());
        backgroundBottom.setBackground(new Color(254, 125, 97));
        background.add(backgroundTop);
        background.add(backgroundBottom);
        // --

        JPanel contenu = new JPanel(new GridBagLayout());
        contenu.setOpaque(false);

        addTop(contenu);
        addMain(contenu);
        addBottom(contenu);

        add(contenu);
        add(background);
    }

    private void addTop(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5, 5, 5, 5);

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new GridBagLayout());
        contenu.add(topPanel, c);
        // -----------

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(false);
        menuBar.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        menuBar.setMargin(new Insets(10, 10, 2, 14));
        buttonsPanel.add(menuBar);

        JMenu menu = new JMenu();
        menu.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/white_burger.png"))).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
        menuBar.add(menu);

        JMenuItem[] menu_items = {
                new JCheckBoxMenuItem("Musique"),
                new JMenuItem("Sauvegarder"),
                new JMenuItem("Menu principal"),
                new JMenuItem("Quitter")
        };

        menu_items[2].addActionListener(e -> controleur.afficherMenuPrincipal());
        menu_items[3].addActionListener(e -> controleur.toClose());

        for (JMenuItem menu_item: menu_items)
            menu.add(menu_item);

        JMenu regles = new JMenu();
        regles.addActionListener(e -> controleur.afficherRegles());
        regles.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/Point-d'interrogation.jpg"))).getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
        menuBar.add(regles);

        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        topPanel.add(buttonsPanel, c);

        // --
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        texteJeu = new JLabel("");
        texteJeu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        texteJeu.setForeground(Color.white);
        texteJeu.setBorder(new EmptyBorder(10,0,0,0));
        textPanel.add(texteJeu);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(textPanel, c);
    }

    private void addMain(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        c.insets = new Insets(0,60,0,60);
        c.anchor = GridBagConstraints.CENTER;

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        contenu.add(mainPanel, c);
        // -----------

        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = FIRST_LINE_END;
        mainPanel.add(j1, c);

        // --
        c.fill = BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = LAST_LINE_START;

        mainPanel.add(addUserActions(), c);
//
//        // --
//        c.fill = NONE;
//        c.gridx = 0;
//        c.gridy = 3;
//        c.weightx = 1;
//        c.weighty = 1;
//        c.anchor = LAST_LINE_START;
//        mainPanel.add(j2, c);
    }

    private void addBottom(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.PAGE_END;

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new GridBagLayout());
        contenu.add(bottomPanel, c);
        // -----------

        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);

        JButton[] controls = {
            new JButton("<"),
            new JButton(">"),
            new JButton("Fin tour")
        };

        controls[0].addActionListener(e -> controleur.annuler());
        controls[1].addActionListener(e -> controleur.refaire());

        for (JButton button: controls) {
            button.setFocusable(false);
            controlsPanel.add(button);
        }

        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = new Insets(10,10,10,10);
        bottomPanel.add(controlsPanel, c);
    }

    private JPanel addUserActions() {
        JPanel userActions = new JPanel(new GridBagLayout());
        userActions.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;

        seeds.setSeeds(4);

        JPanel seedsButtons = new JPanel();
        seedsButtons.setOpaque(false);
        JButton recolter = new JButton("Récolter une graine");
//        recolter.addActionListener((e) -> c.selectionnerRecolterGraine());
        recolter.setEnabled(false);

        JButton planter = new JButton("Planter une graine");
//        planter.addActionListener((e) -> c.selectionnerPlanterGraine());
        planter.setEnabled(true);

        seedsButtons.add(planter);
        seedsButtons.add(recolter);
        seeds.add(seedsButtons);

        userActions.add(seeds, c);
        // --

        c.gridy = 1;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        userActions.add(j2, c);
        // --
        return userActions;
    }

    void nouvellePartie() {
        vueNiveau = new VueNiveau(controleur);
        controleur.jeu().ajouteObservateur(vueNiveau);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        j1.setName((!controleur.jeu().joueur1().estHumain() ? "IA : " : "") + controleur.jeu().joueur1().nom());
        j1.setPions(controleur.jeu().joueur1().nombrePionsReserve());

        j2.setName((!controleur.jeu().joueur2().estHumain() ? "IA : " : "") + controleur.jeu().joueur2().nom());
        j2.setPions(controleur.jeu().joueur2().nombrePionsReserve());


        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        vueNiveau.miseAJour();
        texteJeu.setText("C'est " + controleur.jeu().joueurActuel().nom() + " qui commence (PIONS " + controleur.jeu().joueurActuel().pions().toString() + ")");
//        JOptionPane.showMessageDialog(null, "C'est " + controleur.jeu().joueurActuel().nom() + " qui commence (PIONS " + controleur.jeu().joueurActuel().pions().toString() + ")");
    }

}
