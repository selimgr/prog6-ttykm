package Vue;

import Global.Configuration;
import Modele.Epoque;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

import static Global.Configuration.chargerFichier;

public class Theme {
    private static Theme instance;
    private int hauteurPlateau, largeurPlateau;
    private int bordureHaut, bordureGauche, bordureBas, bordureDroite;
    private int hauteurCase, largeurCase;
    private Image plateau_passe_inactif, plateau_present_inactif, plateau_futur_inactif;
    private Image plateau_passe_actif, plateau_present_actif, plateau_futur_actif;
    private Image blanc_inactif, blanc_actif_passe, blanc_actif_present, blanc_actif_futur;
    private Image blanc_selectionne_passe, blanc_selectionne_present, blanc_selectionne_futur;
    private Image noir_inactif, noir_actif_passe, noir_actif_present, noir_actif_futur;
    private Image noir_selectionne_passe, noir_selectionne_present, noir_selectionne_futur;
    private Image focus_blanc, focus_noir;
    private Image graine_inactif, graine_actif_passe, graine_actif_present, graine_actif_futur;
    private Image arbuste, arbre, arbre_couche_haut, arbre_couche_droite, arbre_couche_bas, arbre_couche_gauche;
    private Image surbrillance_passe, surbrillance_present, surbrillance_futur;

    private Theme() {
        charger();
    }

    public static Theme instance() {
        if (instance == null) {
            instance = new Theme();
        }
        return instance;
    }

    private void chargerDimensions(String theme) {
        Properties p = new Properties();

        InputStream in = chargerFichier(theme + File.separator + "plateaux" + File.separator + "dimensions.cfg");

        try {
            p.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(new IOException("Impossible de charger le fichier dimensions.cfg : ", e));
        }
        hauteurPlateau = Integer.parseInt(p.getProperty("Hauteur_plateau"));
        largeurPlateau = Integer.parseInt(p.getProperty("Largeur_plateau"));
        bordureHaut = Integer.parseInt(p.getProperty("Bordure_haut"));
        bordureGauche = Integer.parseInt(p.getProperty("Bordure_gauche"));
        bordureBas = Integer.parseInt(p.getProperty("Bordure_bas"));
        bordureDroite = Integer.parseInt(p.getProperty("Bordure_droite"));
        hauteurCase = (hauteurPlateau - bordureHaut - bordureBas) / 4;
        largeurCase = (largeurPlateau - bordureGauche - bordureDroite) / 4;
    }

    public Image chargerImage(String nomImage) {
        Image image = null;

        InputStream in = chargerFichier(nomImage);

        try {
            image = ImageIO.read(in);
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de charger l'image : " + nomImage);
            System.exit(1);
        }
        return image;
    }

    void charger() {
        String theme = "assets" + File.separator + "themes" + File.separator + Configuration.instance().lirePropriete("Theme");
        chargerDimensions(theme);

        String plateaux = theme + File.separator + "plateaux" + File.separator;
        plateau_passe_inactif = chargerImage(plateaux + "plateau_passe_inactif.png");
        plateau_present_inactif = chargerImage(plateaux + "plateau_present_inactif.png");
        plateau_futur_inactif = chargerImage(plateaux + "plateau_futur_inactif.png");
        plateau_passe_actif = chargerImage(plateaux + "plateau_passe_actif.png");
        plateau_present_actif = chargerImage(plateaux + "plateau_present_actif.png");
        plateau_futur_actif = chargerImage(plateaux + "plateau_futur_actif.png");

        String pions = theme + File.separator + "pions" + File.separator;
        blanc_inactif = chargerImage(pions + "blanc_inactif.png");
        blanc_actif_passe = chargerImage(pions + "blanc_actif_passe.png");
        blanc_actif_present = chargerImage(pions + "blanc_actif_present.png");
        blanc_actif_futur = chargerImage(pions + "blanc_actif_futur.png");
        blanc_selectionne_passe = chargerImage(pions + "blanc_selectionne_passe.png");
        blanc_selectionne_present = chargerImage(pions + "blanc_selectionne_present.png");
        blanc_selectionne_futur = chargerImage(pions + "blanc_selectionne_futur.png");
        noir_inactif = chargerImage(pions + "noir_inactif.png");
        noir_actif_passe = chargerImage(pions + "noir_actif_passe.png");
        noir_actif_present = chargerImage(pions + "noir_actif_present.png");
        noir_actif_futur = chargerImage(pions + "noir_actif_futur.png");
        noir_selectionne_passe = chargerImage(pions + "noir_selectionne_passe.png");
        noir_selectionne_present = chargerImage(pions + "noir_selectionne_present.png");
        noir_selectionne_futur = chargerImage(pions + "noir_selectionne_futur.png");

        String focus = theme + File.separator + "focus" + File.separator;
        focus_blanc = chargerImage(focus + "focus_blanc.png");
        focus_noir = chargerImage(focus + "focus_noir.png");

        String chapitre_1 = theme + File.separator + "chapitre_1" + File.separator;
        graine_inactif = chargerImage(chapitre_1 + "graine_inactif.png");
        graine_actif_passe = chargerImage(chapitre_1 + "graine_actif_passe.png");
        graine_actif_present = chargerImage(chapitre_1 + "graine_actif_present.png");
        graine_actif_futur = chargerImage(chapitre_1 + "graine_actif_futur.png");
        arbuste = chargerImage(chapitre_1 + "arbuste.png");
        arbre = chargerImage(chapitre_1 + "arbre.png");
        arbre_couche_haut = chargerImage(chapitre_1 + "arbre_couche_haut.png");
        arbre_couche_droite = chargerImage(chapitre_1 + "arbre_couche_droite.png");
        arbre_couche_bas = chargerImage(chapitre_1 + "arbre_couche_bas.png");
        arbre_couche_gauche = chargerImage(chapitre_1 + "arbre_couche_gauche.png");

        String surbrillance = theme + File.separator + "surbrillance" + File.separator;
        surbrillance_passe = chargerImage(surbrillance + "surbrillance_passe.png");
        surbrillance_present = chargerImage(surbrillance + "surbrillance_present.png");
        surbrillance_futur = chargerImage(surbrillance + "surbrillance_futur.png");
    }

