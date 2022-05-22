package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestEpoque {

    @Test
    public void testIndice() {
        assertEquals(0, Epoque.PASSE.indice());
        assertEquals(1, Epoque.PRESENT.indice());
        assertEquals(2, Epoque.FUTUR.indice());
    }

    @Test
    public void testDepuisIndice() {
        assertEquals(Epoque.PASSE, Epoque.depuisIndice(0));
        assertEquals(Epoque.PRESENT, Epoque.depuisIndice(1));
        assertEquals(Epoque.FUTUR, Epoque.depuisIndice(2));
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void testExceptionDepuisIndice() {
        for (int i = -100; i < 100; i++) {
            if (i < 0 || i > 2) {
                final int n = i;
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Epoque.depuisIndice(n));
                assertTrue(e.getMessage().contains("Aucune époque correspondant à l'indice " + n));
            }
        }
    }

    @Test
    public void testToString() {
        assertEquals("Passé", Epoque.PASSE.toString());
        assertEquals("Présent", Epoque.PRESENT.toString());
        assertEquals("Futur", Epoque.FUTUR.toString());
    }

    @Test
    public void testNombreEpoques() {
        assertEquals(3, Epoque.NOMBRE);
    }
}
