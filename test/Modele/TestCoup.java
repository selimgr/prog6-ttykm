package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoup {
    Plateau p;
    Joueur j1, j2;
    Coup c1, c2, c3;

    @Before
    public void initialisation() {
        p = new Plateau();
        j1 = new Joueur("a", TypeJoueur.HUMAIN, Pion.BLANC, 0);
        j2 = new Joueur("b", TypeJoueur.HUMAIN, Pion.NOIR, 0);
        c1 = new Mouvement(p, j1, 0, 0, Epoque.PASSE);
        c2 = new Mouvement(p, j2, 3, 3, Epoque.FUTUR);
        c3 = new Mouvement(p, j1, 1, 2, Epoque.PRESENT);
    }

    @Test
    public void testInitialisation() {
        assertEquals(p, c1.plateau());
        assertEquals(p, c2.plateau());
        assertEquals(p, c3.plateau());
        assertEquals(j1, c1.joueur());
        assertEquals(j2, c2.joueur());
        assertEquals(j1, c3.joueur());
        assertEquals(0, c1.lignePion());
        assertEquals(3, c2.lignePion());
        assertEquals(1, c3.lignePion());
        assertEquals(0, c1.colonnePion());
        assertEquals(3, c2.colonnePion());
        assertEquals(2, c3.colonnePion());
        assertEquals(Epoque.PASSE, c1.epoquePion());
        assertEquals(Epoque.FUTUR, c2.epoquePion());
        assertEquals(Epoque.PRESENT, c3.epoquePion());
    }

    @Test
    public void testAjouterEtat() {
        c1.verifierPremierCoupCree();
        c1.ajouter(1, 1, Epoque.PRESENT, Piece.NOIR);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::verifierPremierCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));
    }

    @Test
    public void testSupprimerEtat() {
        c1.verifierPremierCoupCree();
        c1.supprimer(0, 0, Epoque.PRESENT, Piece.BLANC);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::verifierPremierCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));
    }

    @Test
    public void testJouerAucunCoupCree() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : aucun coup créé"));
    }

    @Test
    public void testAnnulerAucunCoupCree() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : aucun coup créé"));
    }

    @Test
    public void testJouerCoupDejaJoue() {
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : coup déjà joué"));
    }

    @Test
    public void testAnnulerAucunCoupJoue() {
        c1.creer(0, 1, Epoque.PASSE);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));
    }

    @Test
    public void testJouerAnnulerAnnuler() {
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        c1.annuler();
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));
    }
}
