package Vue;

import Modele.Joueur;
import Vue.JComposants.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

class VueJeu extends JPanel {
    CollecteurEvenements controleur;
    VueNiveau vueNiveau;
    private final CInfoJoueur j1;
    private final CInfoJoueur j2;
    private final CGraines seeds;
    private JLabel texteJeu, endGameText;
    private final JPanel backgroundTop, backgroundBottom;
    private JFrame topFrame;

    private JPanel mainPanel, endGame;

    VueJeu(CollecteurEvenements c) {
        controleur = c;

        j1 = new CInfoJoueur(true);
        j2 = new CInfoJoueur(false);
        seeds = new CGraines();

        setLayout(new OverlayLayout(this));

        // Fin partie
        JPanel endGame = new JPanel();

        // Pour les moitiés de couleurs uniquement
        JPanel background = new JPanel(new GridLayout(2, 0));
        backgroundTop = new JPanel(new BorderLayout());
        backgroundTop.setBackground(new Color(23, 23, 23));
        backgroundBottom = new JPanel(new BorderLayout());
        backgroundBottom.setBackground(new Color(255, 116, 87));
        background.add(backgroundTop);
        background.add(backgroundBottom);
        // --

        JPanel contenu = new JPanel(new GridBagLayout());
        contenu.setOpaque(false);

        addTop(contenu);
        addMain(contenu);
        addBottom(contenu);

        addEndGame();
        add(contenu);
        add(background);
    }

    private void addEndGame() {
        endGame = new JPanel(new GridBagLayout());
        endGame.setOpaque(true);
        endGame.setVisible(false);
        endGame.setBackground(new Color(23, 23, 23, 163));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = BOTH;
        gbc.anchor = CENTER;

        JPanel banner = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = VERTICAL;
        gbc2.anchor = PAGE_START;

        banner.setBorder(new EmptyBorder(30, 0, 30, 0));
        banner.setBackground(new Color(100, 183, 68));
        endGameText = new JLabel("game over t tro nul");
        endGameText.setFont(new Font("Arial", Font.BOLD, 30));
        endGameText.setForeground(Color.white);
        banner.add(endGameText, gbc2);

        gbc2.gridy = 1;
        gbc2.anchor = CENTER;
        gbc2.insets = new Insets(20, 0, 0, 0);
        JPanel endButtons = new JPanel();
        endButtons.setOpaque(false);
        JButton menu = new CButton("Menu principal");
        JButton retry = new CButton("Rejouer?").blanc();
        endButtons.add(menu);
        endButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        endButtons.add(retry);
        menu.addActionListener((e) -> controleur.afficherMenuPrincipal());
        retry.addActionListener((e) -> {
            endGame.setVisible(false);
            controleur.partieSuivante();
        });

        banner.add(endButtons, gbc2);
        endGame.add(banner, gbc);

        add(endGame);
    }

