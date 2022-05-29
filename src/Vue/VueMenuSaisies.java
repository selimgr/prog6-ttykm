package Vue;

import Modele.Pion;
import Modele.TypeJoueur;

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

    public VueMenuSaisies(CollecteurEvenements c) {
        controleur = c;

        typeJ1 = new JComboBox();
        typeJ2 = new JComboBox();
        niveauJ1 = new JComboBox();
        niveauJ2 = new JComboBox();

        comboBox1 = new JComboBox();

        MenuSaisies = this;
        $$$setupUI$$$();

        menuPrincipalButton.addActionListener((e) -> controleur.afficherMenuPrincipal());

        jouerButton.addActionListener((e) -> {
            c.nouvellePartie(
                    nomJ1.getText(),
                    TypeJoueur.values()[typeJ1.getSelectedIndex()],
                    comboBox1.getSelectedIndex() == 0 ? Pion.BLANC : (comboBox1.getSelectedIndex() > 1 ? Pion.BLANC : Pion.NOIR),
                    niveauJ1.getSelectedIndex(),
                    // --
                    nomJ2.getText(),
                    TypeJoueur.values()[typeJ2.getSelectedIndex()],
                    comboBox1.getSelectedIndex() == 1 ? Pion.BLANC : Pion.NOIR,
                    niveauJ2.getSelectedIndex(),
                    // --
                    comboBox1.getSelectedIndex()
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
        MenuSaisies.add(Joueur1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(600, -1), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText(" Joueur 1");
        Joueur1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nomJ1 = new JTextField();
        nomJ1.setDropMode(DropMode.USE_SELECTION);
        nomJ1.setFocusCycleRoot(false);
        nomJ1.setFocusTraversalPolicyProvider(false);
        nomJ1.setFocusable(true);
        nomJ1.setText("Nom de Joueur 1");
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
        defaultComboBoxModel2.addElement("Débutant");
        defaultComboBoxModel2.addElement("Initié");
        defaultComboBoxModel2.addElement("Confirmé");
        defaultComboBoxModel2.addElement("Expert");
        niveauJ1.setModel(defaultComboBoxModel2);
        Joueur1.add(niveauJ1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Joueur2 = new JPanel();
        Joueur2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        MenuSaisies.add(Joueur2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(600, -1), 0, false));
        final JLabel label2 = new JLabel();
        label2.setText(" Joueur 2");
        Joueur2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nomJ2 = new JTextField();
        nomJ2.setText("Nom du Joueur 2");
        Joueur2.add(nomJ2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(3, -1), new Dimension(100, -1), new Dimension(200, -1), 0, false));
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Humain");
        defaultComboBoxModel3.addElement("IA Facile");
        defaultComboBoxModel3.addElement("IA Moyenne");
        defaultComboBoxModel3.addElement("IA Difficile");
        typeJ2.setModel(defaultComboBoxModel3);
        Joueur2.add(typeJ2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(50, -1), new Dimension(100, -1), null, 0, false));
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Débutant");
        defaultComboBoxModel4.addElement("Initié");
        defaultComboBoxModel4.addElement("Confirmé");
        defaultComboBoxModel4.addElement("Expert");
        niveauJ2.setModel(defaultComboBoxModel4);
        Joueur2.add(niveauJ2, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Boutons = new JPanel();
        Boutons.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        MenuSaisies.add(Boutons, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(600, 50), 0, false));
        jouerButton = new JButton();
        jouerButton.setText("Jouer");
        Boutons.add(jouerButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuPrincipalButton = new JButton();
        menuPrincipalButton.setText("Menu Principal");
        Boutons.add(menuPrincipalButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        MenuSaisies.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        JoueurCommence = new JLabel();
        JoueurCommence.setText("Qui commence ?");
        panel1.add(JoueurCommence, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("Joueur 1");
        defaultComboBoxModel5.addElement("Joueur 2");
        defaultComboBoxModel5.addElement("Aléatoire");
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

}
