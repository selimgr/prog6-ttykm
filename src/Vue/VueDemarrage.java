package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class VueDemarrage extends JPanel {

    Image t;
    Image logo;
    int logoHeight;
    int called = 0;

    VueDemarrage() {
        setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Chargement des assets
        t = Imager.getImageBuffer("assets/topbanner.png");
        logo = Imager.getImageBuffer("assets/logo.png");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = new Color(255, 140, 85);
        Color color2 = new Color(255, 120, 105);
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

        int width = (int) (getWidth() * 1.7);
        int height = (t.getHeight(null) * width) / t.getWidth(null);

        g.drawImage(t, 0, 0, width, height, null);
        // --

        width = (int) (getWidth() * 0.25);
        logoHeight = (logo.getHeight(null) * width) / logo.getWidth(null);

        int x = ((getWidth() - width) / 2) - 5;

        g.drawImage(logo, x, 35, width, logoHeight, null);
        if (called == 0) {
            setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            called = 1;
        }
    }
}
