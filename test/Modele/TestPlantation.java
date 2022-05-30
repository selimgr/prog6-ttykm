package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlantation {
    Plateau p;
    Joueur j1, j2;
    Coup c1, c2, c3;

    @Before
    public void initialisation() {
        p = new Plateau();
        j1 = new Joueur("a", TypeJoueur.HUMAIN, 0);
        j2 = new Joueur("b", TypeJoueur.HUMAIN, 0);
        j1.initialiserJoueur(Pion.BLANC);
        j2.initialiserJoueur(Pion.NOIR);
    }

    private void nouveauCoup1() {
        c1 = new Plantation(p, j1, 0, 0, Epoque.PASSE);
    }

    private void nouveauCoup2() {
        c2 = new Plantation(p, j2, 3, 3, Epoque.FUTUR);
    }

    private void nouveauCoup3() {
        c3 = new Plantation(p, j1, 1, 2, Epoque.PRESENT);
    }

    @Test
    public void testCorrecte() {
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                for (int k = -10; k < 10; k++) {
                    if (i == 0) {
                        if (j == 0 && k == 0) {
                            assertTrue(Plantation.estCorrecte(j, k, i));
                        } else if ((j == -1 || j == 1) && k == 0) {
                            assertTrue(Plantation.estCorrecte(j, k, i));
                        } else if (j == 0 && (k == -1 || k == 1)) {
                            assertTrue(Plantation.estCorrecte(j, k, i));
                        } else {
                            assertFalse(Plantation.estCorrecte(j, k, i));
                        }
                    } else {
                        assertFalse(Plantation.estCorrecte(j, k, i));
                    }
                }
            }
        }
    }

    @Test
    public void testCreerIncorrects() {
        nouveauCoup1();
        assertFalse(c1.creer(1, 1, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(0, 2, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(2, 0, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PRESENT));
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.FUTUR));

        nouveauCoup2();
        assertFalse(c2.creer(2, 2, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(1, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(3, 1, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.PRESENT));
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.PASSE));

        nouveauCoup3();
        assertFalse(c3.creer(0, 1, Epoque.PRESENT));
        nouveauCoup3();
        assertFalse(c3.creer(0, 3, Epoque.PRESENT));
        nouveauCoup3();
        assertFalse(c3.creer(2, 1, Epoque.PRESENT));
        nouveauCoup3();
        assertFalse(c3.creer(2, 3, Epoque.PRESENT));
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PASSE));
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.FUTUR));
    }

    @Test
    public void testCreerSurCaseVide() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 0, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(3, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));

        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 1, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(0, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(2, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurGraine() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p = new Plateau();
        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p = new Plateau();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurBlanc() {
        nouveauCoup1();
        assertTrue(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup3();
        assertTrue(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup3();
        assertTrue(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup3();
        assertTrue(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup3();
        assertTrue(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurNoir() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));

        nouveauCoup2();
        assertTrue(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup3();
        assertTrue(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup3();
        assertTrue(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup3();
        assertTrue(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup3();
        assertTrue(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurBlancEtGraine() {
        p.ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p = new Plateau();
        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(3, 2, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p = new Plateau();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(1, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(0, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(2, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(1, 3, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurNoirEtGraine() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.NOIR);
        p.ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p = new Plateau();
        p.ajouter(3, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 2, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p = new Plateau();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.NOIR);
        p.ajouter(1, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.NOIR);
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.NOIR);
        p.ajouter(2, 2, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.NOIR);
        p.ajouter(1, 3, Epoque.PRESENT, Piece.GRAINE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurArbuste() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.ARBUSTE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBUSTE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBUSTE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBUSTE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBUSTE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBUSTE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBUSTE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.ARBUSTE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBUSTE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBUSTE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurArbre() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.ARBRE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBRE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBRE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBRE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBRE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.ARBRE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBRE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBRE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurArbreCoucheHaut() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurArbreCoucheDroite() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurArbreCoucheBas() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testCreerSurArbreCoucheGauche() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup1();
        assertFalse(c1.creer(1, 0, Epoque.PASSE));

        p.supprimer(3, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup2();
        assertFalse(c2.creer(3, 2, Epoque.FUTUR));
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 1, Epoque.PRESENT));
        p.ajouter(0, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup3();
        assertFalse(c3.creer(0, 2, Epoque.PRESENT));
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup3();
        assertFalse(c3.creer(2, 2, Epoque.PRESENT));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup3();
        assertFalse(c3.creer(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testJouerAnnuler() {
        // Coup 1
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(p.aGraine(0, 0, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(0, 0, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aGraine(0, 0, Epoque.PASSE));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(p.aGraine(0, 0, Epoque.PASSE));

        assertTrue(p.estVide(0, 1, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
        c1.annuler();
        assertTrue(p.estVide(0, 1, Epoque.PASSE));

        assertTrue(p.estVide(1, 0, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aGraine(1, 0, Epoque.PASSE));
        c1.annuler();
        assertTrue(p.estVide(1, 0, Epoque.PASSE));

        // Coup 2
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertFalse(p.aGraine(3, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 3, Epoque.FUTUR));
        c2.jouer();
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertTrue(p.aGraine(3, 3, Epoque.FUTUR));
        c2.annuler();
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertFalse(p.aGraine(3, 3, Epoque.FUTUR));

        assertTrue(p.estVide(3, 2, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));
        c2.jouer();
        assertTrue(p.aGraine(3, 2, Epoque.FUTUR));
        c2.annuler();
        assertTrue(p.estVide(3, 2, Epoque.FUTUR));

        assertTrue(p.estVide(2, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        c2.jouer();
        assertTrue(p.aGraine(2, 3, Epoque.FUTUR));
        c2.annuler();
        assertTrue(p.estVide(2, 3, Epoque.FUTUR));

        // Coup 3
        assertTrue(p.estVide(1, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.PRESENT));
        c3.jouer();
        assertTrue(p.aGraine(1, 2, Epoque.PRESENT));
        c3.annuler();
        assertTrue(p.estVide(1, 2, Epoque.PRESENT));

        assertTrue(p.estVide(0, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(0, 2, Epoque.PRESENT));
        c3.jouer();
        assertTrue(p.aGraine(0, 2, Epoque.PRESENT));
        c3.annuler();
        assertTrue(p.estVide(0, 2, Epoque.PRESENT));

        assertTrue(p.estVide(1, 1, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 1, Epoque.PRESENT));
        c3.jouer();
        assertTrue(p.aGraine(1, 1, Epoque.PRESENT));
        c3.annuler();
        assertTrue(p.estVide(1, 1, Epoque.PRESENT));

        assertTrue(p.estVide(2, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(2, 2, Epoque.PRESENT));
        c3.jouer();
        assertTrue(p.aGraine(2, 2, Epoque.PRESENT));
        c3.annuler();
        assertTrue(p.estVide(2, 2, Epoque.PRESENT));

        assertTrue(p.estVide(1, 3, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 3, Epoque.PRESENT));
        c3.jouer();
        assertTrue(p.aGraine(1, 3, Epoque.PRESENT));
        c3.annuler();
        assertTrue(p.estVide(1, 3, Epoque.PRESENT));
    }

    @Test
    public void testReserveGraineVide() {
        // Coup 1
        assertEquals(5, p.nombreGrainesReserve());
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(p.aGraine(0, 0, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(0, 0, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aGraine(0, 0, Epoque.PASSE));

        assertEquals(4, p.nombreGrainesReserve());
        assertTrue(p.estVide(0, 1, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aGraine(0, 1, Epoque.PASSE));

        assertEquals(3, p.nombreGrainesReserve());
        assertTrue(p.estVide(1, 0, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aGraine(1, 0, Epoque.PASSE));

        // Coup 2
        assertEquals(2, p.nombreGrainesReserve());
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertFalse(p.aGraine(3, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 3, Epoque.FUTUR));
        c2.jouer();
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertTrue(p.aGraine(3, 3, Epoque.FUTUR));

        assertEquals(1, p.nombreGrainesReserve());
        assertTrue(p.estVide(3, 2, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));
        c2.jouer();
        assertTrue(p.aGraine(3, 2, Epoque.FUTUR));

        assertEquals(0, p.nombreGrainesReserve());
        assertTrue(p.estVide(2, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
    }

    @Test
    public void testCroissanceGraineSurCaseVide() {
        nouveauCoup1();
        assertTrue(p.estVide(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 1, Epoque.PRESENT));
        assertTrue(p.estVide(0, 1, Epoque.FUTUR));
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        c1.jouer();
        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
        assertFalse(p.aArbuste(0, 1, Epoque.PASSE));
        assertFalse(p.aArbre(0, 1, Epoque.PASSE));
        assertFalse(p.aGraine(0, 1, Epoque.PRESENT));
        assertTrue(p.aArbuste(0, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(0, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(0, 1, Epoque.FUTUR));
        assertFalse(p.aArbuste(0, 1, Epoque.FUTUR));
        assertTrue(p.aArbre(0, 1, Epoque.FUTUR));

        nouveauCoup3();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.estVide(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertTrue(c3.creer(1, 2, Epoque.PRESENT));
        c3.jouer();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aGraine(1, 2, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 2, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 2, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 2, Epoque.FUTUR));
        assertTrue(p.aArbuste(1, 2, Epoque.FUTUR));
        assertFalse(p.aArbre(1, 2, Epoque.FUTUR));
    }

    private void croissanceGraineSurObstacle(Piece piece) {
        p.ajouter(0, 1, Epoque.PRESENT, piece);
        nouveauCoup1();

        assertTrue(p.estVide(0, 1, Epoque.PASSE));

        assertTrue(p.aPiece(0, 1, Epoque.PRESENT, piece));
        assertTrue(piece == Piece.GRAINE || !p.aGraine(0, 1, Epoque.PRESENT));
        assertTrue(piece == Piece.ARBUSTE || !p.aArbuste(0, 1, Epoque.PRESENT));
        assertTrue(piece == Piece.ARBRE || !p.aArbre(0, 1, Epoque.PRESENT));

        assertTrue(p.estVide(0, 1, Epoque.FUTUR));

        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        c1.jouer();

        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
        assertFalse(p.aArbuste(0, 1, Epoque.PASSE));
        assertFalse(p.aArbre(0, 1, Epoque.PASSE));

        assertTrue(p.aPiece(0, 1, Epoque.PRESENT, piece));
        assertTrue(piece == Piece.GRAINE || !p.aGraine(0, 1, Epoque.PRESENT));
        assertTrue(piece == Piece.ARBUSTE || !p.aArbuste(0, 1, Epoque.PRESENT));
        assertTrue(piece == Piece.ARBRE || !p.aArbre(0, 1, Epoque.PRESENT));

        assertTrue(p.estVide(0, 1, Epoque.FUTUR));

        p = new Plateau();
        p.ajouter(0, 1, Epoque.FUTUR, piece);
        nouveauCoup1();

        assertTrue(p.estVide(0, 1, Epoque.PASSE));

        assertTrue(p.estVide(0, 1, Epoque.PRESENT));

        assertTrue(p.aPiece(0, 1, Epoque.FUTUR, piece));
        assertTrue(piece == Piece.GRAINE || !p.aGraine(0, 1, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBUSTE || !p.aArbuste(0, 1, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBRE || !p.aArbre(0, 1, Epoque.FUTUR));

        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        c1.jouer();

        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
        assertFalse(p.aArbuste(0, 1, Epoque.PASSE));
        assertFalse(p.aArbre(0, 1, Epoque.PASSE));

        assertFalse(p.aGraine(0, 1, Epoque.PRESENT));
        assertTrue(p.aArbuste(0, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(0, 1, Epoque.PRESENT));

        assertTrue(p.aPiece(0, 1, Epoque.FUTUR, piece));
        assertTrue(piece == Piece.GRAINE || !p.aGraine(0, 1, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBUSTE || !p.aArbuste(0, 1, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBRE || !p.aArbre(0, 1, Epoque.FUTUR));

        p = new Plateau();
        p.ajouter(1, 2, Epoque.FUTUR, piece);
        nouveauCoup3();

        assertTrue(p.estVide(1, 2, Epoque.PASSE));

        assertTrue(p.estVide(1, 2, Epoque.PRESENT));

        assertTrue(p.aPiece(1, 2, Epoque.FUTUR, piece));
        assertTrue(piece == Piece.GRAINE || !p.aGraine(1, 2, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBUSTE || !p.aArbuste(1, 2, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBRE || !p.aArbre(1, 2, Epoque.FUTUR));

        assertTrue(c3.creer(1, 2, Epoque.PRESENT));
        c3.jouer();

        assertTrue(p.estVide(1, 2, Epoque.PASSE));

        assertTrue(p.aGraine(1, 2, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 2, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 2, Epoque.PRESENT));

        assertTrue(p.aPiece(1, 2, Epoque.FUTUR, piece));
        assertTrue(piece == Piece.GRAINE || !p.aGraine(1, 2, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBUSTE || !p.aArbuste(1, 2, Epoque.FUTUR));
        assertTrue(piece == Piece.ARBRE || !p.aArbre(1, 2, Epoque.FUTUR));
    }

    @Test
    public void testCroissanceGraineSurBlanc() {
        croissanceGraineSurObstacle(Piece.BLANC);
    }

    @Test
    public void testCroissanceGraineSurNoir() {
        croissanceGraineSurObstacle(Piece.NOIR);
    }

    @Test
    public void testCroissanceGraineSurGraine() {
        croissanceGraineSurObstacle(Piece.GRAINE);
    }

    @Test
    public void testCroissanceGraineSurArbuste() {
        croissanceGraineSurObstacle(Piece.ARBUSTE);
    }

    @Test
    public void testCroissanceGraineSurArbre() {
        croissanceGraineSurObstacle(Piece.ARBRE);
    }

    @Test
    public void testCroissanceGraineSurArbreCoucheHaut() {
        croissanceGraineSurObstacle(Piece.ARBRE_COUCHE_HAUT);
    }

    @Test
    public void testCroissanceGraineSurArbreCoucheDroite() {
        croissanceGraineSurObstacle(Piece.ARBRE_COUCHE_DROITE);
    }

    @Test
    public void testCroissanceGraineSurArbreCoucheBas() {
        croissanceGraineSurObstacle(Piece.ARBRE_COUCHE_BAS);
    }

    @Test
    public void testCroissanceGraineSurArbreCoucheGauche() {
        croissanceGraineSurObstacle(Piece.ARBRE_COUCHE_GAUCHE);
    }
}
