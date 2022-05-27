package Vue;

import Global.Configuration;

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
    private String theme;
    private int hauteurPlateau, largeurPlateau;
    private int bordureHaut, bordureGauche, bordureBas, bordureDroite;
    private Image passe_inactif, present_inactif, futur_inactif;
    private Image passe_actif, present_actif, futur_actif;
    private Image blanc_inactif, noir_inactif;
    private Image blanc_actif, noir_actif;

    private Theme() {
        charger();
    }

    public static Theme instance() {
        if (instance == null) {
            instance = new Theme();
        }
        return instance;
    }

    private void chargerDimensions() {
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
    }

    private Image chargerImage(String nomImage) {
        Image image = null;

        InputStream in = chargerFichier(theme + File.separator + nomImage + ".png");

        try {
            image = ImageIO.read(in);
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de charger l'image : " + nomImage);
            System.exit(1);
        }
        return image;
    }

    void charger() {
        theme = "assets" + File.separator + "themes" + File.separator + Configuration.instance().lirePropriete("Theme");
        chargerDimensions();
        passe_inactif = chargerImage("plateaux" + File.separator + "passe_inactif");
        present_inactif = chargerImage("plateaux" + File.separator + "present_inactif");
        futur_inactif = chargerImage("plateaux" + File.separator + "futur_inactif");
        passe_actif = chargerImage("plateaux" + File.separator + "passe_actif");
        present_actif = chargerImage("plateaux" + File.separator + "present_actif");
        futur_actif = chargerImage("plateaux" + File.separator + "futur_actif");
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

    public Image plateau_passe_inactif() {
        return passe_inactif;
    }

    public Image plateau_present_inactif() {
        return present_inactif;
    }

    public Image plateau_futur_inactif() {
        return futur_inactif;
    }

    public Image plateau_passe_actif() {
        return passe_actif;
    }

    public Image plateau_present_actif() {
        return present_actif;
    }

    public Image plateau_futur_actif() {
        return futur_actif;
    }
}
