package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CComboxBox extends JComboBox {

    public CComboxBox() {
        super();
        setBackground(Color.white);

        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
