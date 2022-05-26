package Modele;

import static java.util.Objects.requireNonNull;

public class Joueur {
    private final String nom;
    private final TypeJoueur type;
    private final Pion pions;
    private final int handicap;
    private Epoque focus;
    private int nombrePionsReserve;
    private int nombreVictoires;

    static int HANDICAP_MAX = 3;

    Joueur(String nom, TypeJoueur type, Pion pions, int handicap) {
        requireNonNull(nom, "Le nom du joueur ne doit pas être null");
        requireNonNull(type, "Le type du joueur ne doit pas être null");
        requireNonNull(pions, "Le type de pions du joueur ne doit pas être null");
        this.nom = nom;
        this.type = type;
        this.pions = pions;
        this.handicap = handicap;

        if (handicap < 0 || handicap > HANDICAP_MAX) {
            throw new IllegalStateException("Handicap " + handicap + " incorrect");
        }
    }

    void initialiserJoueur() {
        // blanc commence dans le passé
        if (pions == Pion.BLANC) {
            fixerFocus(Epoque.PASSE);
        }
        // noir commence dans le futur
        else {
            fixerFocus(Epoque.FUTUR);
        }
        nombrePionsReserve = Pion.NOMBRE_MAX_RESERVE - handicap;
    }

    public String nom() {
        return nom;
    }

    public boolean estHumain() {
        return type == TypeJoueur.HUMAIN;
    }

    public TypeJoueur type() {
        return type;
    }

    public boolean estIaFacile() {
        return type == TypeJoueur.IA_FACILE;
    }

    public boolean estIaMoyen() {
        return type == TypeJoueur.IA_MOYEN;
    }

    public boolean estIaDifficile() {
        return type == TypeJoueur.IA_DIFFICILE;
    }

    public Pion pions() {
        return pions;
    }

    public boolean aPionsBlancs() {
        return pions == Pion.BLANC;
    }

    public boolean aPionsNoirs() {
        return pions == Pion.NOIR;
    }

    public int handicap() {
        return handicap;
    }

    public Epoque focus() {
        return focus;
    }

    public boolean aFocusPasse() {
        return focus == Epoque.PASSE;
    }

    public boolean aFocusPresent() {
        return focus == Epoque.PRESENT;
    }

    public boolean aFocusFutur() {
        return focus == Epoque.FUTUR;
    }

    public void fixerFocus(Epoque e) {
        requireNonNull(e, "L'époque du focus du joueur ne doit pas être null");
        focus = e;
    }

    public int nombrePionsReserve() {
        return nombrePionsReserve;
    }

    void ajouterPionReserve() {
        if (nombrePionsReserve == Pion.NOMBRE_MAX_RESERVE) {
            throw new IllegalStateException("Impossible d'ajouter un pion au joueur : réserve de pions pleine");
        }
        nombrePionsReserve++;
    }

    void enleverPionReserve() {
        if (nombrePionsReserve == 0) {
            throw new IllegalStateException("Impossible d'enlever un pion au joueur : aucune pion en réserve");
        }
        nombrePionsReserve--;
    }

    public int nombreVictoires() {
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

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom + "'" +
                ", type=" + type +
                ", pions=" + pions +
                ", handicap=" + handicap +
                ", focus=" + focus +
                ", nombrePionsReserve=" + nombrePionsReserve +
                ", nombreVictoires=" + nombreVictoires +
                "}";
    }

    //temporaire
    public void setNombrePionsReserve(int nbP){
        nombrePionsReserve =nbP;
    }
}
