package Modele;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

public class Historique implements Serializable {
    private final Deque<Tour> precedents;
    private Tour actuel;
    private Deque<Tour> suivants;

    Historique() {
        precedents = new ArrayDeque<>();
        suivants = new ArrayDeque<>();
    }

    boolean peutAnnuler() {
        requireNonNull(actuel, "Le tour actuel ne doit pas être null");
        return actuel.pionSelectionne() || !precedents.isEmpty();
    }

    boolean peutRefaire() {
        requireNonNull(actuel, "Le tour actuel ne doit pas être null");
        return actuel.peutRefaire() || !suivants.isEmpty();
    }

    void reinitialiserToursSuivants() {
        suivants = new ArrayDeque<>();
    }

    Tour nouveauTour(Epoque focus) {
        if (actuel != null) {
            precedents.push(actuel);
        }
        actuel = new Tour(focus);
        return actuel;
    }

    Tour tourActuel() {
        requireNonNull(actuel, "Le tour actuel ne doit pas être null");
        return actuel;
    }

    Tour tourPrecedent() {
        if (precedents.isEmpty()) {
            throw new NoSuchElementException("Impossible de revenir au tour précédent : aucun tour précédent");
        }
        suivants.push(actuel);
        actuel = precedents.pop();
        return actuel;
    }

    Tour tourSuivant() {
        if (suivants.isEmpty()) {
            throw new NoSuchElementException("Impossible de rétablir le tour suivant : aucun tour suivant");
        }
        precedents.push(actuel);
        actuel = suivants.pop();
        return actuel;
    }
}
