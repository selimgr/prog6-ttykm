package Modele;


public class Niveau {
    final static int NOMBRE_PLATEAUX = 3;
    Plateau[] plateaux;

    Niveau() {
        this.plateaux = new Plateau[NOMBRE_PLATEAUX];
        for(int i = 0 ; i < NOMBRE_PLATEAUX ; i++){
            this.plateaux[i] = new Plateau();
        }

    }

    Coup jouerCoup(Case depart, Case arrivee) {
        return null;
    }

    void annulerCoup(Coup coup) {

    }

    Plateau plateau(TypePlateau p) {
        return null;
    }
}
