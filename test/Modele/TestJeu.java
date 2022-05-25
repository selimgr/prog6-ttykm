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
        assertTrue(j.prochaineActionSelectionPion());
        assertFalse(j.prochainCoupMouvement());
        assertFalse(j.prochainCoupPlantation());
        assertFalse(j.prochainCoupRecolte());
        assertFalse(j.prochaineActionChangementFocus());
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
            j.selectionnerMouvement();
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
            j.selectionnerMouvement();
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
            j.selectionnerMouvement();
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
            j.selectionnerMouvement();
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
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.FUTUR);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.PASSE);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.prochaineActionSelectionPion());

            j.jouer(0, 1, Epoque.PASSE);
            j.jouer(0, 2, Epoque.PASSE);
            j.jouer(0, 0, Epoque.PRESENT);

            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.PRESENT);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.FUTUR);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.prochaineActionSelectionPion());
        } else {
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.PASSE);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.FUTUR);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.prochaineActionSelectionPion());

            j.jouer(3, 2, Epoque.FUTUR);
            j.jouer(3, 1, Epoque.FUTUR);
            j.jouer(0, 0, Epoque.PRESENT);

            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.PRESENT);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(3, 3, Epoque.PASSE);
            assertTrue(j.prochaineActionSelectionPion());
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.prochaineActionSelectionPion());
        }
    }

    @Test
    public void testJouerAnnulerRefaireMouvement() {
        initialiserPartie();
        assertTrue(j.prochaineActionSelectionPion());
        assertFalse(j.prochainCoupMouvement());
        assertFalse(j.prochainCoupPlantation());
        assertFalse(j.prochainCoupRecolte());
        assertFalse(j.prochaineActionChangementFocus());

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aBlanc(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().estVide(2, 3, Epoque.PASSE));
            j.jouer(2, 3, Epoque.PASSE);
            assertTrue(j.plateau().estVide(2, 3, Epoque.PASSE));

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 2, Epoque.PASSE));
            j.jouer(0, 2, Epoque.PASSE);
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));

            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 3, Epoque.PASSE));
            j.jouer(0, 3, Epoque.PASSE);
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 3, Epoque.PASSE));

            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 2, Epoque.PASSE));
            j.refaire();
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 2, Epoque.PASSE));
            j.annuler();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aBlanc(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().estVide(0, 1, Epoque.PASSE));
            j.refaire();
            assertTrue(j.plateau().estVide(0, 0, Epoque.PASSE));
            assertTrue(j.plateau().aBlanc(0, 1, Epoque.PASSE));
            j.annuler();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

            j.refaire();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            j.jouer(0, 0, Epoque.PASSE);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.jouer(0, 0, Epoque.PASSE);

        } else {
            j.jouer(3, 3, Epoque.FUTUR);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aNoir(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().estVide(1, 0, Epoque.FUTUR));
            j.jouer(1, 0, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(1, 0, Epoque.FUTUR));

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 1, Epoque.FUTUR));
            j.jouer(3, 1, Epoque.FUTUR);
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));

            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 0, Epoque.FUTUR));
            j.jouer(3, 0, Epoque.FUTUR);
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 0, Epoque.FUTUR));

            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 1, Epoque.FUTUR));
            j.refaire();
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 1, Epoque.FUTUR));
            j.annuler();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aNoir(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().estVide(3, 2, Epoque.FUTUR));
            j.refaire();
            assertTrue(j.plateau().estVide(3, 3, Epoque.FUTUR));
            assertTrue(j.plateau().aNoir(3, 2, Epoque.FUTUR));
            j.annuler();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

            j.refaire();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            j.jouer(3, 3, Epoque.FUTUR);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.jouer(3, 3, Epoque.FUTUR);

        }
        assertTrue(j.prochaineActionSelectionPion());
        assertFalse(j.prochaineActionJouerCoup());
        assertFalse(j.prochaineActionChangementFocus());
    }

    @Test
    public void testJouerAnnulerPlantation() {
        initialiserPartie();
        assertTrue(j.prochaineActionSelectionPion());
        assertFalse(j.prochaineActionJouerCoup());
        assertFalse(j.prochaineActionChangementFocus());

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.jouer(0, 0, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(2, 3, Epoque.PASSE));
            j.jouer(2, 3, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(2, 3, Epoque.PASSE));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.jouer(0, 2, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(1, 0, Epoque.PASSE));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.annuler();
            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.refaire();
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.annuler();

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.annuler();
            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.refaire();
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.annuler();

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

            j.refaire();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            j.jouer(0, 0, Epoque.PASSE);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.jouer(0, 0, Epoque.PASSE);

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

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

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.jouer(3, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(1, 0, Epoque.FUTUR));
            j.jouer(1, 0, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(1, 0, Epoque.FUTUR));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.jouer(3, 1, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(2, 3, Epoque.FUTUR));

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.annuler();
            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.refaire();
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.annuler();

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.annuler();
            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.refaire();
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.annuler();

            j.selectionnerPlantation();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertTrue(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

            j.refaire();

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            j.jouer(3, 3, Epoque.FUTUR);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.jouer(3, 3, Epoque.FUTUR);

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

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
        assertTrue(j.prochaineActionSelectionPion());
        assertFalse(j.prochaineActionJouerCoup());
        assertFalse(j.prochaineActionChangementFocus());

        if (j.joueurActuel().pions() == Pion.BLANC) {
            j.jouer(0, 0, Epoque.PASSE);

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.plateau().ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.jouer(0, 0, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.plateau().ajouter(2, 3, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.PASSE));
            j.jouer(2, 3, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.PASSE));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.plateau().ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.jouer(0, 1, Epoque.PASSE);
            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            j.plateau().ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));
            j.jouer(1, 0, Epoque.PASSE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.PASSE));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.refaire();
            assertFalse(j.plateau().aGraine(0, 1, Epoque.PASSE));
            j.annuler();

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.annuler();
            assertTrue(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.refaire();
            assertFalse(j.plateau().aGraine(0, 0, Epoque.PASSE));
            j.annuler();

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

            j.refaire();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            j.jouer(0, 0, Epoque.PASSE);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.jouer(0, 0, Epoque.PASSE);

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

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

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.plateau().ajouter(3, 3, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.jouer(3, 3, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.plateau().ajouter(1, 0, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.FUTUR));
            j.jouer(1, 0, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(1, 0, Epoque.FUTUR));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.plateau().ajouter(3, 2, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.jouer(3, 2, Epoque.FUTUR);
            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            j.plateau().ajouter(2, 3, Epoque.FUTUR, Piece.GRAINE);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));
            j.jouer(2, 3, Epoque.FUTUR);
            assertTrue(j.plateau().aGraine(2, 3, Epoque.FUTUR));

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertTrue(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.refaire();
            assertFalse(j.plateau().aGraine(3, 2, Epoque.FUTUR));
            j.annuler();

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.annuler();
            assertTrue(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.refaire();
            assertFalse(j.plateau().aGraine(3, 3, Epoque.FUTUR));
            j.annuler();

            j.selectionnerRecolte();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertFalse(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertTrue(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

            j.refaire();
            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.annuler();

            j.jouer(3, 3, Epoque.FUTUR);

            assertFalse(j.prochaineActionSelectionPion());
            assertTrue(j.prochaineActionJouerCoup());
            assertTrue(j.prochainCoupMouvement());
            assertFalse(j.prochainCoupPlantation());
            assertFalse(j.prochainCoupRecolte());
            assertFalse(j.prochaineActionChangementFocus());

            j.jouer(3, 3, Epoque.FUTUR);

            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());

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
            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());
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
            assertTrue(j.prochaineActionSelectionPion());
            assertFalse(j.prochaineActionJouerCoup());
            assertFalse(j.prochaineActionChangementFocus());
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
    }
}
