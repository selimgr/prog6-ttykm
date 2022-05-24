package Global;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {
    private static Configuration instance;
    Properties p;
    Logger l;

    public static InputStream chargerFichier(String nomFichier) {
        InputStream in = ClassLoader.getSystemResourceAsStream(nomFichier);

        if (in == null || nomFichier.compareTo("") == 0) {
            throw new UncheckedIOException(new IOException("Impossible de charger le fichier " + nomFichier));
        }
        return in;
    }

    private Configuration() {
        p = new Properties();

        InputStream in = chargerFichier("default.cfg");

        try {
            p.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(new IOException("Impossible de charger le fichier default.cfg : ", e));
        }
    }

    public static Configuration instance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String lirePropriete(String nomPropriete) {
        String valeur = p.getProperty(nomPropriete);

        if (valeur == null) {
            throw new NoSuchElementException("Propriété inexistante : " + nomPropriete);
        }
        return valeur;
    }

    public void ecrirePropriete(String nomPropriete, String valeurPropriete) {

    }

    public Logger logger() {
        if (l == null) {
            System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s : %5$s%n");
            l = Logger.getLogger("Gaufre.Logger");
            l.setLevel(Level.parse(lirePropriete("LogLevel")));
        }
        return l;
    }

    public void sauvegarder() {

    }
}
