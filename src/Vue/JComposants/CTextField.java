package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CTextField extends JTextField {

    public CTextField() {

        // On réinitialise tous les paramètres par défaut

        // Customise le style du bouton
        setBackground(Color.white);
        setForeground(Color.black);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBorder(new EmptyBorder(10, 5, 10, 5));
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }
}
