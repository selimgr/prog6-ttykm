package Modele;

class NiveauASupprimer {
    static final int NOMBRE_PLATEAUX = 3;
    Epoque[] plateaux;

    NiveauASupprimer() {
        plateaux = new Epoque[NOMBRE_PLATEAUX];

        for (int i = 0; i < NOMBRE_PLATEAUX; i++) {
            plateaux[i] = new Epoque();
        }
    }

    Coup jouerCoup(Case depart, Case arrivee) {
        return null;
    }

    void annulerCoup(Coup coup) {

    }

    Epoque plateau(int typePlateau) {
        return plateaux[typePlateau];
    }

//    List<Coup> addCoups(int i, int j, int typePlateau,Joueur J ,Plateau pla, List<Coup> Coups) {
//        Case dep = new Case(i,j,p,pla.contenu(i,j));
//        // BAS
//        Case arr1 = new Case(i+1,j,p,pla.contenu(i+1,j));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr1));
//        //HAUT
//        Case arr2 = new Case(i-1,j,p,pla.contenu(i-1,j));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr1));
//        //DROITE
//        Case arr3 = new Case(i,j+1,p,pla.contenu(i,j+1));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr3));
//        //GAUCHE
//        Case arr4 = new Case(i,j-1,p,pla.contenu(i,j-1));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
//        //PASSE
//        Case arr5 = new Case(i,j,TypePlateau.PASSE,plateaux[PASSE].contenu(i,j));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
//        //PRESENT
//        Case arr6 = new Case(i,j,TypePlateau.PRESENT,plateaux[PRESENT].contenu(i,j));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
//        //FUTUR
//        Case arr7 = new Case(i,j,TypePlateau.FUTUR,plateaux[FUTUR].contenu(i,j));
//        if (estCoupLegal(dep,arr1,J)) Coups.add(new Coup(dep,arr4));
//
//        return Coups;
//    }
//    List<Coup> coupsLegaux(Joueur J, TypePlateau p) {
//        Plateau pla = this.plateau(p);
//        List<Coup> Coups = new ArrayList<>();
//        switch (J.pions()) {
//            case BLANC:
//                for(int i =0; i < 4; i++){
//                    for (int j = 0; j< 4; j++){
//                        if (pla.aBlanc(i,j)) {
//                            Coups = addCoups(i,j,p,J,pla,Coups);
//                        }
//                    }
//                }
//                break;
//            case NOIR:
//                for(int i =0; i < 4; i++){
//                    for (int j = 0; j< 4; j++){
//                        if (pla.aNoir(i,j)) {
//                            Coups = addCoups(i,j,p,J,pla,Coups);
//                        }
//                    }
//                }
//                break;
//            }
//        return Coups;
//    }

    int contenu(Case c) {
        return plateau(c.typePlateau()).contenu(c.ligne(), c.colonne());
    }

    public boolean estVide(Case c) {
        return plateau(c.typePlateau()).estVide(c.ligne(), c.colonne());
    }

    public boolean aBlanc(Case c) {
        return plateau(c.typePlateau()).aBlanc(c.ligne(), c.colonne());
    }

    public boolean aNoir(Case c) {
        return plateau(c.typePlateau()).aNoir(c.ligne(), c.colonne());
    }

    public boolean aPion(Case c) {
        return plateau(c.typePlateau()).aPion(c.ligne(), c.colonne());
    }

    public boolean aGraine(Case c) {
        return plateau(c.typePlateau()).aGraine(c.ligne(), c.colonne());
    }

    public boolean aArbuste(Case c) {
        return plateau(c.typePlateau()).aArbuste(c.ligne(), c.colonne());
    }

    public boolean aArbre(Case c) {
        return plateau(c.typePlateau()).aArbre(c.ligne(), c.colonne());
    }

    public boolean aArbreCouche(Case c) {
        return plateau(c.typePlateau()).aArbreCouche(c.ligne(), c.colonne());
    }

    public boolean estOccupable(Case c) {
        return plateau(c.typePlateau()).estOccupable(c.ligne(), c.colonne());
    }

    public boolean aObstacleMortel(Case c) {
        return plateau(c.typePlateau()).aObstacleMortel(c.ligne(), c.colonne());
    }

    boolean estChangementPlateauCorrect(Case depart, Case arrivee) {
        int typeDepart = depart.typePlateau();
        int typeArrivee = arrivee.typePlateau();
        int dL = arrivee.ligne() - depart.ligne();
        int dC = arrivee.colonne() - depart.colonne();

        return dL == 0 && dC == 0 && (typeArrivee - typeDepart == -1 || typeArrivee - typeDepart == 1);
    }

    boolean estDeplacementCorrect(Case depart, Case arrivee) {
        int typeDepart = depart.typePlateau();
        int typeArrivee = arrivee.typePlateau();
        int dL = arrivee.ligne() - depart.ligne();
        int dC = arrivee.colonne() - depart.colonne();

        return plateau(typeDepart) == plateau(typeArrivee) && plateau(typeDepart).estDeplacementCorrect(dL, dC);
    }

    boolean estMouvementCorrect(Case depart, Case arrivee) {
        return estChangementPlateauCorrect(depart, arrivee) || estDeplacementCorrect(depart, arrivee);
    }

    public boolean estJouable(Case depart, Case arrivee) {
        int dL = arrivee.ligne() - depart.ligne();
        int dC = arrivee.colonne() - depart.colonne();

        if (!estMouvementCorrect(depart, arrivee) || !aPion(depart)) {
            return false;
        }
        if (depart.typePlateau() == arrivee.typePlateau()) {
            return plateau(depart.typePlateau()).estJouable(depart.ligne(), depart.colonne(), dL, dC);
        }
        return estOccupable(arrivee);
    }

    Coup deplacer(Case depart, Case arrivee) { // TODO: Gérer le poussage d'arbre
        if (!estJouable(depart, arrivee)) {
            return null;
        }
        Coup coup = new Coup(depart, arrivee);

        if (estChangementPlateauCorrect(depart, arrivee)) {
        } else {
            int dL = arrivee.ligne() - depart.ligne();
            int dC = arrivee.colonne() - depart.colonne();
            pousser(coup, depart, dL, dC);

            Epoque p = plateau(depart.typePlateau());
            int pieceDepart = p.contenu(depart.ligne(), depart.colonne()) & (Epoque.BLANC | Epoque.NOIR);

            p.supprimer(depart.ligne(), depart.colonne(), pieceDepart);
            p.ajouter(arrivee.ligne(), arrivee.colonne(), pieceDepart);
            coup.ajouterMouvement(depart, arrivee);
        }
    }

    private void pousser(Coup coup, Case depart, int dL, int dC) { // pion poussé, non pousseur
        Case arrivee = new Case(depart.ligne() + dL, depart.colonne() + dC, depart.typePlateau());
        Epoque p = plateau(depart.typePlateau());
        int pieceDepart = p.contenu(depart.ligne(), depart.colonne()) & (Epoque.BLANC | Epoque.NOIR);

        if (aObstacleMortel(arrivee)) { // TODO: vérifier les règles
            p.supprimer(depart.ligne(), depart.colonne(), pieceDepart);
            coup.ajouterMouvement(depart, null);
            return;
        }

        int pieceArrivee = contenu(destL, destC) & (BLANC | NOIR);

        if (pieceDepart == pieceArrivee) {
            supprimer(l, c, pieceDepart);
            supprimer(destL, destC, pieceArrivee);
        } else {
            pousser(destL, destC, dL, dC);
            supprimer(l, c, pieceDepart);
            ajouter(destL, destC, pieceDepart);
        }
    }
}
