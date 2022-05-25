package Modele;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TestHistorique {
    Historique h;

    @Before
    public void initialisation() {
        h = new Historique();
    }

    @Test
    public void testInitialisation() {
        NullPointerException e1 = assertThrows(NullPointerException.class, h::peutAnnuler);
        assertTrue(e1.getMessage().contains("Le tour actuel ne doit pas être null"));
        e1 = assertThrows(NullPointerException.class, h::peutRefaire);
        assertTrue(e1.getMessage().contains("Le tour actuel ne doit pas être null"));
        e1 = assertThrows(NullPointerException.class, h::tourActuel);
        assertTrue(e1.getMessage().contains("Le tour actuel ne doit pas être null"));
        NoSuchElementException e2 = assertThrows(NoSuchElementException.class, h::tourPrecedent);
        assertTrue(e2.getMessage().contains("Impossible de revenir au tour précédent : aucun tour précédent"));
        e2 = assertThrows(NoSuchElementException.class, h::tourSuivant);
        assertTrue(e2.getMessage().contains("Impossible de rétablir le tour suivant : aucun tour suivant"));

        Tour t = h.nouveauTour(Epoque.PASSE);
        assertFalse(h.peutAnnuler());
        assertFalse(h.peutRefaire());
        assertEquals(t, h.tourActuel());
        e2 = assertThrows(NoSuchElementException.class, h::tourPrecedent);
        assertTrue(e2.getMessage().contains("Impossible de revenir au tour précédent : aucun tour précédent"));
        e2 = assertThrows(NoSuchElementException.class, h::tourSuivant);
        assertTrue(e2.getMessage().contains("Impossible de rétablir le tour suivant : aucun tour suivant"));
    }

    @Test
    public void testHistorique() {
        Tour t1 = h.nouveauTour(Epoque.PASSE);
        Tour t2 = h.nouveauTour(Epoque.PRESENT);
        Tour t3 = h.nouveauTour(Epoque.FUTUR);

        assertEquals(t3, h.tourActuel());
        assertTrue(h.peutAnnuler());
        assertFalse(h.peutRefaire());
        Tour t = h.tourPrecedent();
        assertEquals(t2, t);
        assertTrue(h.peutAnnuler());
        assertTrue(h.peutRefaire());
        t = h.tourPrecedent();
        assertEquals(t1, t);
        assertFalse(h.peutAnnuler());
        assertTrue(h.peutRefaire());
        t = h.tourSuivant();
        assertEquals(t2, t);
        assertTrue(h.peutAnnuler());
        assertTrue(h.peutRefaire());
        t = h.tourSuivant();
        assertEquals(t3, t);
        assertTrue(h.peutAnnuler());
        assertFalse(h.peutRefaire());
    }
}
