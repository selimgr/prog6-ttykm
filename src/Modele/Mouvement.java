package Modele;

public class Mouvement {
    Case depart, arrivee;

    Mouvement(Case depart, Case arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }

    Case depart() {
        return depart;
    }

    Case arrivee() {
        return arrivee;
    }
}
