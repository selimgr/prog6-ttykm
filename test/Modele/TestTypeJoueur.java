package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestTypeJoueur {

    @Test
    public void testIndice() {
        assertEquals(0, TypeJoueur.HUMAIN.indice());
        assertEquals(1, TypeJoueur.IA_FACILE.indice());
        assertEquals(2, TypeJoueur.IA_MOYEN.indice());
        assertEquals(3, TypeJoueur.IA_DIFFICILE.indice());
    }

    @Test
    public void testDepuisIndice() {
        assertEquals(TypeJoueur.HUMAIN, TypeJoueur.depuisIndice(0));
        assertEquals(TypeJoueur.IA_FACILE, TypeJoueur.depuisIndice(1));
        assertEquals(TypeJoueur.IA_MOYEN, TypeJoueur.depuisIndice(2));
        assertEquals(TypeJoueur.IA_DIFFICILE, TypeJoueur.depuisIndice(3));
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void testExceptionDepuisIndice() {
        for (int i = -100; i < 100; i++) {
            if (i < 0 || i > 3) {
                final int n = i;
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> TypeJoueur.depuisIndice(n));
                assertTrue(e.getMessage().contains("Aucun type de joueur correspondant à l'indice " + n));
            }
        }
    }

    @Test
    public void testToString() {
        assertEquals("Humain", TypeJoueur.HUMAIN.toString());
        assertEquals("IA Facile", TypeJoueur.IA_FACILE.toString());
        assertEquals("IA Moyen", TypeJoueur.IA_MOYEN.toString());
        assertEquals("IA Difficile", TypeJoueur.IA_DIFFICILE.toString());
    }
}
