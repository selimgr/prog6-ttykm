package Modele;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import static java.util.Objects.requireNonNull;

public class Plateau implements Serializable {
    private final int[][][] contenu;
    private final int[] nbBlancParPlateau;
    private final int[] nbNoirParPlateau;
    private int nombrePlateauVideBlanc;
    private int nombrePlateauVideNoir;
    private int nombreGrainesReserve;

    public static final int TAILLE = 4;
    public static final int NOMBRE_MAX_GRAINES = 5;

    Plateau() {
        contenu = new int[Epoque.NOMBRE][TAILLE][TAILLE];

        nbBlancParPlateau = new int[Epoque.NOMBRE];
        nbNoirParPlateau = new int[Epoque.NOMBRE];

        // ajout des pions dans les coins
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            ajouter(0, 0, Epoque.depuisIndice(i), Piece.BLANC);
            ajouter(TAILLE - 1, TAILLE - 1, Epoque.depuisIndice(i), Piece.NOIR);
        }
        nombrePlateauVideBlanc = 0;
        nombrePlateauVideNoir = 0;
        nombreGrainesReserve = NOMBRE_MAX_GRAINES;
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

    static boolean aMur(int l, int c) {
        return Math.min(l, c) < 0 || Math.max(l, c) >= TAILLE;
    }

    static void verifierCoordoneesCorrectes(int l, int c, Epoque e) {
        requireNonNull(e, "L'époque e ne doit pas être null");

        if (aMur(l, c)) {
            throw new IllegalArgumentException("Coordonnées (" + l + ", " + c + ", " + e + ") incorrectes");
        }
    }

    private int contenu(int l, int c, Epoque e) {
        verifierCoordoneesCorrectes(l, c, e);
        return contenu[e.indice()][l][c];
    }

    boolean aPiece(int l, int c, Epoque e, Piece p) {
        requireNonNull(p, "La pièce p ne doit pas être null");
        return (contenu(l, c, e) & p.valeur()) != 0;
    }

    void ajouter(int l, int c, Epoque e, Piece p) {
        requireNonNull(e, "L'époque e ne doit pas être null");

        if (p == null) {
            return;
        }
        if (!estVide(l, c, e) && (p != Piece.GRAINE || !aPion(l, c, e) || aGraine(l, c, e)) &&
                (p.toPion() == null || !aGraine(l, c, e) || aPion(l, c, e))) {
            throw new IllegalStateException(
                    "Impossible d'ajouter la pièce " + p + " sur la case (" + l + ", " + c + ", " + e + ")"
            );
        }
        contenu[e.indice()][l][c] = contenu(l, c, e) | p.valeur();

        if (p == Piece.BLANC || p == Piece.NOIR) {
            ajouterPionPlateau(p.toPion(), e);
        } else if (p == Piece.GRAINE) {
            enleverGraineReserve();
        }
    }

    void supprimer(int l, int c, Epoque e, Piece p) {
        requireNonNull(e, "L'époque e ne doit pas être null");

        if (p == null) {
            return;
        }
        if (!aPiece(l, c, e, p)) {
            throw new IllegalStateException(
                    "Impossible de supprimer la pièce " + p + " de la case (" + l + ", " + c + ", " + e + ") : pièce absente"
            );
        }
        contenu[e.indice()][l][c] = contenu(l, c, e) & ~p.valeur();

        if (p == Piece.BLANC || p == Piece.NOIR) {
            supprimerPionPlateau(p.toPion(), e);
        } else if (p == Piece.GRAINE) {
            ajouterGraineReserve();
        }
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

    public boolean aArbreCoucheVersLeHaut(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE_COUCHE_HAUT);
    }

