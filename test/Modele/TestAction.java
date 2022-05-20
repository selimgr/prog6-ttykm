package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TestAction {

    @Test
    public void testValeur() {
        assertEquals(0, Action.MOUVEMENT.valeur());
        assertEquals(1, Action.PLANTATION.valeur());
        assertEquals(2, Action.RECOLTE.valeur());
    }

    @Test
    public void testDepuisValeur() {
        assertEquals(Action.MOUVEMENT, Action.depuisValeur(0));
        assertEquals(Action.PLANTATION, Action.depuisValeur(1));
        assertEquals(Action.RECOLTE, Action.depuisValeur(2));
    }

    @Test
    public void testExceptionDepuisValeur() {
        for (int i = -100; i < 1000; i++) {
            if (i < 0 || i >= Action.NOMBRE) {
                final int n = i;
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Action.depuisValeur(n));
                assertTrue(e.getMessage().contains("Aucune action correspondant à la valeur " + n));
            }
        }
    }

    @Test
    public void testToString() {
        assertEquals("Mouvement", Action.MOUVEMENT.toString());
        assertEquals("Plantation", Action.PLANTATION.toString());
        assertEquals("Récolte", Action.RECOLTE.toString());
    }
}
