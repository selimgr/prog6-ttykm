package Modele;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestEtat {

    @Test
    public void testInitialisation() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    int l = 0;

                    while (l < Piece.NOMBRE) {
                        Piece avant;
                        if (l == 0) {
                            avant = null;
                            l++;
                        } else {
                            avant = Piece.depuisValeur(l);
                            l *= 2;
                        }
                        int m = 0;

                        while (m < Piece.NOMBRE) {
                            Piece apres;
                            if (m == 0) {
                                apres = null;
                                m++;
                            } else {
                                apres = Piece.depuisValeur(m);
                                m *= 2;
                            }
                            Etat q = new Etat(j, k, e, avant, apres);

                            assertEquals(j, q.ligne());
                            assertEquals(k, q.colonne());
                            assertEquals(e, q.epoque());
                            assertEquals(avant, q.pieceAvant());
                            assertEquals(apres, q.pieceApres());
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testExceptionEpoqueNull() {
        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                int k = 0;

                while (k < Piece.NOMBRE) {
                    Piece avant;
                    if (k == 0) {
                        avant = null;
                        k++;
                    } else {
                        avant = Piece.depuisValeur(k);
                        k *= 2;
                    }
                    int l = 0;

                    while (l < Piece.NOMBRE) {
                        Piece apres;
                        if (l == 0) {
                            apres = null;
                            l++;
                        } else {
                            apres = Piece.depuisValeur(l);
                            l *= 2;
                        }
                        final int ligne = i;
                        final int colonne = j;
                        NullPointerException e = assertThrows(
                                NullPointerException.class,
                                () -> new Etat(ligne, colonne, null, avant, apres)
                        );
                        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));
                    }
                }
            }
        }
    }

    private void assertLignesColonnesIncorrectes(int l, int c, Epoque e, Piece avant, Piece apres) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Etat(l, c, e, avant, apres)
        );
        assertTrue(thrown.getMessage().contains("Coordonnées (" + l + ", " + c + ", " + e + ") incorrectes"));
    }

    @Test
    public void testLignesColonnesIncorrectes() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque epoque = Epoque.depuisIndice(i);
            int j = 0;

            while (j < Piece.NOMBRE) {
                Piece avant;
                if (j == 0) {
                    avant = null;
                    j++;
                } else {
                    avant = Piece.depuisValeur(j);
                    j *= 2;
                }
                int k = 0;

                while (k < Piece.NOMBRE) {
                    Piece apres;
                    if (k == 0) {
                        apres = null;
                        k++;
                    } else {
                        apres = Piece.depuisValeur(k);
                        k *= 2;
                    }

                    for (int l = -100; l < 0; l++) {
                        int c = 0;
                        assertLignesColonnesIncorrectes(l, c, epoque, avant, apres);
                    }
                    for (int c = -100; c < 0; c++) {
                        int l = 0;
                        assertLignesColonnesIncorrectes(l, c, epoque, avant, apres);
                    }
                    for (int l = Plateau.TAILLE; l < 100; l++) {
                        int c = 0;
                        assertLignesColonnesIncorrectes(l, c, epoque, avant, apres);
                    }
                    for (int c = Plateau.TAILLE; c < 100; c++) {
                        int l = 0;
                        assertLignesColonnesIncorrectes(l, c, epoque, avant, apres);
                    }
                }
            }
        }
    }
}
