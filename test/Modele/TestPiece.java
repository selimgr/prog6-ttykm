package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TestPiece {

    @Test
    public void testValeur() {
        assertEquals(1, Piece.BLANC.valeur());
        assertEquals(2, Piece.NOIR.valeur());
        assertEquals(4, Piece.GRAINE.valeur());
        assertEquals(8, Piece.ARBUSTE.valeur());
        assertEquals(16, Piece.ARBRE.valeur());
        assertEquals(32, Piece.ARBRE_COUCHE_HAUT.valeur());
        assertEquals(64, Piece.ARBRE_COUCHE_DROITE.valeur());
        assertEquals(128, Piece.ARBRE_COUCHE_BAS.valeur());
        assertEquals(256, Piece.ARBRE_COUCHE_GAUCHE.valeur());
    }

    @Test
    public void testDepuisValeur() {
        assertEquals(Piece.BLANC, Piece.depuisValeur(1));
        assertEquals(Piece.NOIR, Piece.depuisValeur(2));
        assertEquals(Piece.GRAINE, Piece.depuisValeur(4));
        assertEquals(Piece.ARBUSTE, Piece.depuisValeur(8));
        assertEquals(Piece.ARBRE, Piece.depuisValeur(16));
        assertEquals(Piece.ARBRE_COUCHE_HAUT, Piece.depuisValeur(32));
        assertEquals(Piece.ARBRE_COUCHE_DROITE, Piece.depuisValeur(64));
        assertEquals(Piece.ARBRE_COUCHE_BAS, Piece.depuisValeur(128));
        assertEquals(Piece.ARBRE_COUCHE_GAUCHE, Piece.depuisValeur(256));
    }

    @Test
    public void testExceptionDepuisValeur() {
        for (int i = -100; i < 1000; i++) {
            // Si i est une puissance de 2
            if (i == 0 || ((i & (i - 1)) != 0)) {
                final int n = i;
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Piece.depuisValeur(n));
                assertTrue(e.getMessage().contains("Aucune pièce correspondant à la valeur " + n));
            }
        }
    }

    @Test
    public void testToPion() {
        assertEquals(Pion.BLANC, Piece.BLANC.toPion());
        assertEquals(Pion.NOIR, Piece.NOIR.toPion());
        assertNull(Piece.GRAINE.toPion());
        assertNull(Piece.ARBUSTE.toPion());
        assertNull(Piece.ARBRE.toPion());
        assertNull(Piece.ARBRE_COUCHE_HAUT.toPion());
        assertNull(Piece.ARBRE_COUCHE_DROITE.toPion());
        assertNull(Piece.ARBRE_COUCHE_BAS.toPion());
        assertNull(Piece.ARBRE_COUCHE_GAUCHE.toPion());
    }

    @Test
    public void testDirectionArbreCouche() {
        assertEquals(Piece.ARBRE_COUCHE_HAUT, Piece.directionArbreCouche(-1, 0));
        assertEquals(Piece.ARBRE_COUCHE_DROITE, Piece.directionArbreCouche(0, 1));
        assertEquals(Piece.ARBRE_COUCHE_BAS, Piece.directionArbreCouche(1, 0));
        assertEquals(Piece.ARBRE_COUCHE_GAUCHE, Piece.directionArbreCouche(0, -1));
    }

    @Test
    public void testExceptionDirectionArbreCouche() {
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                if (i < -1 || j < -1 || i > 1 || j > 1 || (i == 0 && j == 0)) {
                    final int l = i;
                    final int c = j;
                    IllegalArgumentException e = assertThrows(
                            IllegalArgumentException.class,
                            () -> Piece.directionArbreCouche(l, c)
                    );
                    assertTrue(e.getMessage().contains(
                            "Impossible de renvoyer la direction de l'arbre couché : déplacement " +
                                l + ", " + c + " incorrect"));
                }
            }
        }
    }

    @Test
    public void testToString() {
        assertEquals("Blanc", Piece.BLANC.toString());
        assertEquals("Noir", Piece.NOIR.toString());
        assertEquals("Graine", Piece.GRAINE.toString());
        assertEquals("Arbuste", Piece.ARBUSTE.toString());
        assertEquals("Arbre", Piece.ARBRE.toString());
        assertEquals("Arbre couché vers le haut", Piece.ARBRE_COUCHE_HAUT.toString());
        assertEquals("Arbre couché vers la droite", Piece.ARBRE_COUCHE_DROITE.toString());
        assertEquals("Arbre couché vers le bas", Piece.ARBRE_COUCHE_BAS.toString());
        assertEquals("Arbre couché vers la gauche", Piece.ARBRE_COUCHE_GAUCHE.toString());
    }
}
