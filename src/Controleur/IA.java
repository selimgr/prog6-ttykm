package Controleur;

import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class IA {
    Jeu jeu;
    Joueur ia;
    Joueur adversaire;
    //List<Coup> coups;
    Coup c1;
    Coup c2;
    HashMap<String,Integer> antiCycle;
    int horizon;
    ControleurMediateur ctrl;



    int calcul(Plateau p, int horizon,int minmax) {
        /* --------- Feuille --------- */
        if (jeu.vainqueur() == ia){return 1000;}
        if (jeu.vainqueur() == adversaire){return -1000;}
        if (horizon <= 0 ){return fonctionApproximation(p);}
        // On peux parcourir un plus grand horizon donc ce coup peut devenir plus nul ou plus intéressant
        if ( antiCycle.get(p.hash()) != null && antiCycle.get(p.hash()) >= horizon){return -1000*minmax;}

        /* --------- Cas général --------- */
        antiCycle.put(p.hash(),horizon);
        ArrayList<Coup> C2;
        Plateau copieP = p.copier();
        Joueur j = adversaire; if (minmax ==1){j = ia;}
        Coup premierCoup = null;
        Coup secondCoup = null;
        int valeur = -100000*minmax;
        int valeur2,lA,cA;
        Case arr, dep;
        Epoque eA;
        ArrayList<Coup> C = p.casesJouablesEpoque(ia,false,0,0,null);
        Iterator<Coup> it = C.iterator();

        //Parcours de l'arbre ...
        while (it.hasNext()){
            Coup c = it.next();
            jeu.plateau().fixerPlateau(copieP);
            ctrl.jouer(c.pion().ligne(),c.pion().colonne(),c.pion().epoque());
            // if instance of Mouvement ;
            arr = c.getArrivee();
            ctrl.jouer(lA,cA,eA);
            C2 = p.casesJouablesEpoque(ia,true,lA,cA,Epoque.depuisIndice(eA));
            Iterator<Coup> it2 = C2.iterator();
            while (it2.hasNext()){
                Coup c2 = it.next();
                lA = c2.lignePion()+ c.deplacementLignePion();
                cA = c2.deplacementColonnePion()+ c.deplacementColonnePion();
                eA = c2.epoquePion().indice() + c.deplacementEpoquePion();
                ctrl.jouer(lA,cA,eA);
                valeur2 = valeur;
                valeur = Math.max(valeur*minmax,calcul(copieP,horizon-1,minmax*-1)*minmax);
                if (valeur2 < valeur || horizon == this.horizon) {
                    // TODO : Ajout d'aléatoire possible dans une certaine mesure
                    /*
                    Faire un tableau de coups associé à une somme qui se cumule
                    tab[0] = (Coup c1,score(c1))
                    tab[1] = (Coup c2,score(c2) +tab[0][1])
                    ...
                    tab[n] ) (Coup cn, score(cn)+tab[n-1][1]
                     */
                    premierCoup = c;
                    secondCoup = c2;
                }
            }
        }
        this.c1 = premierCoup;
        this.c2 = secondCoup;
        return valeur;
    }
    public abstract int fonctionApproximation(Plateau p);

    // TODO : Ajouter les bonnes heuristiques : voir document IA
    //public abstract void heuristic();

    void jouer() {
        // Copie d'un plateau pour eviter les modifications
        Plateau p = jeu.plateau().copier();
        // Calcul du meilleur coup est stockage dans c1 et c2
        this.calcul(this.jeu.plateau(),horizon,1);
        //DEBUG
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        // Calcul du déplacement de c2
        int lA = this.c2.lignePion()+ this.c2.deplacementLignePion();
        int cA = this.c2.deplacementColonnePion()+ this.c2.deplacementColonnePion();
        int eA = this.c2.epoquePion().indice() + this.c2.deplacementEpoquePion();
        // On remet le plateau tel qu'il était ( assurance d'un plateau identique)
        jeu.plateau().fixerPlateau(p);
        //On joue les 2 coups
        ctrl.jouer(this.c1.lignePion(), this.c1.colonnePion(), this.c1.epoquePion());
        ctrl.jouer(this.c2.lignePion(), this.c2.colonnePion(), this.c2.epoquePion());
        ctrl.jouer(lA,cA,Epoque.depuisIndice(eA));
        //TODO : Gestion du focus

    }


}