    void showEnd() {
        // TODO : Ajout du point du vainqueur
        Joueur vainqueur = controleur.jeu().vainqueur();
        Joueur perdant;
        if (vainqueur == controleur.jeu().joueur1()) {
            perdant = controleur.jeu().joueur2();
        } else {
            perdant = controleur.jeu().joueur1();
        }

        if (vainqueur.estHumain()) {
            endGame.getComponent(0).setBackground(new Color(100, 183, 68));
            if (!perdant.estHumain()) {
                switch (perdant.type()) {
                    case IA_FACILE:
                        endGameText.setText("Tu as gagné contre l'IA facile! C'était \"facile\"..");
                        break;
                    case IA_MOYEN:
                        endGameText.setText("Tu as gagné contre l'IA moyenne! Essaye l'IA difficile!");
                        break;
                    case IA_DIFFICILE:
                        endGameText.setText("Tu as gagné contre l'IA difficile, t'es un roi!");
                        break;
                    default:
                        endGameText.setText("Tu as gagné contre.. un alien?");
                        break;
                }
            } else {
                endGameText.setText(vainqueur.nom() + " a gagné !\n" + perdant.nom() + " a perdu..");
            }
        } else {
            endGame.getComponent(0).setBackground(new Color(201, 67, 67));
            if (perdant.estHumain()) {
                endGameText.setText("Dommage! Tu as perdu contre l'IA.. une prochaine fois!");
            } else {
                endGameText.setText("L'IA " + vainqueur.nom() + " a gagné !");
            }
        }
        endGame.setVisible(true);
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
        menu.setBorderPainted(false);
        menu.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
        menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu.setUI(new CMenuUI());
        menu.setIcon(new ImageIcon(Imager.getScaledImage("assets/white_burger.png", 32, 32)));
        menuBar.add(menu);

        JMenuItem[] menu_items = {
                new JMenuItem("Sauvegarder"),
                new JMenuItem("Menu principal"),
                new JMenuItem("Quitter")
        };

        menu_items[0].addActionListener(e -> controleur.sauvegarderPartie());
        menu_items[1].addActionListener(e -> controleur.afficherMenuPrincipal());
        menu_items[2].addActionListener(e -> controleur.toClose());

        for (JMenuItem menu_item: menu_items) {
            menu_item.setFont(new Font("Arial", Font.PLAIN, 14));
            menu_item.setBorderPainted(false);
            menu_item.setUI(new CMenuItemUI(true));
            menu.add(menu_item);
        }

        JButton regles = new CButton("? Règles").blanc();
        regles.addActionListener(e -> controleur.afficherRegles());

        menuBar.add(Box.createRigidArea(new Dimension(10, 0)));
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
        texteJeu.setFont(new Font("Arial", Font.PLAIN, 18));
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

        c.anchor = GridBagConstraints.CENTER;

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        contenu.add(mainPanel, c);
        // -----------

        c.fill = GridBagConstraints.NONE;
        // MARK: ESPACEMENT POUR LE RESTE (hors plateau)
        c.insets = new Insets(10, 60, 0, 60);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = FIRST_LINE_END;
        mainPanel.add(j1, c);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = FIRST_LINE_START;
        mainPanel.add(j2, c);

        c.insets = new Insets(14, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = PAGE_START;
        mainPanel.add(seeds, c);

        // --
        c.insets = new Insets(10, 60, 0, 60);
        c.fill = NONE;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = PAGE_END;

        mainPanel.add(addUserActions(), c);
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
            new CButton(new ImageIcon(Imager.getScaledImage("assets/undo.png", 18, 18))),
            new CButton(new ImageIcon(Imager.getScaledImage("assets/redo.png", 18, 18))),
//            new CButton("Fin tour")
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
        JPanel userActions = new JPanel();
        userActions.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;

        JPanel seedsPanel = new JPanel(new GridBagLayout());

        seedsPanel.setOpaque(false);
        JPanel seedsButtons = new JPanel();
        seedsButtons.setOpaque(false);
        JButton recolter = new CButton("Récolter une graine");
        recolter.addActionListener(e -> controleur.selectionnerRecolterGraine());
        recolter.setEnabled(true);
        recolter.setFocusable(false);

        JButton planter = new CButton("Planter une graine");
        planter.addActionListener(e -> controleur.selectionnerPlanterGraine());
        planter.setEnabled(true);
        planter.setFocusable(false);

        seedsButtons.add(planter);
        seedsButtons.add(recolter);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.NONE;
        c2.gridx = 0;
        c2.gridy = 0;
        c2.weightx = 1;
        c2.weighty = 1;
        c2.anchor = GridBagConstraints.CENTER;
        seedsPanel.add(seeds, c2);
        c2.insets = new Insets(8, 0, 0, 0);
        c2.gridy = 1;
        seedsPanel.add(seedsButtons, c2);
        userActions.add(seedsPanel);
        // --

        c.gridy = 1;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        return userActions;
    }

    void nouvellePartie() {
        endGame.setVisible(false);
        vueNiveau = new VueNiveau(controleur, this, j1, j2, seeds,texteJeu);
        controleur.jeu().ajouteObservateur(vueNiveau);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        c.insets = new Insets(5,28,5,28);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        // Initialisation du niveau
        j1.setName((!controleur.jeu().joueurActuel().estHumain() ? "IA : " : "") + controleur.jeu().joueurActuel().nom());
        j1.setPions(controleur.jeu().joueurActuel().nombrePionsReserve());

        j2.setName((!controleur.jeu().joueurSuivant().estHumain() ? "IA : " : "") + controleur.jeu().joueurSuivant().nom());
        j2.setPions(controleur.jeu().joueurSuivant().nombrePionsReserve());

        seeds.setSeeds(controleur.jeu().plateau().nombreGrainesReserve());

        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        vueNiveau.miseAJour();
        texteJeu.setText(controleur.jeu().joueurActuel().nom() + " débute la partie !");

        JButton button = new CButton("OK");
        button.addActionListener(e -> JOptionPane.getRootFrame().dispose());

        if (controleur.jeu().joueurActuel().estHumain() && controleur.jeu().joueurSuivant().estHumain()) {
            JOptionPane.showOptionDialog(null,
                    (controleur.jeu().joueurActuel().estHumain() ? controleur.jeu().joueurActuel().nom() : "L'IA " + controleur.jeu().joueurActuel().nom()) + " débute la partie avec les pions " + (controleur.jeu().joueurActuel().aPionsBlancs() ? "blancs" : "noirs") + " sur le plateau " + controleur.jeu().joueurActuel().focus().name() + ".\n" + (controleur.jeu().joueurSuivant().estHumain() ? "Le joueur " + controleur.jeu().joueurSuivant().nom() : "L'IA " + controleur.jeu().joueurSuivant().nom()) + " a les pions " + (controleur.jeu().joueurSuivant().aPionsBlancs() ? "blancs" : "noirs") + " et débutera sur le plateau " + (controleur.jeu().joueurSuivant().focus().name()) + ".",
                    "Début de la partie",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(Imager.getScaledImage("assets/info.png", 24, 24)),
                    new JButton[]{button}, button);
        }
    }

}
