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
        tour = new Tour(Epoque.PASSE);
        plateau = new Plateau();
        joueur = new Joueur("a", TypeJoueur.HUMAIN, 0);
        joueur.initialiserJoueur(Pion.BLANC);
        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
    }

    @Test
    public void testPionNull() {
        assertNull(tour.pion());
    }

    @Test
    public void testInitialisation() {
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);
            tour = new Tour(e);
            assertEquals(e, tour.focus());

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    tour.selectionnerPion(j, k, e);
                    assertEquals(j, tour.pion().ligne());
                    assertEquals(k, tour.pion().colonne());
                    assertEquals(e, tour.pion().epoque());
                    assertEquals(i, tour.pion().indiceEpoque());
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
        tour.annuler();
        assertTrue(tour.pionSelectionne());
        tour.annuler();
        assertTrue(tour.pionSelectionne());
        tour.annuler();
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
        tour.annuler();
        assertEquals(1, tour.nombreCoupsRestants());
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 1, 1, Epoque.PASSE);
        assertEquals(0, tour.nombreCoupsRestants());
    }

    @Test
    public void testSelectionnerPion() {
        tour.selectionnerPion(1, 3, Epoque.PASSE);
        assertEquals(1, tour.pion().ligne());
        assertEquals(3, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());
        assertTrue(tour.pionSelectionne());

        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, () -> tour.selectionnerPion(0, 1, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de sélectionner le pion : état du tour incorrect"));

        tour.deselectionnerPion(1, 3, Epoque.PASSE);
        tour.selectionnerPion(2, 0, Epoque.PASSE);
        assertEquals(2, tour.pion().ligne());
        assertEquals(0, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());

        e = assertThrows(IllegalStateException.class, () -> tour.selectionnerPion(2, 0, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de sélectionner le pion : état du tour incorrect"));

        tour.deselectionnerPion(2, 0, Epoque.PASSE);
        assertFalse(tour.selectionnerPion(0, 0, Epoque.PRESENT));
        assertFalse(tour.selectionnerPion(0, 0, Epoque.FUTUR));
    }

    @Test
    public void testDeselectionnerPion() {
        assertFalse(tour.deselectionnerPion(0, 0, Epoque.PASSE));
        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertFalse(tour.deselectionnerPion(1, 0, Epoque.PASSE));
        assertFalse(tour.deselectionnerPion(0, 1, Epoque.PASSE));
        assertFalse(tour.deselectionnerPion(0, 0, Epoque.PRESENT));
        assertFalse(tour.deselectionnerPion(0, 0, Epoque.FUTUR));
        assertTrue(tour.deselectionnerPion(0, 0, Epoque.PASSE));

        tour = new Tour(Epoque.FUTUR);
        tour.selectionnerPion(3, 3, Epoque.FUTUR);
        joueur = new Joueur("b", TypeJoueur.HUMAIN, 0);
        joueur.initialiserJoueur(Pion.NOIR);
        coup = new Mouvement(plateau, joueur, 3, 3, Epoque.FUTUR);
        tour.jouerCoup(coup, 2, 3, Epoque.FUTUR);
        assertFalse(tour.deselectionnerPion(3, 3, Epoque.FUTUR));
        tour.annuler();
        assertTrue(tour.deselectionnerPion(3, 3, Epoque.FUTUR));
        assertFalse(tour.deselectionnerPion(3, 3, Epoque.FUTUR));
    }

    @Test
    public void testExceptionsJouerCoup() {
        IllegalStateException e;

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 0, 1, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : pion non sélectionné"));

        tour.selectionnerPion(0, 0, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 1, Epoque.PASSE);
        tour.annuler();
        tour.deselectionnerPion(0, 0, Epoque.PASSE);

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 1, 0, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : pion non sélectionné"));

        tour.selectionnerPion(0, 0, Epoque.PASSE);
        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 1, Epoque.PASSE);
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 2, Epoque.PASSE);

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 0, 3, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : tous les coups ont déjà été joués ce tour"));

        tour.annuler();
        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        tour.jouerCoup(coup, 0, 2, Epoque.PASSE);

        e = assertThrows(IllegalStateException.class, () -> tour.jouerCoup(coup, 1, 2, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer un nouveau coup : tous les coups ont déjà été joués ce tour"));
    }

    @Test
    public void testJouerAnnulerCoupsRefaireCorrects() {
        assertFalse(tour.annuler());
        assertFalse(tour.refaire());
        assertFalse(tour.peutRefaire());

        tour.selectionnerPion(0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 1, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.annuler());

        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 1, Epoque.PASSE));

        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 2, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.annuler());
        assertTrue(tour.annuler());


        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 1, Epoque.PASSE));

        coup = new Mouvement(plateau, joueur, 0, 1, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 2, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.annuler());


        coup = new Mouvement(plateau, joueur, 0, 2, Epoque.PASSE);
        assertFalse(tour.jouerCoup(coup, 0, 3, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.annuler());
        assertTrue(tour.annuler());

        tour.selectionnerPion(0, 0, Epoque.PASSE);

        coup = new Mouvement(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 1, 0, Epoque.PASSE));

        coup = new Mouvement(plateau, joueur, 1, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 2, 0, Epoque.PASSE));

        assertTrue(tour.annuler());

        plateau.ajouter(1, 1, Epoque.PASSE, Piece.NOIR);
        coup = new Mouvement(plateau, joueur, 1, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 1, 1, Epoque.PASSE));
        assertEquals(1, tour.pion().ligne());
        assertEquals(1, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());
        assertTrue(plateau.estVide(1, 0, Epoque.PASSE));
        assertTrue(plateau.aBlanc(1, 1, Epoque.PASSE));
        assertTrue(plateau.aNoir(1, 2, Epoque.PASSE));
        assertTrue(plateau.estVide(1, 3, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.annuler());

        coup = new Mouvement(plateau, joueur, 1, 0, Epoque.PASSE);
        assertFalse(tour.jouerCoup(coup, 1, 3, Epoque.PASSE));
        assertEquals(1, tour.pion().ligne());
        assertEquals(0, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());
        assertTrue(plateau.aBlanc(1, 0, Epoque.PASSE));
        assertTrue(plateau.aNoir(1, 1, Epoque.PASSE));
        assertTrue(plateau.estVide(1, 2, Epoque.PASSE));
        assertTrue(plateau.estVide(1, 3, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.annuler());

        assertFalse(plateau.aGraine(0, 0, Epoque.PASSE));
        coup = new Plantation(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 0, Epoque.PASSE));
        assertEquals(0, tour.pion().ligne());
        assertEquals(0, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());
        assertTrue(plateau.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(plateau.aGraine(0, 0, Epoque.PASSE));

        coup = new Plantation(plateau, joueur, 0, 0, Epoque.PASSE);
        assertFalse(tour.jouerCoup(coup, 0, 0, Epoque.PASSE));
        assertEquals(0, tour.pion().ligne());
        assertEquals(0, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());
        assertTrue(plateau.aBlanc(0, 0, Epoque.PASSE));
        assertTrue(plateau.aGraine(0, 0, Epoque.PASSE));

        coup = new Recolte(plateau, joueur, 0, 0, Epoque.PASSE);
        assertTrue(tour.jouerCoup(coup, 0, 0, Epoque.PASSE));
        assertEquals(0, tour.pion().ligne());
        assertEquals(0, tour.pion().colonne());
        assertEquals(Epoque.PASSE, tour.pion().epoque());
        assertTrue(plateau.aBlanc(0, 0, Epoque.PASSE));
        assertFalse(plateau.aGraine(0, 0, Epoque.PASSE));

        assertTrue(tour.annuler());
        assertTrue(tour.annuler());
        assertTrue(tour.annuler());
        assertFalse(tour.annuler());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertTrue(tour.peutRefaire());
        assertTrue(tour.refaire());
        assertFalse(tour.peutRefaire());
        assertFalse(tour.refaire());
    }
}
