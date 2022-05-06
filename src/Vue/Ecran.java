package Vue;

import javax.swing.*;

public abstract class Ecran extends JPanel {
    EcouteurInterface listener;

    Ecran(EcouteurInterface listener) {
        this.listener = listener;
    }
}
