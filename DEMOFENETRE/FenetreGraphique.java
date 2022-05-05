import javax.swing.*;
import java.awt.*;

class FenetreGraphique implements Runnable {

    JFrame frame;
    InterfaceGraphique[] menus;
    int actualIndex = 0;

    /**
     * Créer la fenêtre et initialise celle-ci avec la première InterfaceGraphique de `menus`
     */
    public void demarrer() {
        this.frame = new JFrame("Jeu");
        this.frame.setSize(new Dimension(400, 400));
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // --
        this.menus = new InterfaceGraphique[2];
        this.menus[0] = new InterfaceMenuPrincipal(this);
        this.menus[1] = new InterfaceMenuSecondaire(this);
        // --
        actualIndex = 0;
        this.setMenu(actualIndex);
        this.frame.setVisible(true);
    }

    /**
     * Échange l'affichage courant avec un nouveau
     * @param index indice du menu dans le tableau
     */
    public void setMenu(int index) {
        // TODO: Gérer les erreurs en cas d'index invalide
        this.menus[index].repaint();
        this.menus[index].onSwitch();
        this.frame.setTitle(this.menus[index].windowTitle);
        this.frame.setContentPane(this.menus[index]);
        this.frame.revalidate();
        this.frame.repaint();
        actualIndex = index;
    }

    /**
     * Obtient l'affichage d'indice i
     * @param index indice du menu dans le tableau
     */
    public InterfaceGraphique getMenu(int index) {
        return this.menus[index];
    }

    /**
     * Ferme la fenêtre
     */
    public void fermer() {
        this.frame.dispose();
    }

    @Override
    public void run() {
        this.demarrer();
    }
}