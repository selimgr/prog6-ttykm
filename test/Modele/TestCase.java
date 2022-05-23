package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCase {

    @Test
    public void testInitialisation() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    Case c = new Case(j, k, e);
                    assertEquals(j, c.ligne());
                    assertEquals(k, c.colonne());
                    assertEquals(e, c.epoque());
                    assertEquals(i, c.indiceEpoque());
                }
            }
        }
    }

    private void exceptionCoordonneesIncorrectes(int l, int c, Epoque e) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Case(l, c, e)
        );
        assertTrue(thrown.getMessage().contains("Coordonnées (" + l + ", " + c + ", " + e + ") incorrectes"));
    }

    @Test
    public void testExceptionCoordonneesIncorrectes() {
        for (int i = -100; i < 100; i++) {
            for (int j = -100; j < 100; j++) {
                if (i < 0 || i >= Plateau.TAILLE || j < 0 || j >= Plateau.TAILLE) {
                    exceptionCoordonneesIncorrectes(i, j, Epoque.PASSE);
                    exceptionCoordonneesIncorrectes(i, j, Epoque.PRESENT);
                    exceptionCoordonneesIncorrectes(i, j, Epoque.FUTUR);
                }
            }
        }
    }

    @Test
    public void testExceptionEpoqueNull() {
        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                final int l = i;
                final int c = j;
                NullPointerException e = assertThrows(
                        NullPointerException.class,
                        () -> new Case(l, c, null)
                );
                assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));
            }
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    Case c = new Case(j, k, e);
                    assertEquals("(" + j + ", " + k + ", " + e + ")", c.toString());
                }
            }
        }
    }

    @Test
    public void testEquals() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    Case c1 = new Case(j, k , e);
                    Case c2 = new Case(j, k, e);
                    assertEquals(c1, c2);
                    assertEquals(c1.hashCode(), c2.hashCode());
                }
            }
        }
    }

    @Test
    public void testNotEquals() {
        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                Case c1 = new Case(i, j , Epoque.PASSE);
                Case c2 = new Case(i, j, Epoque.PRESENT);
                Case c3 = new Case(i, j, Epoque.FUTUR);
                assertNotEquals(c1, c2);
                assertNotEquals(c1, c3);
                assertNotEquals(c2, c3);
            }
        }
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    if (i != j) {
                        Case c1 = new Case(i, j, e);
                        Case c2 = new Case(j, i, e);
                        assertNotEquals(c1, c2);
                    }
                }
            }
        }
    }
}