    public int hauteurPlateau() {
        return hauteurPlateau;
    }

    public int largeurPlateau() {
        return largeurPlateau;
    }

    public int bordureHaut() {
        return bordureHaut;
    }

    public int bordureGauche() {
        return bordureGauche;
    }

    public int bordureBas() {
        return bordureBas;
    }

    public int bordureDroite() {
        return bordureDroite;
    }

    public int hauteurCase() {
        return hauteurCase;
    }

    public int largeurCase() {
        return largeurCase;
    }

    public Image plateau_passe_inactif() {
        return plateau_passe_inactif;
    }

    public Image plateau_present_inactif() {
        return plateau_present_inactif;
    }

    public Image plateau_futur_inactif() {
        return plateau_futur_inactif;
    }

    public Image plateau_passe_actif() {
        return plateau_passe_actif;
    }

    public Image plateau_present_actif() {
        return plateau_present_actif;
    }

    public Image plateau_futur_actif() {
        return plateau_futur_actif;
    }

    public Image blanc_inactif() {
        return blanc_inactif;
    }

    public Image blanc_actif(Epoque e) {
        if (e == Epoque.PASSE) {
            return blanc_actif_passe;
        } else if (e == Epoque.PRESENT) {
            return blanc_actif_present;
        } else if (e == Epoque.FUTUR) {
            return blanc_actif_futur;
        } else {
            return null;
        }
    }

    public Image blanc_selectionne(Epoque e) {
        if (e == Epoque.PASSE) {
            return blanc_selectionne_passe;
        } else if (e == Epoque.PRESENT) {
            return blanc_selectionne_present;
        } else if (e == Epoque.FUTUR) {
            return blanc_selectionne_futur;
        } else {
            return null;
        }
    }

    public Image noir_inactif() {
        return noir_inactif;
    }

    public Image noir_actif(Epoque e) {
        if (e == Epoque.PASSE) {
            return noir_actif_passe;
        } else if (e == Epoque.PRESENT) {
            return noir_actif_present;
        } else if (e == Epoque.FUTUR) {
            return noir_actif_futur;
        } else {
            return null;
        }
    }

    public Image noir_selectionne(Epoque e) {
        if (e == Epoque.PASSE) {
            return noir_selectionne_passe;
        } else if (e == Epoque.PRESENT) {
            return noir_selectionne_present;
        } else if (e == Epoque.FUTUR) {
            return noir_selectionne_futur;
        } else {
            return null;
        }
    }

    public Image focus_blanc() {
        return focus_blanc;
    }

    public Image focus_noir() {
        return focus_noir;
    }

    public Image graine_inactif() {
        return graine_inactif;
    }

    public Image graine_actif(Epoque e) {
        if (e == Epoque.PASSE) {
            return graine_actif_passe;
        } else if (e == Epoque.PRESENT) {
            return graine_actif_present;
        } else if (e == Epoque.FUTUR) {
            return graine_actif_futur;
        } else {
            return null;
        }
    }

    public Image arbuste() {
        return arbuste;
    }

    public Image arbre() {
        return arbre;
    }

    public Image arbre_couche_haut() {
        return arbre_couche_haut;
    }

    public Image arbre_couche_droite() {
        return arbre_couche_droite;
    }

    public Image arbre_couche_bas() {
        return arbre_couche_bas;
    }

    public Image arbre_couche_gauche() {
        return arbre_couche_gauche;
    }

    public Image surbrillance(Epoque e) {
        if (e == Epoque.PASSE) {
            return surbrillance_passe;
        } else if (e == Epoque.PRESENT) {
            return surbrillance_present;
        } else if (e == Epoque.FUTUR) {
            return surbrillance_futur;
        } else {
            return null;
        }
    }
}
