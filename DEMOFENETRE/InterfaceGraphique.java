import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

abstract class InterfaceGraphique extends JPanel {

    private String windowTitle;
    private Dimension windowSize;
    private FenetreGraphique fg;

    public InterfaceGraphique() {
        this.windowSize = new Dimension(500, 500);
    }

    /**
     * Évènement onSwitch(): appelé lors d'un changement de menu
     */
    public void onSwitch() { }

    /**
     * Créer un bouton
     * @param s Titre
     * @return JButton
     */
    public JButton createButton(String s) {
        JButton button = new JButton(s);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(120, 50));
        button.setFocusable(false);
        return button;
    }

    /**
     * Créer une zone de texte
     * @param s Placeholder
     * @return JTextField
     */
    public JTextField createText(String s) {
        JTextField T1 = new JTextField(s, 50);
        T1.getFont().deriveFont(Font.ITALIC);
        T1.setForeground(Color.gray);
        T1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (T1.getText().equals(s)) {
                    T1.setText("");
                    T1.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (T1.getText().isEmpty()) {
                    T1.setForeground(Color.GRAY);
                    T1.setText(s);
                }
            }
        });
        return T1;
    }

    // Setters
    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        if(getFg() != null) getFrame().setTitle(windowTitle);
    }

    public void setWindowSize(Dimension windowSize) {
        this.windowSize = windowSize;
        if (getFg() != null) getFrame().setSize(windowSize);
    }

    public void setFg(FenetreGraphique fg) {
        this.fg = fg;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

    public FenetreGraphique getFg() {
        return fg;
    }

    public JFrame getFrame() {
        return fg.frame;
    }

}
