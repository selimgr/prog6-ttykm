import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

class FenetreGraphique implements Runnable {

    JFrame frame;
    Dictionary<String, InterfaceGraphique> interfacesGraphique;
    String currentWindow;

    public FenetreGraphique(String defaultWindow) {
        this.interfacesGraphique = new Hashtable<>();
        this.currentWindow = defaultWindow;
    }
    
    /**
     * Créer la fenêtre et initialise celle-ci
     */
    public void create() throws Exception {
        this.frame = new JFrame("Jeu");
        this.frame.setSize(new Dimension(400, 400));
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (currentWindow.isEmpty()) throw new Exception("NoWindowSpecified");
        this.setCurrentWindow(this.currentWindow);
        this.frame.setVisible(true);
    }

    /**
     * Échange l'affichage courant avec un nouveau
     * @param name nom de la fenêtre à afficher
     */
    public void setCurrentWindow(String name) {
        InterfaceGraphique s = this.interfacesGraphique.get(name);
        if (s.fg == null) s.fg = this;

        this.frame.setTitle(s.windowTitle);
        this.frame.setSize(s.windowSize);

        s.repaint();
        s.onSwitch();
        this.frame.setContentPane(s);
        this.frame.revalidate();
        this.frame.repaint();
        this.currentWindow = name;
    }

    /**
     * Ajoute une fenêtre
     * @param name nom
     * @param g interface graphique
     */
    public void addWindow(String name, InterfaceGraphique g) {
        this.interfacesGraphique.put(name, g);
    }

    /**
     * Retourne la fenêtre souhaitée
     * @param name nom de la fenêtre
     * @return InterfaceGraphique
     */
    public InterfaceGraphique getWindow(String name) {
        return this.interfacesGraphique.get(name);
    }

    /**
     * Retourne la fenêtre actuelle
     * @return InterfaceGraphique
     */
    public InterfaceGraphique getWindow() {
        return this.interfacesGraphique.get(currentWindow);
    }

    /**
     * Ferme la fenêtre
     */
    public void close() {
        this.frame.setVisible(true);
        this.frame.dispose();
    }

    @Override
    public void run() {
        try {
            this.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}