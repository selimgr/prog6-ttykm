package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;

public class CComboxBoxEditor extends BasicComboBoxEditor {

    private JLabel label = new JLabel();
    private JPanel panel = new JPanel();
    private Object selectedItem;

    public CComboxBoxEditor() {
        label.setOpaque(false);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.BLACK);
        label.setBorder(new EmptyBorder(2, 2, 2, 2));

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.add(label);
        panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        panel.setBackground(Color.WHITE);
    }

    public Component getEditorComponent() {
        return this.panel;
    }

    public Object getItem() {
        return this.selectedItem.toString();
    }

    public void setItem(Object item) {
        this.selectedItem = item;
        label.setText(item.toString());
    }
}
