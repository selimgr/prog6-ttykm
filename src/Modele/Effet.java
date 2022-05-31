package Modele;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class Effet implements Serializable {
    private final Piece piece;
    private final Case depart, arrivee;

    Effet(Piece piece, Case depart, Case arrivee) {
        requireNonNull(piece, "La pièce ne doit pas être null");

        if (depart == null && arrivee == null) {
            throw new IllegalArgumentException("Impossible de créer un état avec les cases avant et après null");
        }
        this.piece = piece;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    Piece piece() {
        return piece;
    }

    Case depart() {
        return depart;
    }

    Case arrivee() {
        return arrivee;
    }

    @Override
    public String toString() {
        return "[" + piece + " : " + depart + " -> " + arrivee + "]";
    }
}
