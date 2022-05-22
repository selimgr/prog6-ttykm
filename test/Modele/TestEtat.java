package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestEtat {

    @Test
    public void testInitialisation() {
        for (int i = 1; i <= 256; i *= 2) {
            Piece p = Piece.depuisValeur(i);

            for (int j = 0; j < Epoque.NOMBRE; j++) {
                Epoque e = Epoque.depuisIndice(j);

                for (int k = 0; k < Plateau.TAILLE; k++) {
                    for (int l = 0; l < Plateau.TAILLE; l++) {
                        Case c = new Case(k, l, e);
                        Etat etat = new Etat(p, c, null);
                        assertEquals(p, etat.piece());
                        assertEquals(c, etat.depart());
                        assertNull(etat.arrivee());
                        etat = new Etat(p, null, c);
                        assertEquals(p, etat.piece());
                        assertNull(etat.depart());
                        assertEquals(c, etat.arrivee());
                        etat = new Etat(p, c, c);
                        assertEquals(p, etat.piece());
                        assertEquals(c, etat.depart());
                        assertEquals(c, etat.arrivee());
                    }
                }
            }
        }
    }

    private void exceptionPieceNull(Case depart, Case arrivee) {
        NullPointerException e = assertThrows(
                NullPointerException.class,
                () -> new Etat(null, depart, arrivee)
        );
        assertTrue(e.getMessage().contains("La pièce ne doit pas être null"));
    }

    @Test
    public void testExceptionPieceNull() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    Case c = new Case(j, k, e);
                    exceptionPieceNull(c, null);
                    exceptionPieceNull(null, c);
                    exceptionPieceNull(c, c);
                }
            }
        }
    }

    @Test
    public void testExceptionDeuxCasesNull() {
        for (int i = 1; i <= 256; i *= 2) {
            Piece p = Piece.depuisValeur(i);

            IllegalArgumentException e = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Etat(p, null, null)
            );
            assertTrue(e.getMessage().contains("Impossible de créer un état avec les cases avant et après null"));
        }
    }

    @Test
    public void testToString() {
        Etat e = new Etat(Piece.BLANC, new Case(0, 1, Epoque.PASSE), null);
        assertEquals("[Blanc : (0, 1, Passé) -> null]", e.toString());
        e = new Etat(Piece.ARBRE, new Case(2, 1, Epoque.PRESENT), new Case(2, 1, Epoque.FUTUR));
        assertEquals("[Arbre : (2, 1, Présent) -> (2, 1, Futur)]", e.toString());
        e = new Etat(Piece.NOIR, null, new Case(3, 3, Epoque.FUTUR));
        assertEquals("[Noir : null -> (3, 3, Futur)]", e.toString());
    }
}
