package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestJeu {
    Jeu j;

    @Before
    public void initialisation() {
        j = new Jeu();
    }

    private void assertPartieNonCommencee() {
        IllegalStateException e;
        e = assertThrows(IllegalStateException.class, j::joueurActuel);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur actuel : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, j::joueurSuivant);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur suivant : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, j::selectionnerPlantation);
        assertTrue(e.getMessage().contains("Impossible de sélectionner l'action planter une graine : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, j::selectionnerRecolte);
        assertTrue(e.getMessage().contains("Impossible de sélectionner l'action récolter une graine : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, () -> j.jouer(0, 1, Epoque.PASSE));
        assertTrue(e.getMessage().contains("Impossible de jouer : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, j::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, j::pionSelectionne);
        assertTrue(e.getMessage().contains("Impossible de vérifier si le pion est sélectionné : aucune partie créée"));
        e = assertThrows(IllegalStateException.class, j::nombreCoupsRestantsTour);
        assertTrue(e.getMessage().contains("Impossible de récupérer le nombre de coups restants à jouer pendant ce tour"));
    }

    @Test
    public void testInitialisation() {
        NullPointerException e = assertThrows(NullPointerException.class, j::plateau);
        assertTrue(e.getMessage().contains("Impossible de récupérer le plateau : aucune partie créée"));
        e = assertThrows(NullPointerException.class, j::joueur1);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur 1 : le joueur n'a pas été créé"));
        e = assertThrows(NullPointerException.class, j::joueur2);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur 2 : le joueur n'a pas été créé"));

        IllegalStateException e2 = assertThrows(IllegalStateException.class, j::nouvellePartie);
        assertTrue(e2.getMessage().contains("Impossible de lancer une nouvelle partie : joueurs manquants"));

        assertPartieNonCommencee();
    }

    @Test
    public void testNouveauJoueur() {
        NullPointerException e = assertThrows(NullPointerException.class, j::joueur1);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur 1 : le joueur n'a pas été créé"));

        j.nouveauJoueur("a", TypeJoueur.HUMAIN, Pion.BLANC, 0);
        assertNotNull(j.joueur1());
        j.joueur1().initialiserJoueur();
        assertEquals("a", j.joueur1().nom());
        assertEquals(TypeJoueur.HUMAIN, j.joueur1().type());
        assertEquals(Pion.BLANC, j.joueur1().pions());
        assertEquals(4, j.joueur1().nombrePionsReserve());
        assertEquals(0, j.joueur1().nombreVictoires());
        assertEquals(Epoque.PASSE, j.joueur1().focus());

        e = assertThrows(NullPointerException.class, j::joueur2);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur 2 : le joueur n'a pas été créé"));

        IllegalArgumentException e2 = assertThrows(
                IllegalArgumentException.class,
                () -> j.nouveauJoueur("b", TypeJoueur.HUMAIN, Pion.BLANC, 0)
        );
        assertTrue(e2.getMessage().contains("Impossible de créer le nouveau joueur : le joueur 1 possède déjà les pions Blanc"));

        j.nouveauJoueur("b", TypeJoueur.HUMAIN, Pion.NOIR, 1);
        assertNotNull(j.joueur2());
        j.joueur2().initialiserJoueur();
        assertEquals("b", j.joueur2().nom());
        assertEquals(TypeJoueur.HUMAIN, j.joueur2().type());
        assertEquals(Pion.NOIR, j.joueur2().pions());
        assertEquals(3, j.joueur2().nombrePionsReserve());
        assertEquals(0, j.joueur2().nombreVictoires());
        assertEquals(Epoque.FUTUR, j.joueur2().focus());

        IllegalStateException e3 = assertThrows(
                IllegalStateException.class,
                () -> j.nouveauJoueur("c", TypeJoueur.HUMAIN, Pion.BLANC, 0)
        );
        assertTrue(e3.getMessage().contains("Impossible d'ajouter un nouveau joueur : tous les joueurs ont déjà été ajoutés"));
    }

    @Test
    public void testNouvellePartie() {
        IllegalStateException e = assertThrows(IllegalStateException.class, j::nouvellePartie);
        assertTrue(e.getMessage().contains("Impossible de lancer une nouvelle partie : joueurs manquants"));

        j.nouveauJoueur("abc", TypeJoueur.IA_MOYEN, Pion.BLANC, 3);
        e = assertThrows(IllegalStateException.class, j::nouvellePartie);
        assertTrue(e.getMessage().contains("Impossible de lancer une nouvelle partie : joueurs manquants"));

        j.nouveauJoueur("xyz", TypeJoueur.IA_DIFFICILE, Pion.NOIR, 3);

        NullPointerException e2 = assertThrows(NullPointerException.class, j::plateau);
        assertTrue(e2.getMessage().contains("Impossible de récupérer le plateau : aucune partie créée"));

        j.nouvellePartie();
        assertNotNull(j.plateau());
        assertNotNull(j.joueurActuel());
        assertNotNull(j.joueurSuivant());
        assertTrue(j.joueurActuel() == j.joueur1() || j.joueurActuel() == j.joueur2());
        assertTrue(j.joueurSuivant() == j.joueur1() || j.joueurSuivant() == j.joueur2());
        assertNotEquals(j.joueurActuel(), j.joueurSuivant());
        assertNotEquals(j.joueur1(), j.joueur2());
        assertFalse(j.partieTerminee());
        assertNull(j.vainqueur());
        assertFalse(j.pionSelectionne());
        assertEquals(2, j.nombreCoupsRestantsTour());
    }

    private void initialiserPartie() {
        j.nouveauJoueur("1", TypeJoueur.HUMAIN, Pion.NOIR, 2);
        j.nouveauJoueur("2", TypeJoueur.IA_FACILE, Pion.BLANC, 1);
        j.nouvellePartie();
    }

    @Test
    public void testSelectionnerPlantation() {
        initialiserPartie();

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.selectionnerPlantation();
            j.jouer(0, 0, Epoque.PASSE);

            j.selectionnerPlantation();
            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));

            j.selectionnerPlantation();
            j.selectionnerPlantation();
            assertFalse(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            j.annuler();

            j.selectionnerPlantation();
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
        } else {
            j.selectionnerPlantation();
            j.jouer(3, 3, Epoque.FUTUR);

            j.selectionnerPlantation();
            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));

            j.selectionnerPlantation();
            j.selectionnerPlantation();
            assertFalse(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            j.annuler();

            j.selectionnerPlantation();
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
        }
    }

    @Test
    public void testSelectionnerRecolte() {
        initialiserPartie();

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);

            j.plateau().ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
            j.selectionnerRecolte();
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.jouer(1, 0, Epoque.PASSE);
            assertTrue(j.plateau().estVide(1, 0, Epoque.PASSE));
            j.annuler();

            j.selectionnerRecolte();
            j.selectionnerRecolte();
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));

            j.plateau().ajouter(0, 2, Epoque.PASSE, Piece.GRAINE);
            j.selectionnerRecolte();
            assertTrue(j.plateau().aGraine(0, 2, Epoque.PASSE));
            j.jouer(0, 2, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 2, Epoque.PASSE));
        } else {
            j.jouer(3, 3, Epoque.FUTUR);

            j.plateau().ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
            j.selectionnerRecolte();
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.jouer(2, 3, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(2, 3, Epoque.FUTUR));
            j.annuler();

            j.selectionnerRecolte();
            j.selectionnerRecolte();
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));

            j.plateau().ajouter(3, 1, Epoque.FUTUR, Piece.GRAINE);
            j.selectionnerRecolte();
            assertTrue(j.plateau().aGraine(3, 1, Epoque.FUTUR));
            j.jouer(3, 1, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(3, 1, Epoque.FUTUR));
        }
    }

    @Test
    public void testSelectionnerPion() {
        initialiserPartie();

        if (j.joueurActuel().pions() == Pion.BLANC) {
            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.PASSE);
            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());

            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PRESENT);

            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.PRESENT);
            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());
        } else {
            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.PASSE);
            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());

            j.jouer(3, 2, Epoque.FUTUR);
            j.jouer(3, 1, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PRESENT);

            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.PRESENT);
            assertFalse(j.pionSelectionne());
            j.jouer(3, 3, Epoque.PASSE);
            assertFalse(j.pionSelectionne());
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());
        }
    }

    @Test
    public void testJouerAnnulerMouvement() {
        initialiserPartie();
        assertEquals(2, j.nombreCoupsRestantsTour());
        assertFalse(j.pionSelectionne());

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aBlanc(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().estVide(2, 3, Epoque.PASSE));
            j.jouer(2, 3, Epoque.PASSE);
            assertTrue(j.plateau().estVide(2, 3, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 2, Epoque.PASSE));
            j.jouer(0, 2, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 3, Epoque.PASSE));
            j.jouer(0, 3, Epoque.PASSE);
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 3, Epoque.PASSE));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 2, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aBlanc(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.pionSelectionne());
            j.annuler();
            assertFalse(j.pionSelectionne());

            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());

            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.pionSelectionne());
        } else {
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aNoir(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().estVide(1, 0, Epoque.FUTUR));
            j.jouer(1, 0, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(1, 0, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 1, Epoque.FUTUR));
            j.jouer(3, 1, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 0, Epoque.FUTUR));
            j.jouer(3, 0, Epoque.FUTUR);
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 0, Epoque.FUTUR));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 1, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aNoir(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.pionSelectionne());
            j.annuler();
            assertFalse(j.pionSelectionne());

            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());

            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());
        }
    }

    @Test
    public void testJouerAnnulerPlantation() {
        initialiserPartie();
        assertEquals(2, j.nombreCoupsRestantsTour());
        assertFalse(j.pionSelectionne());

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.selectionnerPlantation();
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(2, 3, Epoque.PASSE));
            j.selectionnerPlantation();
            j.jouer(2, 3, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(2, 3, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.selectionnerPlantation();
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.selectionnerPlantation();
            j.jouer(0, 2, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(1, 0, Epoque.PASSE));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.annuler();
            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.annuler();
            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.pionSelectionne());
            j.annuler();
            assertFalse(j.pionSelectionne());

            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());

            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.pionSelectionne());

            j.selectionnerPlantation();
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.plateau().estVide(1, 0, Epoque.PASSE));
            j.jouer(1, 0, Epoque.PASSE);
            assertTrue(j.plateau().aBlanc(1, 0, Epoque.PASSE));

            j.selectionnerPlantation();
            assertFalse(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.jouer(1, 0, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));

            assertTrue(j.plateau().estVide(1, 1, Epoque.PASSE));
            j.jouer(1, 1, Epoque.PASSE);
            assertFalse(j.plateau().aBlanc(1, 1, Epoque.PASSE));
        } else {
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.selectionnerPlantation();
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(1, 0, Epoque.FUTUR));
            j.selectionnerPlantation();
            j.jouer(1, 0, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(1, 0, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.selectionnerPlantation();
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.selectionnerPlantation();
            j.jouer(3, 1, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.annuler();
            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.annuler();
            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.pionSelectionne());
            j.annuler();
            assertFalse(j.pionSelectionne());

            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());

            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());

            j.selectionnerPlantation();
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(2, 3, Epoque.FUTUR));
            j.jouer(2, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aNoir(2, 3, Epoque.FUTUR));

            j.selectionnerPlantation();
            assertFalse(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.jouer(2, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));

            assertTrue(j.plateau().estVide(2, 2, Epoque.FUTUR));
            j.jouer(2, 2, Epoque.FUTUR);
            assertFalse(j.plateau().aNoir(2, 2, Epoque.FUTUR));
        }
    }

    @Test
    public void testJouerAnnulerRecolte() {
        initialiserPartie();
        assertEquals(2, j.nombreCoupsRestantsTour());
        assertFalse(j.pionSelectionne());

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.selectionnerRecolte();
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(2, 3, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.PASSE));
            j.selectionnerRecolte();
            j.jouer(2, 3, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.selectionnerRecolte();
            j.jouer(0, 1, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            assertEquals(0, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.selectionnerRecolte();
            j.jouer(1, 0, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.pionSelectionne());
            j.annuler();
            assertFalse(j.pionSelectionne());

            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.pionSelectionne());

            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.pionSelectionne());

            j.selectionnerRecolte();
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.plateau().aPion(1, 0, Epoque.PASSE));
            j.jouer(1, 0, Epoque.PASSE);
            assertTrue(j.plateau().aBlanc(1, 0, Epoque.PASSE));

            j.selectionnerRecolte();
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.jouer(1, 0, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(1, 0, Epoque.PASSE));

            assertTrue(j.plateau().estVide(1, 1, Epoque.PASSE));
            j.jouer(1, 1, Epoque.PASSE);
            assertTrue(j.plateau().estVide(1, 1, Epoque.PASSE));
        } else {
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(3, 3, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.selectionnerRecolte();
            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(1, 0, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.FUTUR));
            j.selectionnerRecolte();
            j.jouer(1, 0, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(3, 2, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.selectionnerRecolte();
            j.jouer(3, 2, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            assertEquals(0, j.nombreCoupsRestantsTour());

            j.plateau().ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.selectionnerRecolte();
            j.jouer(2, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            assertEquals(0, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            assertEquals(1, j.nombreCoupsRestantsTour());

            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            assertEquals(2, j.nombreCoupsRestantsTour());

            assertTrue(j.pionSelectionne());
            j.annuler();
            assertFalse(j.pionSelectionne());

            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.pionSelectionne());

            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.pionSelectionne());

            j.selectionnerRecolte();
            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.plateau().aPion(2, 3, Epoque.FUTUR));
            j.jouer(2, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aNoir(2, 3, Epoque.FUTUR));

            j.selectionnerRecolte();
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.jouer(2, 3, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(2, 3, Epoque.FUTUR));

            assertTrue(j.plateau().estVide(2, 2, Epoque.FUTUR));
            j.jouer(2, 2, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(2, 2, Epoque.FUTUR));
        }
    }

    @Test
    public void testFocus() {
        Joueur joueur;
        initialiserPartie();

        if (j.joueurActuel().pions() == Pion.BLANC) {
            joueur = j.joueurActuel();
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PASSE);
            assertEquals(Epoque.PASSE, joueur.focus());
            j.jouer(0, 0, Epoque.PASSE);
            assertEquals(Epoque.PASSE, joueur.focus());
            j.jouer(0, 0, Epoque.PRESENT);
            assertEquals(Epoque.PRESENT, joueur.focus());

            joueur = j.joueurActuel();
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(3, 3, Epoque.FUTUR);
            assertEquals(Epoque.FUTUR, joueur.focus());
            j.jouer(0, 0, Epoque.PASSE);
            assertEquals(Epoque.PASSE, joueur.focus());
        } else {
            joueur = j.joueurActuel();
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(3, 3, Epoque.FUTUR);
            assertEquals(Epoque.FUTUR, joueur.focus());
            j.jouer(0, 0, Epoque.FUTUR);
            assertEquals(Epoque.FUTUR, joueur.focus());
            j.jouer(0, 0, Epoque.PRESENT);
            assertEquals(Epoque.PRESENT, joueur.focus());

            joueur = j.joueurActuel();
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PASSE);
            assertEquals(Epoque.PASSE, joueur.focus());
            j.jouer(3, 3, Epoque.FUTUR);
            assertEquals(Epoque.FUTUR, joueur.focus());
        }
        joueur = j.joueurActuel();
        assertEquals(Epoque.PRESENT, joueur.focus());
        j.jouer(0, 0, Epoque.PRESENT);
        assertEquals(Epoque.PRESENT, joueur.focus());
        j.jouer(3, 3, Epoque.FUTUR);
        assertEquals(Epoque.PRESENT, joueur.focus());
        j.jouer(3, 3, Epoque.PRESENT);
        assertEquals(Epoque.PRESENT, joueur.focus());
        j.jouer(3, 3, Epoque.PASSE);
        assertEquals(Epoque.PRESENT, joueur.focus());
    }

    @Test
    public void testFinPartie() {
        initialiserPartie();

        if (j.joueurActuel().pions() == Pion.BLANC) {
            // Blanc
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(1, 3, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 0, Epoque.FUTUR);
            j.jouer(0, 1, Epoque.FUTUR);
            j.jouer(0, 2, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Noir
            j.jouer(3, 3, Epoque.PASSE);
            j.jouer(2, 3, Epoque.PASSE);
            j.jouer(1, 3, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PRESENT);
            j.jouer(2, 2, Epoque.FUTUR);

            // Blanc
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PRESENT);
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(2, 3, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(3, 2, Epoque.PRESENT);
            j.jouer(0, 0, Epoque.FUTUR);

            // Blanc
            j.jouer(0, 2, Epoque.FUTUR);
            j.jouer(1, 2, Epoque.FUTUR);
            j.jouer(1, 3, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Noir
            j.jouer(0, 0, Epoque.PRESENT);

            // Blanc
            assertFalse(j.partieTerminee());
            j.jouer(0, 2, Epoque.PASSE);
            assertFalse(j.partieTerminee());
            j.jouer(1, 2, Epoque.PASSE);
            assertFalse(j.partieTerminee());
            j.jouer(1, 3, Epoque.PASSE);
            assertFalse(j.partieTerminee());
            assertNull(j.vainqueur());
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.partieTerminee());
            assertNull(j.vainqueur());
            j.jouer(0, 0, Epoque.FUTUR);
            assertTrue(j.partieTerminee());
            assertNotNull(j.vainqueur());
            assertNotEquals(j.joueur1(), j.vainqueur());
            assertEquals(j.joueur2(), j.vainqueur());

            j.nouvellePartie();
            assertEquals(j.joueur1(), j.joueurActuel());
            assertEquals(j.joueur2(), j.joueurSuivant());
            assertFalse(j.partieTerminee());
            assertFalse(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());
            assertEquals(0, j.joueur1().nombreVictoires());
            assertEquals(1, j.joueur2().nombreVictoires());
            assertEquals(2, j.joueur1().nombrePionsReserve());
            assertEquals(3, j.joueur2().nombrePionsReserve());
            assertEquals(5, j.plateau().nombreGrainesReserve());
            assertEquals(Epoque.FUTUR, j.joueur1().focus());
            assertEquals(Epoque.PASSE, j.joueur2().focus());

            // Noir
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.PRESENT);
            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(2, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(2, 3, Epoque.PRESENT));
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            j.jouer(0, 0, Epoque.FUTUR);

            assertFalse(j.partieTerminee());
            assertEquals(0, j.joueur1().nombreVictoires());
            assertEquals(1, j.joueur2().nombreVictoires());
            assertEquals(1, j.joueur1().nombrePionsReserve());
            assertEquals(3, j.joueur2().nombrePionsReserve());
            assertEquals(5, j.plateau().nombreGrainesReserve());

            j.nouvellePartie();
            assertFalse(j.partieTerminee());
            assertEquals(0, j.joueur1().nombreVictoires());
            assertEquals(1, j.joueur2().nombreVictoires());
        } else {
            // Noir
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(3, 2, Epoque.FUTUR);
            j.jouer(3, 1, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(1, 0, Epoque.PASSE);
            j.jouer(2, 0, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.PASSE);
            j.jouer(3, 2, Epoque.PASSE);
            j.jouer(3, 1, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Blanc
            j.jouer(0, 0, Epoque.FUTUR);
            j.jouer(1, 0, Epoque.FUTUR);
            j.jouer(2, 0, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PRESENT);

            // Noir
            j.jouer(2, 2, Epoque.PASSE);
            j.jouer(3, 1, Epoque.FUTUR);
            j.jouer(3, 1, Epoque.PRESENT);
            j.jouer(3, 0, Epoque.PRESENT);
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 0, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(1, 0, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(0, 1, Epoque.PRESENT);
            j.jouer(0, 0, Epoque.PASSE);

            // Noir
            j.jouer(3, 1, Epoque.PASSE);
            j.jouer(2, 1, Epoque.PASSE);
            j.jouer(2, 0, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Blanc
            j.jouer(0, 0, Epoque.PRESENT);

            // Noir
            assertFalse(j.partieTerminee());
            j.jouer(3, 1, Epoque.FUTUR);
            assertFalse(j.partieTerminee());
            j.jouer(2, 1, Epoque.FUTUR);
            assertFalse(j.partieTerminee());
            j.jouer(2, 0, Epoque.FUTUR);
            assertFalse(j.partieTerminee());
            assertNull(j.vainqueur());
            j.jouer(0, 0, Epoque.FUTUR);
            assertFalse(j.partieTerminee());
            assertNull(j.vainqueur());
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.partieTerminee());
            assertNotNull(j.vainqueur());
            assertNotEquals(j.joueur2(), j.vainqueur());
            assertEquals(j.joueur1(), j.vainqueur());

            j.nouvellePartie();
            assertEquals(j.joueur2(), j.joueurActuel());
            assertEquals(j.joueur1(), j.joueurSuivant());
            assertFalse(j.partieTerminee());
            assertFalse(j.pionSelectionne());
            assertEquals(2, j.nombreCoupsRestantsTour());
            assertEquals(1, j.joueur1().nombreVictoires());
            assertEquals(0, j.joueur2().nombreVictoires());
            assertEquals(2, j.joueur1().nombrePionsReserve());
            assertEquals(3, j.joueur2().nombrePionsReserve());
            assertEquals(5, j.plateau().nombreGrainesReserve());
            assertEquals(Epoque.FUTUR, j.joueur1().focus());
            assertEquals(Epoque.PASSE, j.joueur2().focus());

            // Blanc
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.PRESENT);
            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(2, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(2, 3, Epoque.PRESENT));
            j.jouer(0, 0, Epoque.PASSE);

            assertFalse(j.partieTerminee());
            assertEquals(1, j.joueur1().nombreVictoires());
            assertEquals(0, j.joueur2().nombreVictoires());
            assertEquals(1, j.joueur1().nombrePionsReserve());
            assertEquals(3, j.joueur2().nombrePionsReserve());
            assertEquals(5, j.plateau().nombreGrainesReserve());
            j.nouvellePartie();
            assertFalse(j.partieTerminee());
            assertEquals(1, j.joueur1().nombreVictoires());
            assertEquals(0, j.joueur2().nombreVictoires());
        }
        assertEquals(2, j.joueur1().nombrePionsReserve());
        assertEquals(3, j.joueur2().nombrePionsReserve());
        assertEquals(5, j.plateau().nombreGrainesReserve());
    }

    @Test
    public void testExceptionsPartieTermine() {
        IllegalStateException e;
        initialiserPartie();

        if (j.joueurActuel().pions() == Pion.BLANC) {
            // Blanc
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(1, 3, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 0, Epoque.FUTUR);
            j.jouer(0, 1, Epoque.FUTUR);
            j.jouer(0, 2, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Noir
            j.jouer(3, 3, Epoque.PASSE);
            j.jouer(2, 3, Epoque.PASSE);
            j.jouer(1, 3, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PRESENT);

            // Blanc
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(1, 2, Epoque.PASSE);
            j.jouer(1, 3, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(2, 3, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(3, 2, Epoque.PRESENT);
            j.jouer(0, 0, Epoque.FUTUR);

            // Blanc
            j.jouer(0, 2, Epoque.FUTUR);
            j.jouer(1, 2, Epoque.FUTUR);
            j.jouer(1, 3, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);
        } else {
            // Noir
            j.jouer(3, 3, Epoque.FUTUR);
            j.jouer(2, 3, Epoque.FUTUR);
            j.jouer(1, 3, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 0, Epoque.PASSE);
            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(3, 3, Epoque.PASSE);
            j.jouer(2, 3, Epoque.PASSE);
            j.jouer(1, 3, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PRESENT);

            // Blanc
            j.jouer(0, 0, Epoque.FUTUR);
            j.jouer(0, 1, Epoque.FUTUR);
            j.jouer(0, 2, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PASSE);

            // Noir
            j.jouer(3, 3, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(2, 3, Epoque.PRESENT);
            j.selectionnerPlantation();
            j.jouer(3, 2, Epoque.PRESENT);
            j.jouer(0, 0, Epoque.PASSE);

            // Blanc
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(1, 2, Epoque.PASSE);
            j.jouer(1, 3, Epoque.PASSE);
            j.jouer(0, 0, Epoque.FUTUR);

            // Noir
            j.jouer(0, 0, Epoque.FUTUR);

            // Blanc
            j.jouer(0, 2, Epoque.FUTUR);
            j.jouer(1, 2, Epoque.FUTUR);
            j.jouer(1, 3, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PRESENT);
        }
        assertTrue(j.partieTerminee());

        e = assertThrows(IllegalStateException.class, j::joueurActuel);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur actuel : partie terminée"));
        e = assertThrows(IllegalStateException.class, j::joueurSuivant);
        assertTrue(e.getMessage().contains("Impossible de récupérer le joueur suivant : partie terminée"));
        e = assertThrows(IllegalStateException.class, j::selectionnerPlantation);
        assertTrue(e.getMessage().contains("Impossible de sélectionner l'action planter une graine : partie terminée"));
        e = assertThrows(IllegalStateException.class, j::selectionnerRecolte);
        assertTrue(e.getMessage().contains("Impossible de sélectionner l'action récolter une graine : partie terminée"));
        e = assertThrows(IllegalStateException.class, () -> j.jouer(0, 0, Epoque.PRESENT));
        assertTrue(e.getMessage().contains("Impossible de jouer : partie terminée"));
    }
}
