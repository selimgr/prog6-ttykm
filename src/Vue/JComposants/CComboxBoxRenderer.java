package Vue.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CComboxBoxRenderer extends JPanel implements ListCellRenderer {
    private JLabel labelItem = new JLabel();
    private Color itemBackground = new Color(255, 255, 255);
    private Color itemSelectedBackground = new Color(0, 0, 0);
    private Color itemSelectedForeground = new Color(255, 255, 255);
    private Color itemForeground = new Color(0, 0, 0);

    public CComboxBoxRenderer() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);

        labelItem.setBorder(new EmptyBorder(4, 4, 4, 4));
        labelItem.setOpaque(true);
        labelItem.setHorizontalAlignment(JLabel.LEFT);
        labelItem.setFont(new Font("Arial", Font.PLAIN, 14));
        labelItem.setForeground(new Color(40, 40, 40));

        add(labelItem, constraints);
        setBackground(itemBackground);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        labelItem.setText(value.toString());

        if (isSelected) {
            labelItem.setBackground(itemSelectedBackground);
            labelItem.setForeground(itemSelectedForeground);
        } else {
            labelItem.setBackground(itemBackground);
            labelItem.setForeground(itemForeground);
        }

        return this;
    }

}