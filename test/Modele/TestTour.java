package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTour {
    Tour tour;
    Coup coup;
    Plateau plateau;
    Joueur joueur;

    @Before
    public void initialisation() {
        tour = new Tour();
        plateau = new Plateau();
        joueur = new Joueur("a", TypeJoueur.HUMAIN, Pion.BLANC, 0);
        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
    }

    @Test
    public void testExceptionLignePion() {
        IllegalStateException e = assertThrows(IllegalStateException.class, tour::lignePion);
        assertTrue(e.getMessage().contains("Impossible de renvoyer la ligne du pion : pion non sélectionné"));
    }

    @Test
    public void testExceptionColonnePion() {
        IllegalStateException e = assertThrows(IllegalStateException.class, tour::colonnePion);
        assertTrue(e.getMessage().contains("Impossible de renvoyer la colonne du pion : pion non sélectionné"));
    }

    @Test
    public void testExceptionEpoquePion() {
        IllegalStateException e = assertThrows(IllegalStateException.class, tour::epoquePion);
        assertTrue(e.getMessage().contains("Impossible de renvoyer l'époque du pion : pion non sélectionné"));
    }

    @Test
    public void testInitialisation() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    tour.selectionnerPion(j, k, e);
                    assertEquals(j, tour.lignePion());
                    assertEquals(k, tour.colonnePion());
                    assertEquals(e, tour.epoquePion());
                    assertEquals(i, tour.epoquePion().indice());
                    tour.deselectionnerPion(j, k, e);
                }
            }
        }
    }

    @Test
    public void testPionSelectionne() {
        assertFalse(tour.pionSelectionne());
        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertTrue(tour.pionSelectionne());
        tour.deselectionnerPion(0, 0, Epoque.PASSE);
        assertFalse(tour.pionSelectionne());
        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertTrue(tour.pionSelectionne());
        tour.jouerCoup(coup, 0, 1, Epoque.PASSE);
        assertTrue(tour.pionSelectionne());
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 2, Epoque.PASSE);
        assertTrue(tour.pionSelectionne());
        tour.annulerCoup();
        assertTrue(tour.pionSelectionne());
        tour.annulerCoup();
        assertTrue(tour.pionSelectionne());
        tour.annulerCoup();
        assertFalse(tour.pionSelectionne());
    }

    @Test
    public void testNombreCoupJoues() {
        assertEquals(2, tour.nombreCoupsRestants());
        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertEquals(2, tour.nombreCoupsRestants());
        tour.jouerCoup(coup, 0, 1, Epoque.PASSE);
        assertEquals(1, tour.nombreCoupsRestants());
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 2, Epoque.PASSE);
        assertEquals(0, tour.nombreCoupsRestants());
        tour.annulerCoup();
        assertEquals(1, tour.nombreCoupsRestants());
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 1, 1, Epoque.PASSE);
        assertEquals(0, tour.nombreCoupsRestants());
    }

    @Test
    public void testSelectionnerPion() {
        tour.selectionnerPion(1, 3, Epoque.FUTUR);
        assertEquals(1, tour.lignePion());
        assertEquals(3, tour.colonnePion());
        assertEquals(Epoque.FUTUR, tour.epoquePion());

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> tour.selectionnerPion(0, 1, Epoque.PRESENT));
        assertTrue(e.getMessage().contains("Impossible de sélectionner le pion : pion déjà sélectionné"));

        tour.deselectionnerPion(1, 3, Epoque.FUTUR);
        tour.selectionnerPion(2, 0, Epoque.PRESENT);
        assertEquals(2, tour.lignePion());
        assertEquals(0, tour.colonnePion());
        assertEquals(Epoque.PRESENT, tour.epoquePion());

        e = assertThrows(IllegalStateException.class, () -> tour.selectionnerPion(2, 0, Epoque.PRESENT));
        assertTrue(e.getMessage().contains("Impossible de sélectionner le pion : pion déjà sélectionné"));
    }

    @Test
    public void testDeselectionnerPion() {
        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertTrue(tour.deselectionnerPion(0, 0, Epoque.PASSE));
        tour.selectionnerPion(1, 1, Epoque.PRESENT);
        assertFalse(tour.deselectionnerPion(0, 0, Epoque.PASSE));
        assertTrue(tour.deselectionnerPion(1, 1, Epoque.PRESENT));
        tour.selectionnerPion(3, 3, Epoque.FUTUR);
        joueur = new Joueur("b", TypeJoueur.HUMAIN, Pion.NOIR, 0);
        coup = new Mouvement(plateau, joueur, 3, 3, Epoque.FUTUR);
        tour.jouerCoup(coup, 2, 3, Epoque.FUTUR);
        assertFalse(tour.deselectionnerPion(2, 3, Epoque.FUTUR));
        assertFalse(tour.deselectionnerPion(3, 3, Epoque.FUTUR));
        tour.annulerCoup();
        assertTrue(tour.deselectionnerPion(3, 3, Epoque.FUTUR));
        assertFalse(tour.deselectionnerPion(3, 3, Epoque.FUTUR));
    }

    @Test
    public void testExceptionsJouerCoup() {
        IllegalStateException e;

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 0, 1, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : aucun pion sélectionné"));

        tour.selectionnerPion(0, 0, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 1, Epoque.PASSE);
        tour.annulerCoup();
        tour.deselectionnerPion(0, 0, Epoque.PASSE);

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 1, 0, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : aucun pion sélectionné"));

        tour.selectionnerPion(0, 0, Epoque.PASSE);
        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 1, Epoque.PASSE);
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 2, Epoque.PASSE);

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 0, 3, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : tous les coups ont déjà été joués ce tour"));

        tour.annulerCoup();
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 2, Epoque.PASSE);

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 1, 2, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : tous les coups ont déjà été joués ce tour"));
    }

    @Test
    public void testJouerAnnulerCoupsCorrects() {
        assertFalse(tour.annulerCoup());

        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 1, Epoque.PASSE));

        assertTrue(tour.annulerCoup());

        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 1, Epoque.PASSE));

        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 2, Epoque.PASSE));

        assertTrue(tour.annulerCoup());
        assertTrue(tour.annulerCoup());

        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 1, Epoque.PASSE));

        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 2, Epoque.PASSE));

        assertTrue(tour.annulerCoup());

        coup = new Mouvement(plateau, joueur, 0, 2, Epoque.PASSE);
        assertFalse(tour.jouerCoup(coup, 0, 3, Epoque.PASSE));

        assertTrue(tour.annulerCoup());
        assertTrue(tour.annulerCoup());

        tour.selectionnerPion(0, 0, Epoque.PASSE);

        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 1, 0, Epoque.PASSE));

        coup = new Mouvement(plateau, joueur, 1, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 2, 0, Epoque.PASSE));

        assertTrue(tour.annulerCoup());

        plateau.ajouter(1, 1, Epoque.PASSE, Piece.NOIR);
        coup = new Mouvement(plateau, joueur, 1, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 1, 1, Epoque.PASSE));
        assertEquals(1, tour.lignePion());
        assertEquals(1, tour.colonnePion());
        assertEquals(Epoque.PASSE, tour.epoquePion());
        assertTrue(plateau.estVide(1, 0, Epoque.PASSE));
        assertTrue(plateau.aBlanc(1, 1, Epoque.PASSE));
        assertTrue(plateau.aNoir(1, 2, Epoque.PASSE));
        assertTrue(plateau.estVide(1, 3, Epoque.PASSE));

        assertTrue(tour.annulerCoup());

        coup = new Mouvement(plateau, joueur, 1, 0, Epoque.PASSE);
        assertFalse(tour.jouerCoup(coup, 1, 3, Epoque.PASSE));
        assertEquals(1, tour.lignePion());
        assertEquals(0, tour.colonnePion());
        assertEquals(Epoque.PASSE, tour.epoquePion());
        assertTrue(plateau.aBlanc(1, 0, Epoque.PASSE));
        assertTrue(plateau.aNoir(1, 1, Epoque.PASSE));
        assertTrue(plateau.estVide(1, 2, Epoque.PASSE));
        assertTrue(plateau.estVide(1, 3, Epoque.PASSE));

        assertTrue(tour.annulerCoup());

        assertFalse(plateau.aGraine(0, 0, Epoque.PASSE));
        coup = new Plantation(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 0, Epoque.PASSE));
        assertEquals(0, tour.lignePion());
        assertEquals(0, tour.colonnePion());
        assertEquals(Epoque.PASSE, tour.epoquePion());
        assertTrue(plateau.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(plateau.aGraine(0, 0, Epoque.PASSE));

        coup = new Plantation(plateau, joueur, 0, 0, Epoque.PASSE);
        assertFalse(tour.jouerCoup(coup, 0, 0, Epoque.PASSE));
        assertEquals(0, tour.lignePion());
        assertEquals(0, tour.colonnePion());
        assertEquals(Epoque.PASSE, tour.epoquePion());
        assertTrue(plateau.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(plateau.aGraine(0, 0, Epoque.PASSE));

        coup = new Recolte(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 0, Epoque.PASSE));
        assertEquals(0, tour.lignePion());
        assertEquals(0, tour.colonnePion());
        assertEquals(Epoque.PASSE, tour.epoquePion());
        assertTrue(plateau.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(plateau.aGraine(0, 0, Epoque.PASSE));

        assertTrue(tour.annulerCoup());
        assertTrue(tour.annulerCoup());
        assertTrue(tour.annulerCoup());
        assertFalse(tour.annulerCoup());
    }
}
