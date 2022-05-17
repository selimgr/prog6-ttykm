package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TestPion {

    @Test
    public void testValeur() {
        assertEquals(1, Pion.BLANC.valeur());
        assertEquals(2, Pion.NOIR.valeur());
        assertEquals(Piece.BLANC.valeur(), Pion.BLANC.valeur());
        assertEquals(Piece.NOIR.valeur(), Pion.NOIR.valeur());
    }

    @Test
    public void testDepuisValeur() {
        assertEquals(Pion.BLANC, Pion.depuisValeur(1));
        assertEquals(Pion.NOIR, Pion.depuisValeur(2));
    }

    @Test
    public void testExceptionDepuisValeur() {
        for (int i = -100; i < 1000; i++) {
            if (i != 1 && i != 2) {
                final int n = i;
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Pion.depuisValeur(n));
                assertTrue(e.getMessage().contains("Aucun pion correspondant à la valeur " + n));
            }
        }
    }

    @Test
    public void testToPiece() {
        assertEquals(Piece.BLANC, Pion.BLANC.toPiece());
        assertEquals(Piece.NOIR, Pion.NOIR.toPiece());
    }

    @Test
    public void testToString() {
        assertEquals("Blanc", Pion.BLANC.toString());
        assertEquals("Noir", Pion.NOIR.toString());
    }
}
