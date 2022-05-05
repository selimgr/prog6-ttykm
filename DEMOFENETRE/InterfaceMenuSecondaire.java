import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceMenuSecondaire extends InterfaceGraphique {

    JButton menuPrecedent;
    JLabel texte;
    JLabel afficheNom;
    String nom = "";

    public InterfaceMenuSecondaire(FenetreGraphique fg) {
        super(fg);
        this.windowTitle = "Menu secondaire";

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
                fg.setMenu(0); // switch
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
    }
}
