package Vue;

import Vue.JComposants.CButton;

import javax.swing.*;
import java.awt.*;

public class VueFinPartie extends JPanel {

    CollecteurEvenements controleur;
    private JPanel MenuFin;
    private JLabel bravoJoueurAGagneLabel;
    private JLabel dommageJoueurAPerduLabel;
    private JButton rejouerButton;
    private JButton menuPrincipalButton;
    int logoHeight;
    Image t;
    Image vs;
    int called = 0;

    public VueFinPartie(CollecteurEvenements c) {
        controleur = c;

        bravoJoueurAGagneLabel = new JLabel();
        dommageJoueurAPerduLabel = new JLabel();
        menuPrincipalButton = new CButton("");
        rejouerButton = new CButton("").vert();

        // Chargement des assets
        t = Imager.getImageBuffer("assets/topbanner.png");
        //vs = Imager.getImageBuffer("assets/VS.jpg");

        MenuFin = this;

        $$$setupUI$$$();

        menuPrincipalButton.addActionListener((e) -> controleur.afficherMenuPrincipal());
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
        MenuFin.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setOpaque(false);
        MenuFin.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bravoJoueurAGagneLabel = new JLabel();
        bravoJoueurAGagneLabel.setOpaque(true);
        bravoJoueurAGagneLabel.setText("Bravo, <Joueur> a gagné !");
        bravoJoueurAGagneLabel.setVisible(true);
        panel1.add(bravoJoueurAGagneLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), null, 0, false));
        dommageJoueurAPerduLabel = new JLabel();
        dommageJoueurAPerduLabel.setOpaque(true);
        dommageJoueurAPerduLabel.setText("Dommage, <Joueur> a perdu !");
        panel1.add(dommageJoueurAPerduLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setOpaque(false);
        MenuFin.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rejouerButton.setOpaque(false);
        rejouerButton.setText("Rejouer");
        panel2.add(rejouerButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        menuPrincipalButton.setText("Menu Principal");
        panel2.add(menuPrincipalButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MenuFin;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        bravoJoueurAGagneLabel.setText("Il a gagné !");
        //bravoJoueurAGagneLabel.setText(controleur.jeu().vainqueur().nom() + " a gagné !");
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
    }
}
