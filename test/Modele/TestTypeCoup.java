package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TestTypeCoup {

    @Test
    public void testValeur() {
        assertEquals(0, TypeCoup.MOUVEMENT.valeur());
        assertEquals(1, TypeCoup.PLANTATION.valeur());
        assertEquals(2, TypeCoup.RECOLTE.valeur());
    }

    @Test
    public void testDepuisValeur() {
        assertEquals(TypeCoup.MOUVEMENT, TypeCoup.depuisValeur(0));
        assertEquals(TypeCoup.PLANTATION, TypeCoup.depuisValeur(1));
        assertEquals(TypeCoup.RECOLTE, TypeCoup.depuisValeur(2));
    }

    @Test
    public void testExceptionDepuisValeur() {
        for (int i = -100; i < 1000; i++) {
            if (i < 0 || i >= TypeCoup.NOMBRE) {
                final int n = i;
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> TypeCoup.depuisValeur(n));
                assertTrue(e.getMessage().contains("Aucune action correspondant à la valeur " + n));
            }
        }
    }

    @Test
    public void testToString() {
        assertEquals("Effectuer un mouvement", TypeCoup.MOUVEMENT.toString());
        assertEquals("Planter une graine", TypeCoup.PLANTATION.toString());
        assertEquals("Récolter une graine", TypeCoup.RECOLTE.toString());
    }
}
