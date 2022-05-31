package Vue;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
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
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Chargement des assets
        logo = Imager.getImageBuffer("assets/logo.png");

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setForeground(new Color(255, 120, 105));
        progressBar.setIndeterminate(true);

        add(progressBar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();
        g.setColor(new Color(14, 14, 14));
        g.fillRect(0, 0, w, h);
        // --

        int logoWidth = (int) (w * 0.25);
        logoHeight = (logo.getHeight(null) * logoWidth) / logo.getWidth(null);

        int x = ((w - logoWidth) / 2) - 5;

        g.drawImage(logo, x, ((h-logoHeight)/2), logoWidth, logoHeight, null);
    }
}
