package Modele;

import static java.util.Objects.requireNonNull;

public class Etat {
    private final Piece piece;
    private final Case avant, apres;

    Etat(Piece piece, Case avant, Case apres) {
        requireNonNull(piece, "La pièce ne doit pas être null");

        if (avant == null && apres == null) {
            throw new IllegalArgumentException("Impossible de créer un état avec les cases avant et après null");
        }
        this.piece = piece;
        this.avant = avant;
        this.apres = apres;
    }

    Piece piece() {
        return piece;
    }

    Case avant() {
        return avant;
    }

    Case apres() {
        return apres;
    }

    @Override
    public String toString() {
        return "[" + piece + " : " + avant + " -> " + apres + "]";
    }
}
