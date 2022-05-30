package Vue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.invoke.SwitchPoint;

class AdaptateurClavier extends KeyAdapter {
    CollecteurEvenements controleur;

    AdaptateurClavier(CollecteurEvenements c) {
        controleur = c;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        //if(controleur.jeu().partieTerminee()){
        //  return;
        //}
        switch (event.getKeyCode()) {
            case KeyEvent.VK_I:
                controleur.toucheClavier("IA");
                break;
            case KeyEvent.VK_LEFT:
                controleur.toucheClavier("Annuler");
                break;
            case KeyEvent.VK_RIGHT:
                controleur.toucheClavier("Refaire");
                break;
            default:
                System.out.println("Touche non supportée");
        }
    }
}
