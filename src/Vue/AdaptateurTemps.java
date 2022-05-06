package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurTemps implements ActionListener {
    CollecteurEvenements controleur;

    AdaptateurTemps(CollecteurEvenements c) {
        controleur = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
