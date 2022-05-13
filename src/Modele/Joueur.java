package Modele;

public class Joueur {
    private final String nom;
    private TypeJoueur type;
    private Epoque focus;
    private Pion pions;
    private int nombrePionsReserve;
    private int nombreVictoires;

    Joueur(String nom, TypeJoueur type) {
        this.nom = nom;
        this.type = type;
    }

    void initialiserJoueur(Pion p, int handicap) {
        pions = p;

        //blanc commence dans le passé
        if (p == Pion.BLANC) {
            fixerFocus(Epoque.PASSE);
        }
        //noir commence dans le futur
        else {
            fixerFocus(Epoque.FUTUR);
        }
        if (handicap < 0 || handicap > 3) {
            throw new IllegalStateException("Handicap " + handicap + " incorrect");
        }
        nombrePionsReserve = 4 - handicap;
    }

    public String nom() {
        return nom;
    }

    public TypeJoueur type() {
        return type;
    }

    public Epoque focus() {
        return focus;
    }

    void fixerFocus(Epoque e) {
        focus = e;
    }

    public Pion pions() {
        return pions;
    }

    public int nombrePionsReserve() {
        return nombrePionsReserve;
    }

    void ajouterPionReserve() {
        nombrePionsReserve++;
    }

    void enleverPionReserve() {
        nombrePionsReserve--;
    }

    public int nombreVictoires() {
        return nombreVictoires;
    }

    void ajouterVictoire() {
        nombreVictoires++;
    }

    void enleverVictoire() {
        nombreVictoires--;
    }
}
