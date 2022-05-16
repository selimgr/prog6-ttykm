package Modele;

import java.util.Objects;

public class Joueur {
    private final String nom;
    private TypeJoueur type;
    private Epoque focus;
    private Pion pions;
    private int nombrePionsReserve;
    private int nombreVictoires;

    Joueur(String nom, TypeJoueur type, Pion p, int handicap) {
        Objects.requireNonNull(nom);
        Objects.requireNonNull(type);
        Objects.requireNonNull(p);
        this.nom = nom;
        this.type = type;
        pions = p;

        // blanc commence dans le passé
        if (pions == Pion.BLANC) {
            fixerFocus(Epoque.PASSE);
        }
        // noir commence dans le futur
        else {
            fixerFocus(Epoque.FUTUR);
        }

        if (handicap < 0 || handicap > 3) {
            throw new IllegalStateException("Handicap " + handicap + " incorrect");
        }
        nombrePionsReserve = Pion.NOMBRE_RESERVE - handicap;
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
        Objects.requireNonNull(e);
        focus = e;
    }

    public Pion pions() {
        return pions;
    }

    int nombrePionsReserve() {
        return nombrePionsReserve;
    }

    void ajouterPionReserve() {
        nombrePionsReserve++;
    }

    void enleverPionReserve() {
        if (nombrePionsReserve == 0) {
            throw new IllegalStateException("Impossible d'enlever un pion au joueur : aucune pion en réserve");
        }
        nombrePionsReserve--;
    }

    int nombreVictoires() {
        return nombreVictoires;
    }

    void ajouterVictoire() {
        nombreVictoires++;
    }

    void enleverVictoire() {
        if (nombreVictoires == 0) {
            throw new IllegalStateException("Impossible d'enlever une victoire au joueur : aucune victoire");
        }
        nombreVictoires--;
    }
}
