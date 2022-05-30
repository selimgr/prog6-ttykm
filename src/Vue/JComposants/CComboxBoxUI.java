package Vue.JComposants;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CComboxBoxUI extends BasicComboBoxUI {
    public CComboxBoxUI() {
        this.arrowButton.setBackground(Color.white);
        this.listBox.setBackground(Color.white);
        this.listBox.setSelectionBackground(Color.red);
    }
}
