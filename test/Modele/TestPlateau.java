package Modele;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlateau {
    Plateau p;

    @Before
    public void initialisation() {
        p = new Plateau();
    }

    @Test
    public void testInitialisation() {
        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                if (i == 0 && j == 0) {
                    assertTrue(p.aBlanc(i, j, Epoque.PASSE));
                    assertTrue(p.aBlanc(i, j, Epoque.PRESENT));
                    assertTrue(p.aBlanc(i, j, Epoque.FUTUR));
                    assertFalse(p.aNoir(i, j, Epoque.PASSE));
                    assertFalse(p.aNoir(i, j, Epoque.PRESENT));
                    assertFalse(p.aNoir(i, j, Epoque.FUTUR));
                    assertFalse(p.estVide(i, j, Epoque.PASSE));
                    assertFalse(p.estVide(i, j, Epoque.PRESENT));
                    assertFalse(p.estVide(i, j, Epoque.FUTUR));
                } else if (i == Plateau.TAILLE - 1 && i == j) {
                    assertTrue(p.aNoir(i, j, Epoque.PASSE));
                    assertTrue(p.aNoir(i, j, Epoque.PRESENT));
                    assertTrue(p.aNoir(i, j, Epoque.FUTUR));
                    assertFalse(p.aBlanc(i, j, Epoque.PASSE));
                    assertFalse(p.aBlanc(i, j, Epoque.PRESENT));
                    assertFalse(p.aBlanc(i, j, Epoque.FUTUR));
                    assertFalse(p.estVide(i, j, Epoque.PASSE));
                    assertFalse(p.estVide(i, j, Epoque.PRESENT));
                    assertFalse(p.estVide(i, j, Epoque.FUTUR));
                } else {
                    assertTrue(p.estVide(i, j, Epoque.PASSE));
                    assertTrue(p.estVide(i, j, Epoque.PRESENT));
                    assertTrue(p.estVide(i, j, Epoque.FUTUR));
                    assertFalse(p.aBlanc(i, j, Epoque.PASSE));
                    assertFalse(p.aBlanc(i, j, Epoque.PRESENT));
                    assertFalse(p.aBlanc(i, j, Epoque.FUTUR));
                    assertFalse(p.aNoir(i, j, Epoque.PASSE));
                    assertFalse(p.aNoir(i, j, Epoque.PRESENT));
                    assertFalse(p.aNoir(i, j, Epoque.FUTUR));
                }
            }
        }
    }

    @Test
    public void testAMur() {
        for (int i = -100; i < 100; i++) {
            for (int j = -100; j < 100; j++) {
                if (i < 0 || j < 0 || i >= Plateau.TAILLE || j >= Plateau.TAILLE) {
                    assertTrue(Plateau.aMur(i, j));
                } else {
                    assertFalse(Plateau.aMur(i, j));
                }
            }
        }
    }

    @Test
    public void testExceptionContenu() {
        IllegalArgumentException e;

        for (int i = -100; i < 100; i++) {
            for (int j = -100; j < 100; j++) {
                if (i < 0 || j < 0 || i >= Plateau.TAILLE || j >= Plateau.TAILLE) {
                    final int x = i;
                    final int y = j;
                    e = assertThrows(IllegalArgumentException.class, () -> p.estVide(x, y, Epoque.PASSE));
                    assertTrue(e.getMessage().contains("Coordonnées (" + x + ", " + y + ", Passé) incorrectes"));
                    e = assertThrows(IllegalArgumentException.class, () -> p.estVide(x, y, Epoque.PRESENT));
                    assertTrue(e.getMessage().contains("Coordonnées (" + x + ", " + y + ", Présent) incorrectes"));
                    e = assertThrows(IllegalArgumentException.class, () -> p.estVide(x, y, Epoque.FUTUR));
                    assertTrue(e.getMessage().contains("Coordonnées (" + x + ", " + y + ", Futur) incorrectes"));
                }
            }
        }
    }

    private void assertContenuVide() {
        assertTrue(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuBlanc() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.BLANC));
        assertTrue(p.aBlanc(1, 1, Epoque.PRESENT));
        assertTrue(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuBlancEtGraine() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.BLANC));
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.GRAINE));
        assertTrue(p.aBlanc(1, 1, Epoque.PRESENT));
        assertTrue(p.aPion(1, 1, Epoque.PRESENT));
        assertTrue(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuNoir() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.NOIR));
        assertTrue(p.aNoir(1, 1, Epoque.PRESENT));
        assertTrue(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuNoirEtGraine() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.NOIR));
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.GRAINE));
        assertTrue(p.aNoir(1, 1, Epoque.PRESENT));
        assertTrue(p.aPion(1, 1, Epoque.PRESENT));
        assertTrue(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuGraine() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.GRAINE));
        assertTrue(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuArbuste() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.ARBUSTE));
        assertTrue(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuArbre() {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, Piece.ARBRE));
        assertTrue(p.aArbre(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbreCouche(1, 1, Epoque.PRESENT));
    }

    private void assertContenuArbreCouche(Piece piece) {
        assertTrue(p.aPiece(1, 1, Epoque.PRESENT, piece));
        assertTrue(p.aArbreCouche(1, 1, Epoque.PRESENT));
        assertFalse(p.estVide(1, 1, Epoque.PRESENT));
        assertFalse(p.aBlanc(1, 1, Epoque.PRESENT));
        assertFalse(p.aNoir(1, 1, Epoque.PRESENT));
        assertFalse(p.aPion(1, 1, Epoque.PRESENT));
        assertFalse(p.aGraine(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbuste(1, 1, Epoque.PRESENT));
        assertFalse(p.aArbre(1, 1, Epoque.PRESENT));

        switch (piece) {
            case ARBRE_COUCHE_HAUT:
                assertTrue(p.aArbreCoucheVersLeHaut(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLaDroite(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLeBas(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLaGauche(1, 1, Epoque.PRESENT));
                break;
            case ARBRE_COUCHE_DROITE:
                assertFalse(p.aArbreCoucheVersLeHaut(1, 1, Epoque.PRESENT));
                assertTrue(p.aArbreCoucheVersLaDroite(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLeBas(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLaGauche(1, 1, Epoque.PRESENT));
                break;
            case ARBRE_COUCHE_BAS:
                assertFalse(p.aArbreCoucheVersLeHaut(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLaDroite(1, 1, Epoque.PRESENT));
                assertTrue(p.aArbreCoucheVersLeBas(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLaGauche(1, 1, Epoque.PRESENT));
                break;
            case ARBRE_COUCHE_GAUCHE:
                assertFalse(p.aArbreCoucheVersLeHaut(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLaDroite(1, 1, Epoque.PRESENT));
                assertFalse(p.aArbreCoucheVersLeBas(1, 1, Epoque.PRESENT));
                assertTrue(p.aArbreCoucheVersLaGauche(1, 1, Epoque.PRESENT));
        }
    }

    private void exceptionAjouterContenu(Piece piece) {
        IllegalStateException e = assertThrows(
                IllegalStateException.class, () -> p.ajouter(1, 1, Epoque.PRESENT, piece)
        );
        assertTrue(e.getMessage().contains("Impossible d'ajouter la pièce " + piece + " sur la case (1, 1, Présent)"));
    }

    private void exceptionSupprimerContenu(Piece piece) {
        IllegalStateException e = assertThrows(
                IllegalStateException.class, () -> p.supprimer(1, 1, Epoque.PRESENT, piece)
        );
        assertTrue(e.getMessage().contains(
                "Impossible de supprimer la pièce " + piece + " de la case (1, 1, Présent) : pièce absente")
        );
    }

    private void assertExceptionsAjouterContenu() {
        exceptionAjouterContenu(Piece.BLANC);
        exceptionAjouterContenu(Piece.NOIR);
        exceptionAjouterContenu(Piece.GRAINE);
        exceptionAjouterContenu(Piece.ARBUSTE);
        exceptionAjouterContenu(Piece.ARBRE);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_HAUT);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_DROITE);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_BAS);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_GAUCHE);
    }

    private void assertExceptionsSupprimerContenu(Piece piece) {
        for (int i = 1; i <= 256; i *= 2) {
            if (i != piece.valeur()) {
                exceptionSupprimerContenu(Piece.depuisValeur(i));
            }
        }
    }

    @Test
    public void testContenuBlanc() {
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.BLANC);
        assertContenuBlanc();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertContenuBlancEtGraine();
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuBlancEtGraine();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuBlancEtGraine();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertContenuBlanc();
        assertExceptionsSupprimerContenu(Piece.BLANC);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.BLANC);
        assertContenuVide();
    }

    @Test
    public void testContenuNoir() {
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.NOIR);
        assertContenuNoir();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertContenuNoirEtGraine();
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuNoirEtGraine();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuNoirEtGraine();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertContenuNoir();
        assertExceptionsSupprimerContenu(Piece.NOIR);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.NOIR);
        assertContenuVide();
    }

    @Test
    public void testContenuGraine() {
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertContenuGraine();
        exceptionAjouterContenu(Piece.GRAINE);
        exceptionAjouterContenu(Piece.ARBUSTE);
        exceptionAjouterContenu(Piece.ARBRE);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_HAUT);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_DROITE);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_BAS);
        exceptionAjouterContenu(Piece.ARBRE_COUCHE_GAUCHE);
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuGraine();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuGraine();
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.BLANC);
        assertContenuBlancEtGraine();
        p.supprimer(1, 1, Epoque.PRESENT, Piece.BLANC);
        assertContenuGraine();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.NOIR);
        assertContenuNoirEtGraine();
        p.supprimer(1, 1, Epoque.PRESENT, Piece.NOIR);
        assertContenuGraine();
        assertExceptionsSupprimerContenu(Piece.GRAINE);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertContenuVide();
    }

    @Test
    public void testContenuArbuste() {
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        assertContenuArbuste();
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuArbuste();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuArbuste();
        assertExceptionsAjouterContenu();
        assertExceptionsSupprimerContenu(Piece.ARBUSTE);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        assertContenuVide();
    }

    @Test
    public void testContenuArbre() {
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE);
        assertContenuArbre();
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuArbre();
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuArbre();
        assertExceptionsAjouterContenu();
        assertExceptionsSupprimerContenu(Piece.ARBRE);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE);
        assertContenuVide();
    }

    @Test
    public void testContenuArbreCouche() {
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_HAUT);
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_HAUT);
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_HAUT);
        assertExceptionsAjouterContenu();
        assertExceptionsSupprimerContenu(Piece.ARBRE_COUCHE_HAUT);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_DROITE);
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_DROITE);
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_DROITE);
        assertExceptionsAjouterContenu();
        assertExceptionsSupprimerContenu(Piece.ARBRE_COUCHE_DROITE);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_BAS);
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_BAS);
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_BAS);
        assertExceptionsAjouterContenu();
        assertExceptionsSupprimerContenu(Piece.ARBRE_COUCHE_BAS);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        assertContenuVide();
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_GAUCHE);
        assertExceptionsAjouterContenu();
        p.ajouter(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_GAUCHE);
        assertExceptionsAjouterContenu();
        p.supprimer(1, 1, Epoque.PRESENT, null);
        assertContenuArbreCouche(Piece.ARBRE_COUCHE_GAUCHE);
        assertExceptionsAjouterContenu();
        assertExceptionsSupprimerContenu(Piece.ARBRE_COUCHE_GAUCHE);
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        assertContenuVide();
    }

    @Test
    public void testEstOccupable() {
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.GRAINE);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.BLANC);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.BLANC);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.NOIR);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.NOIR);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        assertFalse(p.estOccupable(1, 1, Epoque.PRESENT));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        assertTrue(p.estOccupable(1, 1, Epoque.PRESENT));
    }

    @Test
    public void testExceptionAObstacleMortel() {
        IllegalArgumentException e;
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, 0, 0));
        assertTrue(e.getMessage().contains("Déplacement incorrect : 0, 0"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, -1, 1));
        assertTrue(e.getMessage().contains("Déplacement incorrect : -1, 1"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, 1, -1));
        assertTrue(e.getMessage().contains("Déplacement incorrect : 1, -1"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, -1, -1));
        assertTrue(e.getMessage().contains("Déplacement incorrect : -1, -1"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, 1, 1));
        assertTrue(e.getMessage().contains("Déplacement incorrect : 1, 1"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, -2, 0));
        assertTrue(e.getMessage().contains("Déplacement incorrect : -2, 0"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, 0, 2));
        assertTrue(e.getMessage().contains("Déplacement incorrect : 0, 2"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, -2, 1));
        assertTrue(e.getMessage().contains("Déplacement incorrect : -2, 1"));
        e = assertThrows(IllegalArgumentException.class, () -> p.aObstacleMortel(0, 0, Epoque.PRESENT, -1, 2));
        assertTrue(e.getMessage().contains("Déplacement incorrect : -1, 2"));
    }

    @Test
    public void testAObstacleMortel() {
        assertTrue(p.aObstacleMortel(-1, 0, Epoque.PRESENT, 0, 1));
        assertTrue(p.aObstacleMortel(0, -1, Epoque.PRESENT, 0, 1));
        assertTrue(p.aObstacleMortel(Plateau.TAILLE, 0, Epoque.PRESENT, 0, 1));
        assertTrue(p.aObstacleMortel(0, Plateau.TAILLE, Epoque.PRESENT, 0, 1));

        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        assertTrue(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBUSTE);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        assertTrue(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_HAUT);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        assertTrue(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        assertTrue(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_BAS);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        assertTrue(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.supprimer(1, 1, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));

        p.ajouter(1, 1, Epoque.PRESENT, Piece.ARBRE);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBRE);
        assertFalse(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        assertFalse(p.aObstacleMortel(1, 2, Epoque.PRESENT, 0, 1));
        p.ajouter(1, 3, Epoque.PRESENT, Piece.ARBRE);
        assertTrue(p.aObstacleMortel(1, 1, Epoque.PRESENT, 0, 1));
        assertTrue(p.aObstacleMortel(1, 2, Epoque.PRESENT, 0, 1));
        assertTrue(p.aObstacleMortel(1, 3, Epoque.PRESENT, 0, 1));
    }

    @Test
    public void testNombrePlateauVide() {
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        assertEquals(1, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        p.supprimer(0, 0, Epoque.PRESENT, Piece.BLANC);
        assertEquals(2, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        p.supprimer(0, 0, Epoque.FUTUR, Piece.BLANC);
        assertEquals(3, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.NOIR));
        p.supprimer(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.FUTUR, Piece.NOIR);
        assertEquals(3, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(1, p.nombrePlateauVide(Pion.NOIR));
        p.supprimer(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.PRESENT, Piece.NOIR);
        assertEquals(3, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(2, p.nombrePlateauVide(Pion.NOIR));
        p.supprimer(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.PASSE, Piece.NOIR);
        assertEquals(3, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(3, p.nombrePlateauVide(Pion.NOIR));
        p.ajouter(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.PASSE, Piece.NOIR);
        assertEquals(2, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(2, p.nombrePlateauVide(Pion.BLANC));
        p.ajouter(0, 0, Epoque.PRESENT, Piece.BLANC);
        p.ajouter(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.FUTUR, Piece.NOIR);
        assertEquals(1, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(1, p.nombrePlateauVide(Pion.BLANC));
        p.ajouter(0, 0, Epoque.FUTUR, Piece.BLANC);
        p.ajouter(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.PRESENT, Piece.NOIR);
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
        assertEquals(0, p.nombrePlateauVide(Pion.BLANC));
    }

    @Test
    public void testNombrePionPlateau() {
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        assertEquals(0, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.supprimer(Plateau.TAILLE-1, Plateau.TAILLE-1, Epoque.PASSE, Piece.NOIR);
        assertEquals(0, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(0, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(0, 0, Epoque.PASSE, Piece.BLANC);
        p.ajouter(1, 0, Epoque.PASSE, Piece.NOIR);
        assertEquals(1, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(0, 1, Epoque.PASSE, Piece.BLANC);
        assertEquals(2, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(0, 2, Epoque.PASSE, Piece.BLANC);
        assertEquals(3, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(0, 3, Epoque.PASSE, Piece.BLANC);
        assertEquals(4, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(1, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(1, 1, Epoque.PASSE, Piece.NOIR);
        assertEquals(4, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(2, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(1, 2, Epoque.PASSE, Piece.NOIR);
        assertEquals(4, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(3, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
        p.ajouter(1, 3, Epoque.PASSE, Piece.NOIR);
        assertEquals(4, p.nombrePionPlateau(Pion.BLANC, Epoque.PASSE));
        assertEquals(4, p.nombrePionPlateau(Pion.NOIR, Epoque.PASSE));
    }

    @Test
    public void testNombreGraineReserve() {
        assertEquals(5, p.nombreGrainesReserve());
        p.ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
        assertEquals(4, p.nombreGrainesReserve());
        p.supprimer(0, 0, Epoque.PASSE, Piece.GRAINE);
        assertEquals(5, p.nombreGrainesReserve());
        p.ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
        assertEquals(4, p.nombreGrainesReserve());
        p.ajouter(0, 1, Epoque.PASSE, Piece.GRAINE);
        assertEquals(3, p.nombreGrainesReserve());
        p.ajouter(0, 2, Epoque.PASSE, Piece.GRAINE);
        assertEquals(2, p.nombreGrainesReserve());
        p.ajouter(0, 3, Epoque.PASSE, Piece.GRAINE);
        assertEquals(1, p.nombreGrainesReserve());
        p.ajouter(1, 1, Epoque.PASSE, Piece.GRAINE);
        assertEquals(0, p.nombreGrainesReserve());
        p.supprimer(0, 0, Epoque.PASSE, Piece.GRAINE);
        assertEquals(1, p.nombreGrainesReserve());
        p.supprimer(0, 1, Epoque.PASSE, Piece.GRAINE);
        assertEquals(2, p.nombreGrainesReserve());
        p.supprimer(0, 2, Epoque.PASSE, Piece.GRAINE);
        assertEquals(3, p.nombreGrainesReserve());
        p.supprimer(0, 3, Epoque.PASSE, Piece.GRAINE);
        assertEquals(4, p.nombreGrainesReserve());
        p.supprimer(1, 1, Epoque.PASSE, Piece.GRAINE);
        assertEquals(5, p.nombreGrainesReserve());
    }

    @Test
    public void testExceptionAjouterGraineReserve() {
        for (int i = 0; i < Plateau.NOMBRE_MAX_GRAINES; i++) {
            p.ajouter(0, i, Epoque.PASSE, Piece.GRAINE);
        }
        p.ajouter(1, 0, Epoque.PASSE, Piece.GRAINE);
        IllegalStateException e = assertThrows(
                IllegalStateException.class,
                () -> p.ajouter(1, 1, Epoque.PASSE, Piece.GRAINE)
        );
        assertTrue(e.getMessage().contains("Impossible d'ajouter une graine : nombre maximal atteint"));
    }

    @Test
    public void testCopier() {
        p.supprimer(0, 0, Epoque.PASSE, Piece.BLANC);
        p.supprimer(Plateau.TAILLE - 1, Plateau.TAILLE - 1, Epoque.FUTUR, Piece.NOIR);
        p.ajouter(0, 0, Epoque.PASSE, Piece.GRAINE);
        p.ajouter(1, 2, Epoque.PRESENT, Piece.ARBUSTE);
        p.ajouter(2, 1, Epoque.PRESENT, Piece.ARBRE);
        p.ajouter(2, 2, Epoque.PASSE, Piece.ARBRE_COUCHE_HAUT);
        p.ajouter(2, 2, Epoque.PRESENT, Piece.ARBRE_COUCHE_DROITE);
        p.ajouter(3, 3, Epoque.FUTUR, Piece.ARBRE_COUCHE_BAS);
        p.ajouter(2, 3, Epoque.PRESENT, Piece.ARBRE_COUCHE_GAUCHE);

        Plateau copie = p.copier();

        for (int i = 0; i < Epoque.NOMBRE; i++) {
            Epoque e = Epoque.depuisIndice(i);

            for (int j = 0; j < Plateau.TAILLE; j++) {
                for (int k = 0; k < Plateau.TAILLE; k++) {
                    if (p.estVide(j, k, e)) {
                        assertTrue(copie.estVide(j, k, e));
                    } else {
                        assertFalse(copie.estVide(j, k, e));
                    }
                    if (p.aBlanc(j, k, e)) {
                        assertTrue(copie.aBlanc(j, k, e));
                    } else {
                        assertFalse(copie.aBlanc(j, k, e));
                    }
                    if (p.aNoir(j, k, e)) {
                        assertTrue(copie.aNoir(j, k, e));
                    } else {
                        assertFalse(copie.aNoir(j, k, e));
                    }
                    if (p.aPion(j, k, e)) {
                        assertTrue(copie.aPion(j, k, e));
                    } else {
                        assertFalse(copie.aPion(j, k, e));
                    }
                    if (p.aGraine(j, k, e)) {
                        assertTrue(copie.aGraine(j, k, e));
                    } else {
                        assertFalse(copie.aGraine(j, k, e));
                    }
                    if (p.aArbuste(j, k, e)) {
                        assertTrue(copie.aArbuste(j, k, e));
                    } else {
                        assertFalse(copie.aArbuste(j, k, e));
                    }
                    if (p.aArbre(j, k, e)) {
                        assertTrue(copie.aArbre(j, k, e));
                    } else {
                        assertFalse(copie.aArbre(j, k, e));
                    }
                    if (p.aArbreCoucheVersLeHaut(j, k, e)) {
                        assertTrue(copie.aArbreCoucheVersLeHaut(j, k, e));
                    } else {
                        assertFalse(copie.aArbreCoucheVersLeHaut(j, k, e));
                    }
                    if (p.aArbreCoucheVersLaDroite(j, k, e)) {
                        assertTrue(copie.aArbreCoucheVersLaDroite(j, k, e));
                    } else {
                        assertFalse(copie.aArbreCoucheVersLaDroite(j, k, e));
                    }
                    if (p.aArbreCoucheVersLeBas(j, k, e)) {
                        assertTrue(copie.aArbreCoucheVersLeBas(j, k, e));
                    } else {
                        assertFalse(copie.aArbreCoucheVersLeBas(j, k, e));
                    }
                    if (p.aArbreCoucheVersLaGauche(j, k, e)) {
                        assertTrue(copie.aArbreCoucheVersLaGauche(j, k, e));
                    } else {
                        assertFalse(copie.aArbreCoucheVersLaGauche(j, k, e));
                    }
                    if (p.aArbreCouche(j, k, e)) {
                        assertTrue(copie.aArbreCouche(j, k, e));
                    } else {
                        assertFalse(copie.aArbreCouche(j, k, e));
                    }
                    if (p.estOccupable(j, k, e)) {
                        assertTrue(copie.estOccupable(j, k, e));
                    } else {
                        assertFalse(copie.estOccupable(j, k, e));
                    }
                    if (p.aObstacleMortel(j, k, e, -1, 0)) {
                        assertTrue(copie.aObstacleMortel(j, k, e, -1, 0));
                    } else {
                        assertFalse(copie.aObstacleMortel(j, k, e, -1, 0));
                    }
                    if (p.aObstacleMortel(j, k, e, 0, 1)) {
                        assertTrue(copie.aObstacleMortel(j, k, e, 0, 1));
                    } else {
                        assertFalse(copie.aObstacleMortel(j, k, e, 0, 1));
                    }
                    if (p.aObstacleMortel(j, k, e, 1, 0)) {
                        assertTrue(copie.aObstacleMortel(j, k, e, 1, 0));
                    } else {
                        assertFalse(copie.aObstacleMortel(j, k, e, 1, 0));
                    }
                    if (p.aObstacleMortel(j, k, e, 0, -1)) {
                        assertTrue(copie.aObstacleMortel(j, k, e, 0, -1));
                    } else {
                        assertFalse(copie.aObstacleMortel(j, k, e, 0, -1));
                    }
                }
            }
            assertEquals(p.nombrePionPlateau(Pion.BLANC, e), copie.nombrePionPlateau(Pion.BLANC, e));
            assertEquals(p.nombrePionPlateau(Pion.NOIR, e), copie.nombrePionPlateau(Pion.NOIR, e));
        }
        assertEquals(p.nombrePlateauVide(Pion.BLANC), copie.nombrePlateauVide(Pion.BLANC));
        assertEquals(p.nombrePlateauVide(Pion.NOIR), copie.nombrePlateauVide(Pion.NOIR));

        assertEquals(p.nombreGrainesReserve(), copie.nombreGrainesReserve());
    }

    @Test
    public void testExceptionsArgumentNull() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> p.aPiece(0, 0, null, Piece.BLANC));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));
        e = assertThrows(NullPointerException.class, () -> p.aPiece(0, 0, Epoque.PRESENT, null));
        assertTrue(e.getMessage().contains("La pièce p ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.ajouter(0, 0, null, Piece.BLANC));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.supprimer(0, 0, null, Piece.BLANC));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.estVide(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aBlanc(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aNoir(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aPion(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aGraine(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbuste(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbre(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbreCoucheVersLeHaut(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbreCoucheVersLaDroite(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbreCoucheVersLeBas(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbreCoucheVersLaGauche(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aArbreCouche(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.estOccupable(0, 0, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.aObstacleMortel(0, 0, null, 0, 1));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.nombrePlateauVide(null));
        assertTrue(e.getMessage().contains("Le pion p ne doit pas être null"));

        e = assertThrows(NullPointerException.class, () -> p.nombrePionPlateau(null, Epoque.PRESENT));
        assertTrue(e.getMessage().contains("Le pion p ne doit pas être null"));
        e = assertThrows(NullPointerException.class, () -> p.nombrePionPlateau(Pion.BLANC, null));
        assertTrue(e.getMessage().contains("L'époque e ne doit pas être null"));
    }
}
