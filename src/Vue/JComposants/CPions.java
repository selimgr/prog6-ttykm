package Vue.JComposants;

import javax.swing.*;
import java.awt.*;

public class CPions extends JPanel {
    private final int hgap = 5;
    private boolean reverse = false;
    public CPions(boolean reverse) {
//        this.reverse = reverse;
        setOpaque(false);
        setBackground(null);
        setLayout(new GridLayout(1, 0, hgap, 0));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Pour que ce soit bien smoooooooooooooooth
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.setColor(!reverse ? Color.white : new Color(29, 29, 29));
        g2.fillRoundRect(0, 0, getSize().width-1, getSize().height-1, 45, 45);

        super.paintComponent(g);
    }
}
