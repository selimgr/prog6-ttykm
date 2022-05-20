package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurFinTour implements ActionListener {
    CollecteurEvenements c;

    AdaptateurFinTour(CollecteurEvenements c){
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
}
