package Modele;

/**
 * Gérer ordres des mouvements
 */
public class Mouvement {
    Case depart, arrivee;

    Mouvement(Case depart, Case arrivee, int typeCase) {
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
