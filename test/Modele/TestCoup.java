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
        c2 = new Plantation(p, j2, 3, 3, Epoque.FUTUR);
        p.ajouter(1, 2, Epoque.PRESENT, Piece.BLANC);
        c3 = new Recolte(p, j1, 1, 2, Epoque.PRESENT);
    }

    @Test
    public void testInitialisation() {
        assertEquals(p, c1.plateau());
        assertEquals(p, c2.plateau());
        assertEquals(p, c3.plateau());
        assertEquals(j1, c1.joueur());
        assertEquals(j2, c2.joueur());
        assertEquals(j1, c3.joueur());
        Case pion1 = c1.pion();
        assertEquals(0, pion1.ligne());
        assertEquals(0, pion1.colonne());
        assertEquals(Epoque.PASSE, pion1.epoque());
        Case pion2 = c2.pion();
        assertEquals(3, pion2.ligne());
        assertEquals(3, pion2.colonne());
        assertEquals(Epoque.FUTUR, pion2.epoque());
        Case pion3 = c3.pion();
        assertEquals(1, pion3.ligne());
        assertEquals(2, pion3.colonne());
        assertEquals(Epoque.PRESENT, pion3.epoque());
    }

    @Test
    public void testDeplacerEtat() {
        c1.verifierAucunCoupCree();
        c1.deplacer(Piece.BLANC, 0, 0, Epoque.PASSE, 0, 1, Epoque.PASSE);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        c2.verifierAucunCoupCree();
        c2.deplacer(Piece.NOIR, 3, 3, Epoque.FUTUR, 3, 2, Epoque.FUTUR);
        e = assertThrows(IllegalStateException.class, c2::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        c3.verifierAucunCoupCree();
        c3.deplacer(Piece.BLANC, 1, 2, Epoque.PRESENT, 1, 1, Epoque.PRESENT);
        e = assertThrows(IllegalStateException.class, c3::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));
    }

    @Test
    public void testAjouterEtat() {
        c1.verifierAucunCoupCree();
        c1.ajouter(Piece.NOIR, 0, 1, Epoque.PASSE);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        c2.verifierAucunCoupCree();
        c2.ajouter(Piece.NOIR, 3, 2, Epoque.FUTUR);
        e = assertThrows(IllegalStateException.class, c2::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        c3.verifierAucunCoupCree();
        c3.ajouter(Piece.NOIR, 1, 1, Epoque.PRESENT);
        e = assertThrows(IllegalStateException.class, c3::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));
    }

    @Test
    public void testSupprimerEtat() {
        c1.verifierAucunCoupCree();
        c1.supprimer(Piece.BLANC, 0, 0, Epoque.PASSE);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        c2.verifierAucunCoupCree();
        c2.supprimer(Piece.NOIR, 3, 3, Epoque.FUTUR);
        e = assertThrows(IllegalStateException.class, c2::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));

        c3.verifierAucunCoupCree();
        c3.supprimer(Piece.BLANC, 1, 2, Epoque.PRESENT);
        e = assertThrows(IllegalStateException.class, c3::verifierAucunCoupCree);
        assertTrue(e.getMessage().contains("Impossible de créer un nouveau coup : un coup a déjà été créé"));
    }

    @Test
    public void testJouerAucunCoupCree() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : aucun coup créé"));

        e = assertThrows(IllegalStateException.class, c2::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : aucun coup créé"));

        e = assertThrows(IllegalStateException.class, c3::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : aucun coup créé"));
    }

    @Test
    public void testAnnulerAucunCoupCree() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : aucun coup créé"));

        e = assertThrows(IllegalStateException.class, c2::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : aucun coup créé"));

        e = assertThrows(IllegalStateException.class, c3::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : aucun coup créé"));
    }

    @Test
    public void testJouerCoupDejaJoue() {
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : coup déjà joué"));

        c2.creer(3, 2, Epoque.FUTUR);
        c2.jouer();
        e = assertThrows(IllegalStateException.class, c2::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : coup déjà joué"));

        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        c3.creer(1, 1, Epoque.PRESENT);
        c3.jouer();
        e = assertThrows(IllegalStateException.class, c3::jouer);
        assertTrue(e.getMessage().contains("Impossible de jouer le coup : coup déjà joué"));
    }

    @Test
    public void testAnnulerAucunCoupJoue() {
        c1.creer(0, 1, Epoque.PASSE);
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));

        c2.creer(3, 2, Epoque.FUTUR);
        e = assertThrows(IllegalStateException.class, c2::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));

        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        c3.creer(1, 1, Epoque.PRESENT);
        e = assertThrows(IllegalStateException.class, c3::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));
    }

    @Test
    public void testJouerAnnulerAnnuler() {
        c1.creer(0, 1, Epoque.PASSE);
        c1.jouer();
        c1.annuler();
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));

        c2.creer(3, 2, Epoque.FUTUR);
        c2.jouer();
        c2.annuler();
        e = assertThrows(IllegalStateException.class, c2::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));

        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        c3.creer(1, 1, Epoque.PRESENT);
        c3.jouer();
        c3.annuler();
        e = assertThrows(IllegalStateException.class, c3::annuler);
        assertTrue(e.getMessage().contains("Impossible d'annuler le coup : le coup n'a pas encore été joué"));
    }

    @Test
    public void testModificationPionJouerAnnuler() {
        c1.creer(0, 1, Epoque.PASSE);
        assertEquals(0, c1.pion().ligne());
        assertEquals(0, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.jouer();
        assertEquals(0, c1.pion().ligne());
        assertEquals(1, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.annuler();
        assertEquals(0, c1.pion().ligne());
        assertEquals(0, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());

        c2 = new Mouvement(p, j2, 3, 3, Epoque.FUTUR);
        c2.creer(2, 3, Epoque.FUTUR);
        assertEquals(3, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());
        c2.jouer();
        assertEquals(2, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());
        c2.annuler();
        assertEquals(3, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());

        c3 = new Mouvement(p, j1, 1, 2, Epoque.PRESENT);
        c3.creer(1, 2, Epoque.FUTUR);
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.PRESENT, c3.pion().epoque());
        c3.jouer();
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.FUTUR, c3.pion().epoque());
        c3.annuler();
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.PRESENT, c3.pion().epoque());
    }

    @Test
    public void testPion() {
        // Déplacement
        assertEquals(0, c1.pion().ligne());
        assertEquals(0, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.creer(0, 1, Epoque.PASSE);
        assertEquals(0, c1.pion().ligne());
        assertEquals(0, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.jouer();
        assertEquals(0, c1.pion().ligne());
        assertEquals(1, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.annuler();
        assertEquals(0, c1.pion().ligne());
        assertEquals(0, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());

        // Voyage temporel
        c1.jouer();
        c1 = new Mouvement(p, j1, 0, 1, Epoque.PASSE);
        assertEquals(0, c1.pion().ligne());
        assertEquals(1, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.creer(0, 1, Epoque.PRESENT);
        assertEquals(0, c1.pion().ligne());
        assertEquals(1, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());
        c1.jouer();
        assertEquals(0, c1.pion().ligne());
        assertEquals(1, c1.pion().colonne());
        assertEquals(Epoque.PRESENT, c1.pion().epoque());
        c1.annuler();
        assertEquals(0, c1.pion().ligne());
        assertEquals(1, c1.pion().colonne());
        assertEquals(Epoque.PASSE, c1.pion().epoque());

        // Plantation
        assertEquals(3, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());
        c2.creer(2, 3, Epoque.FUTUR);
        assertEquals(3, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());
        c2.jouer();
        assertEquals(3, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());
        c2.annuler();
        assertEquals(3, c2.pion().ligne());
        assertEquals(3, c2.pion().colonne());
        assertEquals(Epoque.FUTUR, c2.pion().epoque());

        // Récolte
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.PRESENT, c3.pion().epoque());
        c3.creer(1, 1, Epoque.PRESENT);
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.PRESENT, c3.pion().epoque());
        c3.jouer();
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.PRESENT, c3.pion().epoque());
        c3.annuler();
        assertEquals(1, c3.pion().ligne());
        assertEquals(2, c3.pion().colonne());
        assertEquals(Epoque.PRESENT, c3.pion().epoque());
    }

    @Test
    public void testDepart() {
        // Déplacement
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::depart);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case de départ : aucun coup créé"));
        c1.creer(0, 1, Epoque.PASSE);
        assertEquals(0, c1.depart().ligne());
        assertEquals(0, c1.depart().colonne());
        assertEquals(Epoque.PASSE, c1.depart().epoque());
        c1.jouer();
        assertEquals(0, c1.depart().ligne());
        assertEquals(0, c1.depart().colonne());
        assertEquals(Epoque.PASSE, c1.depart().epoque());
        c1.annuler();
        assertEquals(0, c1.depart().ligne());
        assertEquals(0, c1.depart().colonne());
        assertEquals(Epoque.PASSE, c1.depart().epoque());

        // Voyage temporel
        c1.jouer();
        c1 = new Mouvement(p, j1, 0, 1, Epoque.PASSE);
        e = assertThrows(IllegalStateException.class, c1::depart);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case de départ : aucun coup créé"));
        c1.creer(0, 1, Epoque.PRESENT);
        assertEquals(0, c1.depart().ligne());
        assertEquals(1, c1.depart().colonne());
        assertEquals(Epoque.PASSE, c1.depart().epoque());
        c1.jouer();
        assertEquals(0, c1.depart().ligne());
        assertEquals(1, c1.depart().colonne());
        assertEquals(Epoque.PASSE, c1.depart().epoque());
        c1.annuler();
        assertEquals(0, c1.depart().ligne());
        assertEquals(1, c1.depart().colonne());
        assertEquals(Epoque.PASSE, c1.depart().epoque());

        // Plantation
        e = assertThrows(IllegalStateException.class, c2::depart);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case de départ : aucun coup créé"));
        c2.creer(2, 3, Epoque.FUTUR);
        assertNull(c2.depart());
        c2.jouer();
        assertNull(c2.depart());
        c2.annuler();
        assertNull(c2.depart());

        // Récolte
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        e = assertThrows(IllegalStateException.class, c3::depart);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case de départ : aucun coup créé"));
        c3.creer(1, 1, Epoque.PRESENT);
        assertEquals(1, c3.depart().ligne());
        assertEquals(1, c3.depart().colonne());
        assertEquals(Epoque.PRESENT, c3.depart().epoque());
        c3.jouer();
        assertEquals(1, c3.depart().ligne());
        assertEquals(1, c3.depart().colonne());
        assertEquals(Epoque.PRESENT, c3.depart().epoque());
        c3.annuler();
        assertEquals(1, c3.depart().ligne());
        assertEquals(1, c3.depart().colonne());
        assertEquals(Epoque.PRESENT, c3.depart().epoque());
    }

    @Test
    public void testArrivee() {
        // Déplacement
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::arrivee);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case d'arrivée : aucun coup créé"));
        c1.creer(0, 1, Epoque.PASSE);
        assertEquals(0, c1.arrivee().ligne());
        assertEquals(1, c1.arrivee().colonne());
        assertEquals(Epoque.PASSE, c1.arrivee().epoque());
        c1.jouer();
        assertEquals(0, c1.arrivee().ligne());
        assertEquals(1, c1.arrivee().colonne());
        assertEquals(Epoque.PASSE, c1.arrivee().epoque());
        c1.annuler();
        assertEquals(0, c1.arrivee().ligne());
        assertEquals(1, c1.arrivee().colonne());
        assertEquals(Epoque.PASSE, c1.arrivee().epoque());

        // Voyage temporel
        c1.jouer();
        c1 = new Mouvement(p, j1, 0, 1, Epoque.PASSE);
        e = assertThrows(IllegalStateException.class, c1::arrivee);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case d'arrivée : aucun coup créé"));
        c1.creer(0, 1, Epoque.PRESENT);
        assertEquals(0, c1.arrivee().ligne());
        assertEquals(1, c1.arrivee().colonne());
        assertEquals(Epoque.PRESENT, c1.arrivee().epoque());
        c1.jouer();
        assertEquals(0, c1.arrivee().ligne());
        assertEquals(1, c1.arrivee().colonne());
        assertEquals(Epoque.PRESENT, c1.arrivee().epoque());
        c1.annuler();
        assertEquals(0, c1.arrivee().ligne());
        assertEquals(1, c1.arrivee().colonne());
        assertEquals(Epoque.PRESENT, c1.arrivee().epoque());

        // Plantation
        e = assertThrows(IllegalStateException.class, c2::arrivee);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case d'arrivée : aucun coup créé"));
        c2.creer(2, 3, Epoque.FUTUR);
        assertEquals(2, c2.arrivee().ligne());
        assertEquals(3, c2.arrivee().colonne());
        assertEquals(Epoque.FUTUR, c2.arrivee().epoque());
        c2.jouer();
        assertEquals(2, c2.arrivee().ligne());
        assertEquals(3, c2.arrivee().colonne());
        assertEquals(Epoque.FUTUR, c2.arrivee().epoque());
        c2.annuler();
        assertEquals(2, c2.arrivee().ligne());
        assertEquals(3, c2.arrivee().colonne());
        assertEquals(Epoque.FUTUR, c2.arrivee().epoque());

        // Récolte
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        e = assertThrows(IllegalStateException.class, c3::arrivee);
        assertTrue(e.getMessage().contains("Impossible de récupérer la case d'arrivée : aucun coup créé"));
        c3.creer(1, 1, Epoque.PRESENT);
        assertNull(c3.arrivee());
        c3.jouer();
        assertNull(c3.arrivee());
        c3.annuler();
        assertNull(c3.arrivee());
    }

    @Test
    public void testEstMouvement() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c1::estMouvement);
        assertTrue(e.getMessage().contains("Impossible de vérifier si le coup est un mouvement : aucun coup créé"));

        // Déplacement
        c1.creer(0, 1, Epoque.PASSE);
        assertTrue(c1.estMouvement());
        assertFalse(c1.estPlantation());
        assertFalse(c1.estRecolte());
        c1.jouer();

        // Voyage Temporel
        c1 = new Mouvement(p, j1, 0, 1, Epoque.PASSE);
        c1.creer(0, 1, Epoque.PRESENT);
        assertTrue(c1.estMouvement());
        assertFalse(c1.estPlantation());
        assertFalse(c1.estRecolte());
    }

    @Test
    public void testEstPlantation() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c2::estPlantation);
        assertTrue(e.getMessage().contains("Impossible de vérifier si le coup est une plantation : aucun coup créé"));

        // Déplacement
        c2.creer(2, 3, Epoque.FUTUR);
        assertTrue(c2.estPlantation());
        assertFalse(c2.estMouvement());
        assertFalse(c2.estRecolte());
    }

    @Test
    public void testEstRecolte() {
        IllegalStateException e = assertThrows(IllegalStateException.class, c3::estRecolte);
        assertTrue(e.getMessage().contains("Impossible de vérifier si le coup est une récolte : aucun coup créé"));

        // Déplacement
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        c3.creer(1, 1, Epoque.PRESENT);
        assertTrue(c3.estRecolte());
        assertFalse(c3.estMouvement());
        assertFalse(c3.estPlantation());
    }
}
