package Modele;

import java.util.Objects;

public class Case {
    private final int ligne, colonne;
    private final Epoque epoque;

    public Case(int ligne, int colonne, Epoque e) {
        Plateau.verifierCoordoneesCorrectes(ligne, colonne, e);
        this.ligne = ligne;
        this.colonne = colonne;
        epoque = e;
    }

    public int ligne() {
        return ligne;
    }

    public int colonne() {
        return colonne;
    }

    public Epoque epoque() {
        return epoque;
    }

    int indiceEpoque() {
        return epoque.indice();
    }

    @Override
    public String toString() {
        return "(" + ligne + ", " + colonne + ", " + epoque + ")";
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Case)) {
            return false;
        }
        Case c = (Case) o;

        for (int i = 0; i < attributs().length; ++i){
            if (!Objects.equals(attributs()[i], c.attributs()[i])){
                return false;
            }
        }
        return true;
    }

    @Override public int hashCode() {
        return Objects.hash(attributs());
    }

    private Object[] attributs() {
        return new Object[]{ligne, colonne, epoque};
    }
}
