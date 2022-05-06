package Patterns;

import java.util.LinkedList;
import java.util.List;

public class Observable {
    List<Observateur> observateurs;

    public Observable() {
        observateurs = new LinkedList<>();
    }

    public void ajouteObservateur(Observateur o) {
        observateurs.add(o);
    }

    public void metAJour() {
        for (Observateur o : observateurs) {
            o.miseAJour();
        }
    }
}