    public boolean aArbreCoucheVersLaDroite(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE_COUCHE_DROITE);
    }

    public boolean aArbreCoucheVersLeBas(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE_COUCHE_BAS);
    }

    public boolean aArbreCoucheVersLaGauche(int l, int c, Epoque e) {
        return aPiece(l, c, e, Piece.ARBRE_COUCHE_GAUCHE);
    }

    public boolean aArbreCouche(int l, int c, Epoque e) {
        return aArbreCoucheVersLeHaut(l, c, e) || aArbreCoucheVersLaDroite(l, c, e) ||
                aArbreCoucheVersLeBas(l, c, e) || aArbreCoucheVersLaGauche(l, c, e);
    }

    public boolean estOccupable(int l, int c, Epoque e) {
        return estVide(l, c, e) || contenu(l, c, e) == Piece.GRAINE.valeur();
    }

    public boolean aObstacleMortel(int l, int c, Epoque e, int dL, int dC) {
        if (Math.abs(dL) + Math.abs(dC) >= 2 || dL + dC == 0) {
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

    public int nombrePlateauVide(Pion p) {
        requireNonNull(p, "Le pion p ne doit pas être null");

        if (p == Pion.BLANC) {
            return nombrePlateauVideBlanc;
        }
        return nombrePlateauVideNoir;
    }

    int nombrePionPlateau(Pion p, Epoque e) {
        requireNonNull(p, "Le pion p ne doit pas être null");
        requireNonNull(e, "L'époque e ne doit pas être null");

        if (p == Pion.BLANC) {
            return nbBlancParPlateau[e.indice()];
        }
        return nbNoirParPlateau[e.indice()];
    }

    private void ajouterPionPlateau(Pion p, Epoque e) {
        if (p == Pion.BLANC) {
            if (nombrePionPlateau(p, e) == 0) {
                nombrePlateauVideBlanc--;
            }
            nbBlancParPlateau[e.indice()]++;
        } else {
            if (nombrePionPlateau(p, e) == 0) {
                nombrePlateauVideNoir--;
            }
            nbNoirParPlateau[e.indice()]++;
        }
    }

    private void supprimerPionPlateau(Pion p, Epoque e) {
        if (p == Pion.BLANC) {
            nbBlancParPlateau[e.indice()]--;

            if (nombrePionPlateau(p, e) == 0) {
                nombrePlateauVideBlanc++;
            }
        } else {
            nbNoirParPlateau[e.indice()]--;

            if (nombrePionPlateau(p, e) == 0) {
                nombrePlateauVideNoir++;
            }
        }
    }

    public int nombreGrainesReserve() {
        return nombreGrainesReserve;
    }

    private void ajouterGraineReserve() {
        if (nombreGrainesReserve == NOMBRE_MAX_GRAINES) {
            throw new IllegalStateException("Impossible d'ajouter une graine : nombre maximal atteint");
        }
        nombreGrainesReserve++;
    }

    private void enleverGraineReserve() {
        if (nombreGrainesReserve == 0) {
            throw new IllegalStateException("Impossible d'enlever une graine : aucune graine en réserve");
        }
        nombreGrainesReserve--;
    }

    public List<Case> chercherPions(Joueur j, Epoque e){
        ArrayList<Case> cases = new ArrayList<>();

        Piece p = Piece.depuisValeur(j.pions().valeur());
        for (int l2 = 0; l2 < Plateau.TAILLE;l2++) {
            for (int c2 = 0; c2 < Plateau.TAILLE; c2++) {
                if (aPiece(l2,c2,e,p)) cases.add(new Case(l2,c2,e));
            }
        }
        return cases;
    }
    public ArrayList<Coup> casesJouablesEpoque(Joueur j, boolean sel,  int l, int c, Epoque e){
        ArrayList<Coup> jouables = new ArrayList<>();
        List<Case> pions;
        if (!sel)  {
            pions = chercherPions(j,j.focus());
        }
        else { pions = new ArrayList<>(); pions.add(new Case(l,c,e));}
        return gestionCoupsJouables(j, jouables, pions,sel);
    }

    public int coupJouables(Joueur j){
        ArrayList<Coup> jouables = new ArrayList<>();
        List<Case> pions;
        pions = chercherPions(j,Epoque.PASSE);
        jouables.addAll(gestionCoupsJouables(j, jouables, pions,false));
        pions = chercherPions(j,Epoque.PRESENT);
        jouables.addAll(gestionCoupsJouables(j, jouables, pions,false));
        pions = chercherPions(j,Epoque.FUTUR);
        jouables.addAll(gestionCoupsJouables(j, jouables, pions,false));
        return jouables.size();
    }

    private ArrayList<Coup> gestionCoupsJouables(Joueur j, ArrayList<Coup> jouables,List<Case> pions,boolean sel){
        Iterator<Case> it  = pions.iterator();
        ArrayList<Coup> coup = new ArrayList<>();
        int i =0;
        int i2 =0;

        while(it.hasNext()){
            Case cas = it.next();
            Epoque eActu = cas.epoque();
            // Mouvement possible :
            for (int k = 0; k <7 ;k++) {
                coup.add(new Mouvement(this, j, cas.ligne(), cas.colonne(), eActu));
            }
            if (coup.get(i).creer(cas.ligne()+1, cas.colonne(), eActu)) {
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            if (coup.get(i).creer(cas.ligne()-1,cas.colonne(),eActu)){
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            if (coup.get(i).creer(cas.ligne(), cas.colonne()+1, eActu)){
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            if (coup.get(i).creer(cas.ligne(), cas.colonne()-1,eActu)){
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            if (coup.get(i).creer(cas.ligne(), cas.colonne(), Epoque.FUTUR)){
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            if (coup.get(i).creer(cas.ligne(), cas.colonne(), Epoque.PRESENT)){
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            if (coup.get(i).creer(cas.ligne(), cas.colonne(), Epoque.PASSE)){
                jouables.add(coup.get(i));
                i2++;
            }
            i++;
            // TODO : Ajouter action sur les graines
            // ...
        }
        return jouables;

    }


    // Utile pour l'IA
    public Plateau copier() {
        Plateau retour = new Plateau();

        for (int i = 0; i < Epoque.NOMBRE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                System.arraycopy(contenu[i][j], 0, retour.contenu[i][j], 0, TAILLE);
            }
            retour.nbBlancParPlateau[i] = nbBlancParPlateau[i];
            retour.nbNoirParPlateau[i] = nbNoirParPlateau[i];
        }
        retour.nombrePlateauVideBlanc = nombrePlateauVideBlanc;
        retour.nombrePlateauVideNoir = nombrePlateauVideNoir;
        retour.nombreGrainesReserve = nombreGrainesReserve;
        return retour;
    }

    public String hash() {
        String plateauStr = "";
        for (int i = 0; i < Epoque.NOMBRE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                for (int k = 0; k < TAILLE; k++) {
                    plateauStr += this.contenu(j, k, Epoque.depuisIndice(i)) + " ";
                }
            }
        }
        return plateauStr;
    }

    public String hash2() {
        StringBuilder plateauStr = new StringBuilder();
        for (int lig = 0; lig < TAILLE; lig++){
            for (int ep =0; ep < Epoque.NOMBRE; ep++){
                for(int col = 0; col < TAILLE; col++){
                    plateauStr.append(this.contenu(lig, col, Epoque.depuisIndice(ep))).append(" ");
                }
                plateauStr.append("     ");
            }
            plateauStr.append("\n");
        }
        return plateauStr.toString();
    }
}
