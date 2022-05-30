package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMouvement {
    Plateau p;
    Joueur j1, j2;
    Coup c1, c2, c3;

    @Before
    public void initialisation() {
        p = new Plateau();
        j1 = new Joueur("a", TypeJoueur.HUMAIN, 0);
        j2 = new Joueur("b", TypeJoueur.HUMAIN, 3);
        j1.initialiserJoueur(Pion.BLANC);
        j2.initialiserJoueur(Pion.NOIR);
    }

    private void nouveauCoup1() {
        c1 = new Mouvement(p, j1, 0, 0, Epoque.PASSE);
    }

    private void nouveauCoup2() {
        c2 = new Mouvement(p, j2, 3, 3, Epoque.FUTUR);
    }

    private void nouveauCoup3() {
        c3 = new Mouvement(p, j1, 1, 2, Epoque.PRESENT);
    }

    private void assertMouvementsCorrects() {
        assertTrue(Mouvement.estCorrect(0, 1, 0));
        assertTrue(Mouvement.estCorrect(1, 0, 0));
        assertTrue(Mouvement.estCorrect(0, -1, 0));
        assertTrue(Mouvement.estCorrect(-1, 0, 0));
        assertTrue(Mouvement.estCorrect(0, 0, 1));
        assertTrue(Mouvement.estCorrect(0, 0, -1));
    }

    private void assertMouvementsIncorrects() {
        for (int i = -10; i < 10; i++) {
            if (i != 0) {
                assertFalse(Mouvement.estCorrect(0, 1, i));
                assertFalse(Mouvement.estCorrect(1, 0, i));
                assertFalse(Mouvement.estCorrect(0, -1, i));
                assertFalse(Mouvement.estCorrect(-1, 0, i));
            }
            if (i != -1 && i != 1) {
                assertFalse(Mouvement.estCorrect(0, 0, i));
            }
        }
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                if ((i != 0 || (j != 1 && j != -1)) && (j != 0 || (i != 1 && i != -1))) {
                    assertFalse(Mouvement.estCorrect(i, j, 0));
                }
                if (i != 0 || j != 0) {
                    assertFalse(Mouvement.estCorrect(i, j, 1));
                    assertFalse(Mouvement.estCorrect(i, j, -1));
                }
            }
        }
    }

    private void assertDeplacementsCorrects() {
        assertTrue(Mouvement.estDeplacement(0, 1, 0));
        assertTrue(Mouvement.estDeplacement(1, 0, 0));
        assertTrue(Mouvement.estDeplacement(0, -1, 0));
        assertTrue(Mouvement.estDeplacement(-1, 0, 0));
    }

    private void assertDeplacementsIncorrects() {
        for (int i = -10; i < 10; i++) {
            if (i != 0) {
                assertFalse(Mouvement.estDeplacement(0, 1, i));
                assertFalse(Mouvement.estDeplacement(1, 0, i));
                assertFalse(Mouvement.estDeplacement(0, -1, i));
                assertFalse(Mouvement.estDeplacement(-1, 0, i));
            }
        }
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                if ((i != 0 || (j != 1 && j != -1)) && (j != 0 || (i != 1 && i != -1))) {
                    assertFalse(Mouvement.estDeplacement(i, j, 0));
                }
            }
        }
    }

    private void assertVoyagesTemporelsCorrects() {
        assertTrue(Mouvement.estVoyageTemporel(0, 0, 1));
        assertTrue(Mouvement.estVoyageTemporel(0, 0, -1));
    }

    private void assertVoyagesTemporelsIncorrects() {
        for (int i = -10; i < 10; i++) {
            if (i != -1 && i != 1) {
                assertFalse(Mouvement.estVoyageTemporel(0, 0, i));
            }
        }
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                if (i != 0 || j != 0) {
                    assertFalse(Mouvement.estVoyageTemporel(i, j, 1));
                    assertFalse(Mouvement.estVoyageTemporel(i, j, -1));
                }
            }
        }
    }

    @Test
    public void testExceptionPlusieursAppelsACreer() {
        IllegalStateException e;

        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        e = assertThrows(IllegalStateException.class, () -> c1.creer(1, 0, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        e = assertThrows(IllegalStateException.class, () -> c2.creer(1, 3, Epoque.FUTUR));
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));
    }

    private void assertCreerCorrect() {
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));

        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));

        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup3();
        assertTrue(c3.creer(0, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(2, 2, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 1, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 3, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.PASSE));
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.FUTUR));
    }

    private void assertCreerIncorrect() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);

        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            if (i != e.indice()) {
                nouveauCoup3();
                assertFalse(c3.creer(0, 2, e));
                nouveauCoup3();
                assertFalse(c3.creer(2, 2, e));
                nouveauCoup3();
                assertFalse(c3.creer(1, 1, e));
                nouveauCoup3();
                assertFalse(c3.creer(1, 3, e));
            }
            if (e == Epoque.PRESENT) {
                nouveauCoup3();
                assertFalse(c3.creer(1, 2, e));
            }
        }
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                if (((i != 0 && i != 2) || j != 2) && (i != 1 || (j != 1 && j != 3))) {
                    nouveauCoup3();
                    assertFalse(c3.creer(i, j, Epoque.PRESENT));
                }
                if (i != 1 || j != 2) {
                    nouveauCoup3();
                    assertFalse(c3.creer(i, j, Epoque.PASSE));
                    nouveauCoup3();
                    assertFalse(c3.creer(i, j, Epoque.FUTUR));
                }
            }
        }
    }

    @Test
    public void testEstCorrect() {
        assertMouvementsCorrects();
        p = new Plateau();
        assertMouvementsIncorrects();
    }

    @Test
    public void testEstDeplacement() {
        assertDeplacementsCorrects();
        p = new Plateau();
        assertDeplacementsIncorrects();
    }

    @Test
    public void testEstVoyageTemporel() {
        assertVoyagesTemporelsCorrects();
        p = new Plateau();
        assertVoyagesTemporelsIncorrects();
    }

    @Test
    public void testCreerMouvementCorrect() {
        assertCreerCorrect();
        p = new Plateau();
        assertCreerIncorrect();
    }

    @Test
    public void testCreerMouvementPionIncorrect() {
        nouveauCoup1();
        assertFalse(c1.creer(2, 3, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(3, 2, Epoque.PASSE));
        p.supprimer(3, 3, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup1();
        assertFalse(c1.creer(3, 3, Epoque.PRESENT));

        nouveauCoup2();
        assertFalse(c2.creer(0, 1, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(1, 0, Epoque.FUTUR));
        p.supprimer(0, 0, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup2();
        assertFalse(c2.creer(0, 0, Epoque.PRESENT));
    }

    @Test
    public void testCreerDeplacementDansMur() {
        nouveauCoup1();
        assertFalse(c1.creer(0, -1, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(-1, 0, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(-1, -1, Epoque.PASSE));

        nouveauCoup2();
        assertFalse(c2.creer(3, 4, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(4, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(4, 4, Epoque.FUTUR));
    }

    @Test
    public void testCreerDeplacementSurCaseVide() {
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));

        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));
    }

    @Test
    public void testCreerDeplacementSurGraine() {
        p.ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertTrue(c1.creer(1, 0, Epoque.PASSE));

        p.ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.ajouter(3, 2, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertTrue(c2.creer(3, 2, Epoque.FUTUR));
    }

    @Test
    public void testCreerDeplacementSurPion() {
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(0, 2, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(0, 3, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 3, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 2, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 2, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 1, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));

        p.ajouter(2, 3, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.ajouter(1, 3, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.ajouter(0, 3, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(1, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(1, 3, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(2, 3, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(2, 3, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
    }

    private void assertCreerDeplacementSurObstacle(Piece obstacle) {
        p.ajouter(0, 1, Epoque.PASSE, obstacle);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 1, Epoque.PASSE, obstacle);
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, obstacle);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));

        p.ajouter(2, 3, Epoque.FUTUR, obstacle);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(2, 3, Epoque.FUTUR, obstacle);
        p.ajouter(2, 3, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(1, 3, Epoque.FUTUR, obstacle);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
    }

    @Test
    public void testCreerDeplacementSurArbuste() {
        assertCreerDeplacementSurObstacle(Piece.ARBUSTE);
    }

    @Test
    public void testCreerDeplacementSurArbreCoucheHaut() {
        assertCreerDeplacementSurObstacle(Piece.ARBRE_COUCHE_HAUT);
    }

    @Test
    public void testCreerDeplacementSurArbreCoucheDroite() {
        assertCreerDeplacementSurObstacle(Piece.ARBRE_COUCHE_DROITE);
    }

    @Test
    public void testCreerDeplacementSurArbreCoucheBas() {
        assertCreerDeplacementSurObstacle(Piece.ARBRE_COUCHE_BAS);
    }

    @Test
    public void testCreerDeplacementSurArbreCoucheGauche() {
        assertCreerDeplacementSurObstacle(Piece.ARBRE_COUCHE_GAUCHE);
    }

    @Test
    public void testCreerDeplacementSurArbre() {
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBRE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 3, Epoque.PASSE, Piece.NOIR);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 3, Epoque.PASSE, Piece.GRAINE);
        nouveauCoup1();
        assertTrue(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.GRAINE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBUSTE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.ARBUSTE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_DROITE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));
        p.supprimer(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_BAS);
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup1();
        assertFalse(c1.creer(0, 1, Epoque.PASSE));

        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.ajouter(1, 3, Epoque.FUTUR, Piece.ARBRE);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.ajouter(0, 3, Epoque.FUTUR, Piece.ARBRE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.ARBRE);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.BLANC);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.GRAINE);
        nouveauCoup2();
        assertTrue(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.GRAINE);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.ARBUSTE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.ARBUSTE);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_HAUT);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_HAUT);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_DROITE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_DROITE);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_BAS);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
        p.supprimer(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_BAS);
        p.ajouter(0, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_GAUCHE);
        nouveauCoup2();
        assertFalse(c2.creer(2, 3, Epoque.FUTUR));
    }

    private void assertVoyageTemporelCasIncorrects() {
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PASSE));
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.FUTUR));
        nouveauCoup2();
        assertFalse(c2.creer(0, 0, Epoque.PASSE));
        nouveauCoup2();
        assertFalse(c2.creer(0, 0, Epoque.FUTUR));
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PRESENT));
    }

    private void assertVoyageTemporelPossible() {
        p.supprimer(0, 0, Epoque.PRESENT, Piece.BLANC);
        nouveauCoup1();
        assertTrue(c1.creer(0, 0, Epoque.PRESENT));
        p.supprimer(3, 3, Epoque.PRESENT, Piece.NOIR);
        nouveauCoup2();
        assertTrue(c2.creer(3, 3, Epoque.PRESENT));
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.PASSE));
        nouveauCoup3();
        assertTrue(c3.creer(1, 2, Epoque.FUTUR));
    }

    private void assertVoyageTemporelImpossible() {
        nouveauCoup1();
        assertFalse(c1.creer(0, 0, Epoque.PRESENT));
        nouveauCoup2();
        assertFalse(c2.creer(3, 3, Epoque.PRESENT));
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.PASSE));
        nouveauCoup3();
        assertFalse(c3.creer(1, 2, Epoque.FUTUR));
    }

    private void ajouterPieceVoyageTemporel(Piece piece) {
        p.ajouter(1, 2, Epoque.PASSE, piece);
        p.ajouter(1, 2, Epoque.FUTUR, piece);
    }

    @Test
    public void testCreerVoyageTemporelSurCaseVide() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelPossible();
    }

    @Test
    public void testCreerVoyageTemporelSurGraine() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.GRAINE);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelPossible();
    }

    @Test
    public void testCreerVoyageTemporelSurBlanc() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.BLANC);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurNoir() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.NOIR);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurArbuste() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.ARBUSTE);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurArbre() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.ARBRE);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurArbreCoucheHaut() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.ARBRE_COUCHE_HAUT);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurArbreCoucheDroite() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.ARBRE_COUCHE_DROITE);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurArbreCoucheBas() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.ARBRE_COUCHE_BAS);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testCreerVoyageTemporelSurArbreCoucheGauche() {
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        ajouterPieceVoyageTemporel(Piece.ARBRE_COUCHE_GAUCHE);
        assertVoyageTemporelCasIncorrects();
        assertVoyageTemporelImpossible();
    }

    @Test
    public void testExceptionAucunCoupCree() {
        IllegalStateException e;
        nouveauCoup1();
        e = assertThrows(IllegalStateException.class, c1::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : aucun coup créé"));
        e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : aucun coup créé"));
    }

    @Test
    public void testExceptionAucunCoupJoue() {
        nouveauCoup1();
        c1.creer(0, 1, Epoque.PASSE);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));
    }

    @Test
    public void testJouerAnnulerDeplacementSurCaseVide() {
        nouveauCoup1();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.estVide(0, 1, Epoque.PASSE));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.estVide(0, 1, Epoque.PASSE));
    }

    @Test
    public void testJouerDeplacementSurGraine() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 1, Epoque.PASSE));
    }

    @Test
    public void testJouerDeplacementSurPionSimple() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionMultiples() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.BLANC);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionSimpleMort() {
        c2 = new Mouvement(p, j2, 0, 1, Epoque.PASSE);
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c2.creer(0, 0, Epoque.PASSE);
        c2.jouer();
        assertTrue(p.aNoir(0, 0, Epoque.PASSE));
        assertTrue(p.estVide(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(0, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(1, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c2.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionMultiplesMort() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 3, Epoque.PASSE, Piece.NOIR);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.aNoir(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(3, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.aNoir(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(3, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionSimpleParadoxe() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.BLANC);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.estVide(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(0, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(1, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionMultiplesParadoxe() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 3, Epoque.PASSE, Piece.BLANC);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 3, Epoque.PASSE));
        assertEquals(3, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 3, Epoque.PASSE));
        assertEquals(3, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionSimpleGraine() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.GRAINE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertFalse(p.aPion(0, 2, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertFalse(p.aPion(0, 2, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
    }

    @Test
    public void testJouerDeplacementSurPionObstacleArbuste() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBUSTE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbuste(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbuste(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbuste(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionObstacleArbre() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.ARBUSTE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aArbuste(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aArbuste(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aArbuste(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionObstacleArbreCoucheHaut() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeHaut(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeHaut(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeHaut(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionObstacleArbreCoucheDroite() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE_COUCHE_DROITE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionObstacleArbreCoucheBas() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE_COUCHE_BAS);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeBas(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeBas(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeBas(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurPionObstacleArbreCoucheGauche() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.NOIR);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE_COUCHE_GAUCHE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaGauche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaGauche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aNoir(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaGauche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
    }

    @Test
    public void testJouerDeplacementSurArbreSimpleDroite() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.estVide(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreSimpleBas() {
        nouveauCoup1();
        p.ajouter(1, 0, Epoque.PASSE, Piece.ARBRE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(1, 0, Epoque.PASSE));
        assertTrue(p.estVide(2, 0, Epoque.PASSE));
        assertTrue(p.estVide(3, 0, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.creer(1, 0, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 0, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLeBas(2, 0, Epoque.PASSE));
        assertTrue(p.aArbreCouche(2, 0, Epoque.PASSE));
        assertTrue(p.estVide(3, 0, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(1, 0, Epoque.PASSE));
        assertTrue(p.estVide(2, 0, Epoque.PASSE));
        assertTrue(p.estVide(3, 0, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreSimpleGauche() {
        nouveauCoup2();
        p.ajouter(3, 2, Epoque.FUTUR, Piece.ARBRE);
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertTrue(p.aArbre(3, 2, Epoque.FUTUR));
        assertTrue(p.estVide(3, 1, Epoque.FUTUR));
        assertTrue(p.estVide(3, 0, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c2.creer(3, 2, Epoque.FUTUR);
        c2.jouer();
        assertTrue(p.estVide(3, 3, Epoque.FUTUR));
        assertTrue(p.aNoir(3, 2, Epoque.FUTUR));
        assertTrue(p.aArbreCoucheVersLaGauche(3, 1, Epoque.FUTUR));
        assertTrue(p.aArbreCouche(3, 1, Epoque.FUTUR));
        assertTrue(p.estVide(3, 0, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c2.annuler();
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertTrue(p.aArbre(3, 2, Epoque.FUTUR));
        assertTrue(p.estVide(3, 1, Epoque.FUTUR));
        assertTrue(p.estVide(3, 0, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreSimpleHaut() {
        nouveauCoup2();
        p.ajouter(2, 3, Epoque.FUTUR, Piece.ARBRE);
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertTrue(p.aArbre(2, 3, Epoque.FUTUR));
        assertTrue(p.estVide(1, 3, Epoque.FUTUR));
        assertTrue(p.estVide(0, 3, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c2.creer(2, 3, Epoque.FUTUR);
        c2.jouer();
        assertTrue(p.estVide(3, 3, Epoque.FUTUR));
        assertTrue(p.aNoir(2, 3, Epoque.FUTUR));
        assertTrue(p.aArbreCoucheVersLeHaut(1, 3, Epoque.FUTUR));
        assertTrue(p.aArbreCouche(1, 3, Epoque.FUTUR));
        assertTrue(p.estVide(0, 3, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c2.annuler();
        assertTrue(p.aNoir(3, 3, Epoque.FUTUR));
        assertTrue(p.aArbre(2, 3, Epoque.FUTUR));
        assertTrue(p.estVide(1, 3, Epoque.FUTUR));
        assertTrue(p.estVide(0, 3, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreMultiples() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 3, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreSimpleGraine() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.GRAINE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreMultipleGraine() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.GRAINE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aGraine(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 3, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aGraine(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreSimplePion() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.BLANC);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreMultiplePion() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.NOIR);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aNoir(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 3, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aNoir(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreSimpleGrainePion() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.GRAINE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.NOIR);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aGraine(0, 2, Epoque.PASSE));
        assertTrue(p.aNoir(0, 2, Epoque.PASSE));
        assertTrue(p.estVide(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerDeplacementSurArbreMultipleGrainePion() {
        nouveauCoup1();
        p.ajouter(0, 1, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 2, Epoque.PASSE, Piece.ARBRE);
        p.ajouter(0, 3, Epoque.PASSE, Piece.BLANC);
        p.ajouter(0, 3, Epoque.PASSE, Piece.GRAINE);
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 3, Epoque.PASSE));
        assertTrue(p.aGraine(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        assertTrue(p.estVide(0, 0, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 1, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 2, Epoque.PASSE));
        assertTrue(p.aArbreCoucheVersLaDroite(0, 3, Epoque.PASSE));
        assertTrue(p.aArbreCouche(0, 3, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(5, p.nombreGrainesReserve());
        c1.annuler();
        assertTrue(p.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(p.aArbre(0, 1, Epoque.PASSE));
        assertTrue(p.aArbre(0, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(0, 3, Epoque.PASSE));
        assertTrue(p.aGraine(0, 3, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        assertEquals(4, p.nombreGrainesReserve());
    }

    @Test
    public void testJouerVoyageTemporelAvantSurCaseVide() {
        nouveauCoup3();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
        c3.creer(1, 2, Epoque.FUTUR);
        c3.jouer();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.estVide(1, 2, Epoque.PRESENT));
        assertTrue(p.aBlanc(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
        c3.annuler();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
    }

    @Test
    public void testJouerVoyageTemporelAvantSurGraine() {
        nouveauCoup3();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(1, 2, Epoque.FUTUR, Piece.GRAINE);
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.aGraine(1, 2, Epoque.FUTUR));
        assertFalse(p.aBlanc(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
        c3.creer(1, 2, Epoque.FUTUR);
        c3.jouer();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.estVide(1, 2, Epoque.PRESENT));
        assertTrue(p.aGraine(1, 2, Epoque.FUTUR));
        assertTrue(p.aBlanc(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
        c3.annuler();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.aGraine(1, 2, Epoque.FUTUR));
        assertFalse(p.aBlanc(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
    }

    @Test
    public void testJouerVoyageTemporelArriereSurCaseVide() {
        nouveauCoup3();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
        c3.creer(1, 2, Epoque.PASSE);
        c3.jouer();
        assertTrue(p.aBlanc(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(3, j1.nombrePionsReserve());
        c3.annuler();
        assertTrue(p.estVide(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
    }

    @Test
    public void testJouerVoyageTemporelArriereSurGraine() {
        nouveauCoup3();
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(1, 2, Epoque.PASSE, Piece.GRAINE);
        assertTrue(p.aGraine(1, 2, Epoque.PASSE));
        assertFalse(p.aBlanc(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
        c3.creer(1, 2, Epoque.PASSE);
        c3.jouer();
        assertTrue(p.aGraine(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(3, j1.nombrePionsReserve());
        c3.annuler();
        assertTrue(p.aGraine(1, 2, Epoque.PASSE));
        assertFalse(p.aBlanc(1, 2, Epoque.PASSE));
        assertTrue(p.aBlanc(1, 2, Epoque.PRESENT));
        assertTrue(p.estVide(1, 2, Epoque.FUTUR));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PRESENT));
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.FUTUR));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(4, j1.nombrePionsReserve());
    }

    @Test
    public void testReservePionVide() {
        p.ajouter(1, 1, Epoque.FUTUR, Piece.BLANC);
        Coup c = new Mouvement(p, j1, 1, 1, Epoque.FUTUR);
        assertEquals(4, j1.nombrePionsReserve());
        assertTrue(c.creer(1, 1, Epoque.PRESENT));
        c.jouer();
        assertEquals(3, j1.nombrePionsReserve());

        c = new Mouvement(p, j1, 1, 1, Epoque.PRESENT);
        assertEquals(3, j1.nombrePionsReserve());
        assertTrue(c.creer(1, 1, Epoque.PASSE));
        c.jouer();
        assertEquals(2, j1.nombrePionsReserve());

        p.ajouter(1, 2, Epoque.FUTUR, Piece.BLANC);
        c = new Mouvement(p, j1, 1, 2, Epoque.FUTUR);
        assertEquals(2, j1.nombrePionsReserve());
        assertTrue(c.creer(1, 2, Epoque.PRESENT));
        c.jouer();
        assertEquals(1, j1.nombrePionsReserve());

        c = new Mouvement(p, j1, 1, 2, Epoque.PRESENT);
        assertEquals(1, j1.nombrePionsReserve());
        assertTrue(c.creer(1, 2, Epoque.PASSE));
        c.jouer();
        assertEquals(0, j1.nombrePionsReserve());

        p.ajouter(1, 3, Epoque.FUTUR, Piece.BLANC);
        c = new Mouvement(p, j1, 1, 3, Epoque.FUTUR);
        assertEquals(0, j1.nombrePionsReserve());
        assertFalse(c.creer(1, 3, Epoque.PRESENT));
    }
}
