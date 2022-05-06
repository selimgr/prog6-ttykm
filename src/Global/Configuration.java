package Global;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Configuration {
    private static Configuration instance;
    Properties p;
    Logger l;

    public static InputStream chargerFichier(String nomFichier) {
        return null;
    }

    private Configuration() {

    }

    public static Configuration instance() {
        return null;
    }

    public String lirePropriete(String nomPropriete) {
        return null;
    }

    public void ecrirePropriete(String nomPropriete, String valeurPropriete) {

    }

    public Logger logger() {
        return null;
    }
}
