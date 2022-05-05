import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        FenetreGraphique fg = new FenetreGraphique("principal");
        fg.addWindow("principal", new InterfaceMenuPrincipal());
        fg.addWindow("secondaire", new InterfaceMenuSecondaire());

        SwingUtilities.invokeLater(fg);
    }

}