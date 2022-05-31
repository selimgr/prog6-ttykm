package Global;

import Modele.Jeu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sauvegarde implements Serializable {
    private final Jeu jeu;
    private String nomFichier;
    private static final String path = System.getProperty("user.home") + File.separator + ".TTYKM" + File.separator +
            "sauvegardes" + File.separator;

    public Sauvegarde(Jeu jeu) {
        this.jeu = jeu;
    }

    public void enregistrer() {
        FileOutputStream fichier;
        ObjectOutputStream output;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();

        if (!dossierExiste()) {
            creerDossier();
        }

        if (nomFichier != null) {
            supprimer(path + nomFichier);
        }
        nomFichier = dtf.format(now) + "_" + jeu.joueur1().nom() + "_vs_" + jeu.joueur2().nom();

        try {
            File f = new File(path + nomFichier);

            if (!f.createNewFile()) {
                Configuration.instance().logger().severe("Impossible de sauvegarder la partie");
                return;
            }
            fichier = new FileOutputStream(path + nomFichier, false);
            output = new ObjectOutputStream(fichier);
            output.writeObject(jeu);
        } catch (IOException e) {
            Configuration.instance().logger().severe("Impossible de sauvegarder la partie");
            return;
        }

        try {
            output.flush();
            output.close();
            fichier.close();
        } catch (IOException e) {
            Configuration.instance().logger().warning("Erreur de fermeture du fichier de sauvegarde : " + nomFichier);
        }
    }

    public static Jeu charger(String nomSauvegarde) {
        FileInputStream fichier;
        ObjectInputStream input;
        Jeu j;

        try {
            fichier = new FileInputStream(path + nomSauvegarde);
            input = new ObjectInputStream(fichier);
            j = (Jeu) input.readObject();
            input.close();
            fichier.close();
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de charger la partie");
            return null;
        }

        try {
            input.close();
            fichier.close();
        } catch (IOException e) {
            Configuration.instance().logger().warning("Erreur de fermeture du fichier de sauvegarde : " + nomSauvegarde);
        }
        return j;
    }

    public static void supprimer(String nomSauvegarde) {
        try {
            Files.deleteIfExists(Path.of(path + nomSauvegarde));
        } catch (IOException e) {
            Configuration.instance().logger().severe("Impossible de supprimer la partie");
        }
    }

    public static String[] liste() {
        File f = new File(path);
        return f.list();
    }

    private static boolean dossierExiste() {
        File f = new File(path);
        return f.exists();
    }

    private static void creerDossier() {
        File f = new File(path);

        if (!f.mkdirs()) {
            throw new RuntimeException("Impossible de créer le dossier .TTYKM");
        }
    }
}
