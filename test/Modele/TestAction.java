package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TestAction {

    @Test
    public void testValeur() {
        assertEquals(0, Action.SELECTION_PION.valeur());
        assertEquals(1, Action.MOUVEMENT.valeur());
        assertEquals(2, Action.PLANTATION.valeur());
        assertEquals(3, Action.RECOLTE.valeur());
        assertEquals(4, Action.CHANGEMENT_FOCUS.valeur());
    }

    @Test
    public void testDepuisValeur() {
        assertEquals(Action.SELECTION_PION, Action.depuisValeur(0));
        assertEquals(Action.MOUVEMENT, Action.depuisValeur(1));
        assertEquals(Action.PLANTATION, Action.depuisValeur(2));
        assertEquals(Action.RECOLTE, Action.depuisValeur(3));
        assertEquals(Action.CHANGEMENT_FOCUS, Action.depuisValeur(4));
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
        assertEquals("Sélectionner un pion", Action.SELECTION_PION.toString());
        assertEquals("Mouvement", Action.MOUVEMENT.toString());
        assertEquals("Planter une graine", Action.PLANTATION.toString());
        assertEquals("Récolter une graine", Action.RECOLTE.toString());
        assertEquals("Changer le focus d'époque", Action.CHANGEMENT_FOCUS.toString());
    }
}
