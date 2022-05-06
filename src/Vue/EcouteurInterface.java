package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurInterface implements ActionListener {
    JFrame frame;

    EcouteurInterface(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();

        switch (e.getActionCommand()) {
            case "Menu Principal":
                layout.show(frame.getContentPane(), "Menu Principal");
                frame.setTitle("Menu Principal");
                break;
            case "Jeu":
                layout.show(frame.getContentPane(), "Jeu");
                frame.setTitle("Jeu");
                break;
            default:
                break;
        }
    }
}
