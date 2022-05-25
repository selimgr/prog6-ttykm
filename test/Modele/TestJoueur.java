package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestJoueur {
    Joueur j;

    @Before
    public void initialiserJoueur() {
        j = new Joueur("abc", TypeJoueur.HUMAIN, Pion.BLANC, 0);
        j.initialiserJoueur();
    }

    @Test
    public void testInitialisation() {
        for (int i = 97; i < 123; i++) {
            String nom = "" + (char) i;

            for (int j = 0; j < TypeJoueur.NOMBRE; j++) {
                TypeJoueur type = TypeJoueur.depuisIndice(j);

                for (int k = 1; k < 3; k++) {
                    Pion pion = Pion.depuisValeur(k);

                    for (int l = 0; l < Joueur.HANDICAP_MAX; l++) {
                        Joueur joueur = new Joueur(nom, type, pion, l);
                        assertEquals(nom, joueur.nom());
                        assertEquals(type, joueur.type());

                        switch (type) {
                            case HUMAIN:
                                assertTrue(joueur.estHumain());
                                assertFalse(joueur.estIaFacile());
                                assertFalse(joueur.estIaMoyen());
                                assertFalse(joueur.estIaDifficile());
                                break;
                            case IA_FACILE:
                                assertFalse(joueur.estHumain());
                                assertTrue(joueur.estIaFacile());
                                assertFalse(joueur.estIaMoyen());
                                assertFalse(joueur.estIaDifficile());
                                break;
                            case IA_MOYEN:
                                assertFalse(joueur.estHumain());
                                assertFalse(joueur.estIaFacile());
                                assertTrue(joueur.estIaMoyen());
                                assertFalse(joueur.estIaDifficile());
                                break;
                            case IA_DIFFICILE:
                                assertFalse(joueur.estHumain());
                                assertFalse(joueur.estIaFacile());
                                assertFalse(joueur.estIaMoyen());
                                assertTrue(joueur.estIaDifficile());
                                break;
                        }

                        assertEquals(pion, joueur.pions());
                        assertEquals(l, joueur.handicap());

                        joueur.initialiserJoueur();
                        if (pion == Pion.BLANC) {
                            assertTrue(joueur.aPionsBlancs());
                            assertFalse(joueur.aPionsNoirs());
                            assertTrue(joueur.aFocusPasse());
                            assertFalse(joueur.aFocusPresent());
                            assertFalse(joueur.aFocusFutur());
                            assertEquals(Epoque.PASSE, joueur.focus());
                        } else {
                            assertFalse(joueur.aPionsBlancs());
                            assertTrue(joueur.aPionsNoirs());
                            assertFalse(joueur.aFocusPasse());
                            assertFalse(joueur.aFocusPresent());
                            assertTrue(joueur.aFocusFutur());
                            assertEquals(Epoque.FUTUR, joueur.focus());
                        }

                        switch (l) {
                            case 0:
                                assertEquals(4, joueur.nombrePionsReserve());
                                break;
                            case 1:
                                assertEquals(3, joueur.nombrePionsReserve());
                                break;
                            case 2:
                                assertEquals(2, joueur.nombrePionsReserve());
                                break;
                            case 3:
                                assertEquals(1, joueur.nombrePionsReserve());
                                break;
                        }
                        assertEquals(0, joueur.nombreVictoires());
                    }
                }
            }
        }
    }

    @Test
    public void testExceptionHandicapIncorrect() {
        for (int i = -100; i < 100; i++) {
            if (i < 0 || i > Joueur.HANDICAP_MAX) {
                final int handicap = i;
                IllegalStateException e = assertThrows(
                        IllegalStateException.class,
                        () -> new Joueur("abc", TypeJoueur.HUMAIN, Pion.BLANC, handicap)
                );
                assertTrue(e.getMessage().contains("Handicap " + handicap + " incorrect"));
            }
        }
    }

    @Test
    public void testFocus() {
        j.fixerFocus(Epoque.PASSE);
        assertTrue(j.aFocusPasse());
        assertFalse(j.aFocusPresent());
        assertFalse(j.aFocusFutur());
        assertEquals(Epoque.PASSE, j.focus());
        j.fixerFocus(Epoque.PRESENT);
        assertFalse(j.aFocusPasse());
        assertTrue(j.aFocusPresent());
        assertFalse(j.aFocusFutur());
        assertEquals(Epoque.PRESENT, j.focus());
        j.fixerFocus(Epoque.FUTUR);
        assertFalse(j.aFocusPasse());
        assertFalse(j.aFocusPresent());
        assertTrue(j.aFocusFutur());
        assertEquals(Epoque.FUTUR, j.focus());
    }

    @Test
    public void testNombrePions() {
        assertEquals(Pion.NOMBRE_MAX_RESERVE, j.nombrePionsReserve());
        j.enleverPionReserve();
        assertEquals(Pion.NOMBRE_MAX_RESERVE - 1, j.nombrePionsReserve());
        j.ajouterPionReserve();
        assertEquals(Pion.NOMBRE_MAX_RESERVE, j.nombrePionsReserve());

        for (int i = 0; i < Pion.NOMBRE_MAX_RESERVE; i++) {
            j.enleverPionReserve();
            assertEquals(Pion.NOMBRE_MAX_RESERVE - i - 1, j.nombrePionsReserve());
        }
        assertEquals(0, j.nombrePionsReserve());

        for (int i = 0; i < Pion.NOMBRE_MAX_RESERVE; i++) {
            j.ajouterPionReserve();
            assertEquals(i + 1, j.nombrePionsReserve());
        }
        assertEquals(Pion.NOMBRE_MAX_RESERVE, j.nombrePionsReserve());
    }

    @Test
    public void testExceptionAjouterPion() {
        IllegalStateException e = assertThrows(IllegalStateException.class, j::ajouterPionReserve);
        assertTrue(e.getMessage().contains("Impossible d'ajouter un pion au joueur : réserve de pions pleine"));
    }

    @Test
    public void testExceptionEnleverPion() {
        while (j.nombrePionsReserve() > 0) {
            j.enleverPionReserve();
        }
        IllegalStateException e = assertThrows(IllegalStateException.class, j::enleverPionReserve);
        assertTrue(e.getMessage().contains("Impossible d'enlever un pion au joueur : aucune pion en réserve"));
    }

    @Test
    public void testNombreVictoires() {
        assertEquals(0, j.nombreVictoires());
        j.ajouterVictoire();
        assertEquals(1, j.nombreVictoires());
        j.enleverVictoire();
        assertEquals(0, j.nombreVictoires());

        for (int i = 0; i < 100; i++) {
            j.ajouterVictoire();
            assertEquals(i + 1, j.nombreVictoires());
        }
        assertEquals(100, j.nombreVictoires());

        for (int i = 100; i > 0; i--) {
            j.enleverVictoire();
            assertEquals(i - 1, j.nombreVictoires());
        }
        assertEquals(0, j.nombreVictoires());
    }

    @Test
    public void testExceptionEnleverVictoire() {
        IllegalStateException e = assertThrows(IllegalStateException.class, j::enleverVictoire);
        assertTrue(e.getMessage().contains("Impossible d'enlever une victoire au joueur : aucune victoire"));
    }

    @Test
    public void testExceptionsArgumentNull() {
        NullPointerException e;

        e = assertThrows(
                NullPointerException.class,
                () -> new Joueur(null, TypeJoueur.HUMAIN, Pion.BLANC, 0)
        );
        assertTrue(e.getMessage().contains("Le nom du joueur ne doit pas être null"));

        e = assertThrows(
                NullPointerException.class,
                () -> new Joueur("abc", null, Pion.BLANC, 0)
        );
        assertTrue(e.getMessage().contains("Le type du joueur ne doit pas être null"));

        e = assertThrows(
                NullPointerException.class,
                () -> new Joueur("abc", TypeJoueur.HUMAIN, null, 0)
        );
        assertTrue(e.getMessage().contains("Le type de pions du joueur ne doit pas être null"));

        e = assertThrows(
                NullPointerException.class,
                () -> j.fixerFocus(null)
        );
        assertTrue(e.getMessage().contains("L'époque du focus du joueur ne doit pas être null"));
    }

    @Test
    public void testToString() {
        assertEquals("Joueur{nom='abc', type=Humain, pions=Blanc, handicap=0, focus=Passé, nombrePionsReserve=4" +
                ", nombreVictoires=0}", j.toString());
        j = new Joueur("def", TypeJoueur.IA_MOYEN, Pion.NOIR, 3);
        j.initialiserJoueur();
        assertEquals("Joueur{nom='def', type=IA Moyen, pions=Noir, handicap=3, focus=Futur, nombrePionsReserve=1" +
                ", nombreVictoires=0}", j.toString());
    }
}
