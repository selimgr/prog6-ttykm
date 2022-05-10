package Patterns;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observateur> observateurs;

    public Observable() {
        observateurs = new ArrayList<>(1);
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
