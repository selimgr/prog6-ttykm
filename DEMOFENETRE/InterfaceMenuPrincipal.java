import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceMenuPrincipal extends InterfaceGraphique {

    JButton menuSuivant;
    JLabel texte;
    JTextField nomInput;
    int i = 0;
    public InterfaceMenuPrincipal(FenetreGraphique fg) {
        super(fg);
        this.windowTitle = "Menu principal";

        // ----------

        this.setLayout(new GridLayout(4, 1));
        // --
        this.texte = new JLabel("Menu principal - i = " + i);
        this.texte.setHorizontalAlignment(SwingConstants.CENTER);
        // --
        this.nomInput = createText("Votre nom?");
        // --
        this.menuSuivant = createButton("Menu secondaire");
        this.menuSuivant.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                // Incrémentation du compteur
                i ++;
                texte.setText("Menu principal - i = " + i);

                // Changement de la variable "nom" dans le menu secondaire
                InterfaceMenuSecondaire s = (InterfaceMenuSecondaire)fg.getMenu(1);
                s.nom = nomInput.getText();
                fg.setMenu(1); // switch
            }
        });
        // --
        JButton exit = createButton("Exit");
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                fg.fermer();
            }
        });

        // --
        this.add(texte);
        this.add(nomInput);
        this.add(menuSuivant);
        this.add(exit);
    }
}
