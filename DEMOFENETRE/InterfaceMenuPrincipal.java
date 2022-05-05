import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceMenuPrincipal extends InterfaceGraphique {

    JButton menuSuivant;
    JLabel texte;
    JTextField nomInput;
    int i = 0;

    public InterfaceMenuPrincipal() {
        this.windowTitle = "Menu principal";
        this.windowSize = new Dimension(400, 500);

        // ----------

        this.setLayout(new GridLayout(4, 1));
        // --
        this.texte = new JLabel("Menu principal");
        this.texte.setHorizontalAlignment(SwingConstants.CENTER);
        // --
        this.nomInput = createText("Votre nom?");
        // --
        this.menuSuivant = createButton("Menu secondaire");
        this.menuSuivant.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                // Changement de la variable "nom" dans le menu secondaire
                InterfaceMenuSecondaire s = (InterfaceMenuSecondaire)fg.getWindow("secondaire");
                s.nom = nomInput.getText();
                fg.setCurrentWindow("secondaire"); // switch
            }
        });
        // --
        JButton exit = createButton("Exit");
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                fg.close();
            }
        });

        // --
        this.add(texte);
        this.add(nomInput);
        this.add(menuSuivant);
        this.add(exit);
    }

    @Override
    public void onSwitch() {
        super.onSwitch();
        i++;
        fg.frame.setTitle("Menu principal | i:" + i);
    }
}
