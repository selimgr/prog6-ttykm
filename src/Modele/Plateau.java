package Modele;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

public class Plateau {
    private int[][][] contenu;
    private int[] nbBlancParPlateau;
    private int[] nbNoirParPlateau;
    private int nombrePlateauVideBlanc;
    private int nombrePlateauVideNoir;
    private int nombreGrainesReserve;
    public static final int TAILLE = 4;

    Plateau() {
        contenu = new int[Epoque.NOMBRE][TAILLE][TAILLE];
        nbBlancParPlateau = new int[Epoque.NOMBRE];
        nbNoirParPlateau = new int[Epoque.NOMBRE];

        // ajout des pions dans les coins
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            contenu[i][0][0] = Piece.BLANC.valeur();
            contenu[i][TAILLE - 1][TAILLE - 1] = Piece.NOIR.valeur();
            nbBlancParPlateau[i] = 1;
            nbNoirParPlateau[i] = 1;
        }
    }

    public void fixerPlateau(Plateau p){
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                for (int k = 0; k < TAILLE; k++) {
                    contenu[i][j][k] = p.contenu(j, k, Epoque.depuisIndice(i));
                }
            }
        }
    }

    public boolean aMur(int l, int c) {
        return Math.min(l, c) < 0 && Math.max(l, c) >= TAILLE;
    }

    private void verifierCoordoneesCorrectes(int l, int c, Epoque e) {
        if (aMur(l, c) || e == null) {
            throw new IllegalArgumentException("Coordonnées (" + l + ", " + c + ", " + e + ") incorrectes");
        }
    }

    int contenu(int l, int c, Epoque e) {
        verifierCoordoneesCorrectes(l, c, e);
        return contenu[e.indice()][l][c];
    }

    boolean aPiece(int l, int c, Epoque e, Piece p) {
        return (contenu(l, c, e) & p.valeur()) != 0;
    }

    public boolean estVide(int l, int c, Epoque e) {
        return contenu(l, c, e) == 0;
    }

    public boolean aBlanc(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.BLANC);
    }

    public boolean aNoir(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.NOIR);
    }

    public boolean aPion(int l, int c, Epoque e) {
        return aBlanc(l, c, e) || aNoir(l, c, e);
    }

    public boolean aGraine(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.GRAINE);
    }

    public boolean aArbuste(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBUSTE);
    }

    public boolean aArbre(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE);
    }

    public boolean aArbreCouche(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE_COUCHE_HAUT) || aPiece(l, c, e, Piece.ARBRE_COUCHE_DROITE) ||
                aPiece(l, c, e, Piece.ARBRE_COUCHE_BAS) || aPiece(l, c, e, Piece.ARBRE_COUCHE_GAUCHE);
    }

    public boolean estOccupable(int l, int c, Epoque e) {
        return estVide(l, c, e) || aGraine(l, c, e);
    }

    public boolean aObstacleMortel(int l, int c, Epoque e, int dL, int dC) {
        if (!Mouvement.estDeplacement(dL, dC, 0)) {
            throw new IllegalArgumentException("Déplacement incorrect : " + dL + ", " + dC);
        }

        if (aMur(l, c) || aArbuste(l, c, e) || aArbreCouche(l, c, e)) {
            return true;
        }
        if (aArbre(l, c, e)) {
            return aObstacleMortel(l + dL, c + dC, e, dL, dC);
        }
        return false;
    }

    void fixerCase(int l, int c, Epoque e, int contenu) {
        this.contenu[e.indice()][l][c] = contenu;
    }

    int ajout(int l, int c, Epoque e, Piece p) {
        if (aPiece(l, c, e, p) || (!estOccupable(l, c, e))) {
            throw new IllegalStateException(
                    "Impossible d'ajouter la pièce " + p + " sur la case (" + l + ", " + c + ", " + e + ")"
            );
        }
        return contenu(l, c, e) | p.valeur();
    }

    int suppression(int l, int c, Epoque e, Piece p) {
        if (!aPiece(l, c, e, p)) {
            throw new IllegalStateException(
                    "Impossible de supprimer la pièce " + p + " de la case (" + l + ", " + c + ", " + e + ") : pièce absente"
            );
        }
        return contenu(l, c, e) & ~p.valeur();
    }

    public int nombrePlateauVide(Pion p) {
        if (p == Pion.BLANC) {
            return nombrePlateauVideBlanc;
        }
        return nombrePlateauVideNoir;
    }

    public int nombrePionPlateau(Pion pion, Epoque e) {
        if (pion == Pion.BLANC) {
            return nbBlancParPlateau[e.indice()];
        }
        return nbNoirParPlateau[e.indice()];
    }

    void modifierNombrePionPlateau(Piece pion, Epoque e, int nombre) {
        if (nombre == 0) {
            throw new IllegalArgumentException("Nombre ne doit pas être nul");
        }
        int nombreApres;

        switch (pion) {
            case BLANC:
                nbBlancParPlateau[e.indice()] += nombre;
                nombreApres = nbBlancParPlateau[e.indice()];

                if (nombreApres < 0) {
                    throw new IllegalArgumentException("Impossible d'enlever " + nombre + " pions blancs du plateau " + e);
                } else if (nombreApres == nombre) {
                    nombrePlateauVideBlanc--;
                } else if (nombreApres == 0) {
                    nombrePlateauVideBlanc++;
                }
                break;

            case NOIR:
                nbNoirParPlateau[e.indice()] += nombre;
                nombreApres = nbNoirParPlateau[e.indice()];

                if (nombreApres < 0) {
                    throw new IllegalArgumentException("Impossible d'enlever " + nombre + " pions noirs du plateau " + e);
                } else if (nombreApres == nombre) {
                    nombrePlateauVideNoir--;
                } else if (nombreApres == 0) {
                    nombrePlateauVideNoir++;
                }
                break;

            default:
                throw new IllegalArgumentException("Mauvaise pièce en paramètre");
        }
    }

    public int nombreGrainesReserve() {
        return nombreGrainesReserve;
    }

    void ajouterGraineReserve() {
        nombreGrainesReserve++;
    }

    void enleverGraineReserve() {
        if (nombreGrainesReserve == 0) {
            throw new IllegalStateException("Impossible d'enlever une graine : aucune graine en réserve");
        }
        nombreGrainesReserve--;
    }

    private List<Case> chercherPions(Joueur j, Epoque e){
        ArrayList<Case> cases = new ArrayList<>();
        Epoque e2 =e;
        Piece p = Piece.depuisValeur(j.pions().valeur());
        for (int l2 = 0; l2 < Plateau.TAILLE;l2++) {
            for (int c2 = 0; c2 < Plateau.TAILLE; c2++) {
                if (aPiece(l2,c2,e2,p)) cases.add(new Case(l2,c2,e2));
            }
        }
        return new ArrayList<Case>();
    }

    // TODO : Implémenter casesJouables
    public ArrayList<Coup> casesJouablesEpoque(Joueur j, boolean sel,  int l, int c, Epoque e){
        ArrayList<Coup> jouables = new ArrayList<>();
        List<Case> pions;
        if (!sel)  pions = chercherPions(j,j.focus());
        else { pions = new ArrayList<>(); pions.add(new Case(l,c,e));}
        gestionCoupsJouables(j, jouables, pions);
        return jouables;
    }

    public int coupJouables(Joueur j){
        ArrayList<Coup> jouables = new ArrayList<>();
        List<Case> pions;
        pions = chercherPions(j,Epoque.PASSE);
        pions = chercherPions(j,Epoque.PRESENT);
        pions = chercherPions(j,Epoque.FUTUR);
        gestionCoupsJouables(j, jouables, pions);
        return jouables.size();
    }

    private  ArrayList<Coup> gestionCoupsJouables(Joueur j, ArrayList<Coup> jouables,List<Case> pions){
        Iterator<Case> it  = pions.iterator();
        while(it.hasNext()){
            Case cas = it.next();
            Epoque eActu = cas.epoque();
            // Mouvement possible :
            Coup coup = new Mouvement(this,j,cas.ligne(),cas.colonne(),eActu);
            if (coup.creer(1,0,eActu)) jouables.add(coup);
            if (coup.creer(-1,0,eActu)) jouables.add(coup);
            if (coup.creer(0,1,eActu)) jouables.add(coup);
            if (coup.creer(0,-1,eActu)) jouables.add(coup);
            if (coup.creer(0,0,Epoque.FUTUR)) jouables.add(coup);
            if (coup.creer(1,0,Epoque.PRESENT)) jouables.add(coup);
            if (coup.creer(0,0,Epoque.PASSE)) jouables.add(coup);
        }
        return jouables;
    }


    // Utile pour l'IA
    public Plateau copier() {
        Plateau retour = new Plateau();

        for (int i = 0; i < Epoque.NOMBRE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                for (int k = 0; k < TAILLE; k++) {
                    retour.contenu[i][j][k] = this.contenu(j, k, Epoque.depuisIndice(i));
                }
            }
        }
        return retour;
    }

    public String hash() {
        String plateauStr = new String();
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                for (int k = 0; k < TAILLE; k++) {
                    plateauStr += this.contenu(j, k, Epoque.depuisIndice(i)) + " ";
                }
            }
        }
        return plateauStr;
    }
}
