import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceMenuSecondaire extends InterfaceGraphique {

    JButton menuPrecedent;
    JLabel texte;
    JLabel afficheNom;
    String nom = "";

    public InterfaceMenuSecondaire() {
        setWindowTitle("Menu secondaire");
        setWindowSize(new Dimension(500, 400));
        // ----------

        this.setLayout(new GridLayout(3, 1));
        // --
        this.afficheNom = new JLabel("Votre nom est: " + nom);
        // --
        this.menuPrecedent = createButton("Retour menu principal");
        this.menuPrecedent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                texte.setText("Vous avez déjà cliqué sur le bouton Retour menu!!");
                getFg().setCurrentWindow("principal"); // switch
            }
        });
        // --
        this.texte = new JLabel("");
        this.texte.setHorizontalAlignment(SwingConstants.CENTER);

        // --
        this.add(afficheNom);
        this.add(menuPrecedent);
        this.add(texte);
    }


    @Override
    public void onSwitch() {
        super.onSwitch();
        this.afficheNom.setText("Votre nom est: " + nom);
        JOptionPane.showMessageDialog(null, "Votre nom est: " + nom);
    }
}
