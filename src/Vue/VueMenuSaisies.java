package Vue;

import Modele.TypeJoueur;
import Vue.JComposants.CButton;
import Vue.JComposants.CComboxBox;
import Vue.JComposants.CTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VueMenuSaisies extends JPanel {

    CollecteurEvenements controleur;

    private JPanel MenuSaisies;
    private JTextField nomJ1;
    private JComboBox typeJ1;
    private JButton jouerButton;
    private JButton menuPrincipalButton;
    private JPanel Boutons;
    private JPanel Joueur2;
    private JPanel Joueur1;
    private JTextField nomJ2;
    private JComboBox typeJ2;
    private JComboBox niveauJ2;
    private JComboBox niveauJ1;
    private JLabel JoueurCommence;
    private JComboBox comboBox1;
    int logoHeight;
    Image t;
    Image vs;
    int called = 0;

    public VueMenuSaisies(CollecteurEvenements c) {
        controleur = c;

        typeJ1 = new CComboxBox();
        typeJ2 = new CComboxBox();
        niveauJ1 = new CComboxBox();
        niveauJ2 = new CComboxBox();
        menuPrincipalButton = new CButton();
        jouerButton = new CButton().vert();
        comboBox1 = new CComboxBox();
        nomJ1 = new CTextField();
        nomJ2 = new CTextField();

        setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Chargement des assets
        t = Imager.getImageBuffer("assets/topbanner.png");
        vs = Imager.getImageBuffer("assets/VS.png");

        MenuSaisies = this;
        $$$setupUI$$$();

        for (Component co : MenuSaisies.getComponents()) {
            if (co instanceof JPanel) {
                for (Component coo : ((JPanel) co).getComponents()) {
                    if (coo instanceof CComboxBox) ((CComboxBox) coo).setEditable(true);
                    else if (coo instanceof JLabel) {
                        coo.setFont(new Font("Arial", Font.PLAIN, 14));
                        coo.setForeground(Color.WHITE);
                    }
                }
            }

        }

        menuPrincipalButton.addActionListener((e) -> controleur.afficherMenuPrincipal());

        jouerButton.addActionListener((e) -> {
            c.nouvellePartie(
                    nomJ1.getText(),
                    TypeJoueur.values()[typeJ1.getSelectedIndex()],
                    niveauJ1.getSelectedIndex(),
                    // --
                    nomJ2.getText(),
                    TypeJoueur.values()[typeJ2.getSelectedIndex()],
                    niveauJ2.getSelectedIndex(),
                    // --
                    comboBox1.getSelectedIndex() - 1
            );
            c.afficherJeu();
        });

        nomJ1.addFocusListener(new FocusAdapter() {
            final String s = nomJ1.getText();

            @Override
            public void focusGained(FocusEvent e) {
                if (nomJ1.getText().equals(s)) {
                    nomJ1.setText("");
                    nomJ1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomJ1.getText().isEmpty()) {
                    nomJ1.setForeground(Color.BLACK);
                    nomJ1.setText(s);
                }
            }
        });
        nomJ2.addFocusListener(new FocusAdapter() {
            final String s = nomJ2.getText();

            @Override
            public void focusGained(FocusEvent e) {
                if (nomJ2.getText().equals(s)) {
                    nomJ2.setText("");
                    nomJ2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomJ2.getText().isEmpty()) {
                    nomJ2.setForeground(Color.BLACK);
                    nomJ2.setText(s);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        MenuSaisies.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        MenuSaisies.setMaximumSize(new Dimension(441, 110));
        MenuSaisies.setMinimumSize(new Dimension(441, 110));
        MenuSaisies.setPreferredSize(new Dimension(441, 110));
        Joueur1 = new JPanel();
        Joueur1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        Joueur1.setBackground(new Color(-14276823));
        Joueur1.setOpaque(false);
        MenuSaisies.add(Joueur1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(600, -1), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText(" Joueur 1");
        Joueur1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nomJ1.setDropMode(DropMode.USE_SELECTION);
        nomJ1.setFocusCycleRoot(false);
        nomJ1.setFocusTraversalPolicyProvider(false);
        nomJ1.setFocusable(true);
        nomJ1.setText("Ate");
        nomJ1.setVisible(true);
        Joueur1.add(nomJ1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(3, -1), new Dimension(120, -1), new Dimension(200, -1), 0, false));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Humain");
        defaultComboBoxModel1.addElement("IA Facile");
        defaultComboBoxModel1.addElement("IA Moyenne");
        defaultComboBoxModel1.addElement("IA Difficile");
        typeJ1.setModel(defaultComboBoxModel1);
        Joueur1.add(typeJ1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(50, -1), new Dimension(100, -1), null, 0, false));
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Débutant (4 pions)");
        defaultComboBoxModel2.addElement("Initié (3 pions)");
        defaultComboBoxModel2.addElement("Confirmé (2 pions)");
        defaultComboBoxModel2.addElement("Expert (1 pion)");
        niveauJ1.setModel(defaultComboBoxModel2);
        Joueur1.add(niveauJ1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Joueur2 = new JPanel();
        Joueur2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        Joueur2.setBackground(new Color(-14276823));
        Joueur2.setOpaque(false);
        MenuSaisies.add(Joueur2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(600, -1), 0, false));
        final JLabel label2 = new JLabel();
        label2.setText(" Joueur 2");
        Joueur2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nomJ2.setText("Tom");
        Joueur2.add(nomJ2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(3, -1), new Dimension(100, -1), new Dimension(200, -1), 0, false));
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Humain");
        defaultComboBoxModel3.addElement("IA Facile");
        defaultComboBoxModel3.addElement("IA Moyenne");
        defaultComboBoxModel3.addElement("IA Difficile");
        typeJ2.setModel(defaultComboBoxModel3);
        Joueur2.add(typeJ2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(50, -1), new Dimension(100, -1), null, 0, false));
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Débutant (4 pions)");
        defaultComboBoxModel4.addElement("Initié (3 pions)");
        defaultComboBoxModel4.addElement("Confirmé (2 pions)");
        defaultComboBoxModel4.addElement("Expert (1 pion)");
        niveauJ2.setModel(defaultComboBoxModel4);
        Joueur2.add(niveauJ2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Boutons = new JPanel();
        Boutons.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        Boutons.setBackground(new Color(-14276823));
        Boutons.setOpaque(false);
        MenuSaisies.add(Boutons, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(600, 50), 0, false));
        jouerButton.setText("Jouer");
        Boutons.add(jouerButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuPrincipalButton.setText("Menu Principal");
        Boutons.add(menuPrincipalButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-14276823));
        panel1.setOpaque(false);
        MenuSaisies.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        JoueurCommence = new JLabel();
        JoueurCommence.setText("Qui commence ?");
        panel1.add(JoueurCommence, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("Aléatoire");
        defaultComboBoxModel5.addElement("Joueur 1");
        defaultComboBoxModel5.addElement("Joueur 2");
        comboBox1.setModel(defaultComboBoxModel5);
        panel1.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label1.setLabelFor(nomJ1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MenuSaisies;
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
        logoHeight = (vs.getHeight(null) * width) / vs.getWidth(null);

        int x = (int) ((getWidth() - width) / 2) - 5;

        g.drawImage(vs, x, 35, width, logoHeight, null);
        if (called == 0) {
            setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            called = 1;
        }
    }
}
