package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CComboxBox extends JComboBox {

    public CComboxBox() {
        super();
        setEditor(new CComboxBoxEditor());
        setRenderer(new CComboxBoxRenderer());
        setUI(new BasicComboBoxUI(){
            @Override
            protected JButton createArrowButton() {
                JButton b = new CButton().carre();
                b.setBackground(new Color(47, 47, 47, 255));
                b.setText("▼");
                b.setFocusPainted(false);
                b.setFocusable(false);
                b.setBorder(new EmptyBorder(0,0,0,0));
                b.setBorderPainted(false);
                return b;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
