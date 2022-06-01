package Vue;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Vues {
    JFrame frame;
    VueJeu vueJeu;

    final static String DEMARRAGE = "Démarrage";
    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_SAISIES = "Nouvelle Partie";
    final static String MENU_PARTIES = "Charger Partie";
    final static String JEU = "Jeu";

    Vues(JFrame f) {
        frame = f;
    }

    void fixerVueJeu(VueJeu vue) {
        vueJeu = vue;
    }

    public void nouvellePartie() {
        if (vueJeu == null) {
            throw new IllegalStateException("Impossible de créer une nouvelle partie : vue du jeu non fixée");
        }
        vueJeu.nouvellePartie();
    }

    private void afficher(String nom) {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), nom);
    }

    public void afficherDemarrage() {
        afficher(DEMARRAGE);
    }

    public void afficherMenuPrincipal() {
        afficher(MENU_PRINCIPAL);
    }

    public void afficherMenuNouvellePartie() {
        afficher(MENU_SAISIES);
    }

    public void afficherJeu() {
        afficher(JEU);
    }

    public void afficherMenuChargerPartie(){afficher(MENU_PARTIES);}

    public void close() {
        frame.setVisible(true);
        frame.dispose();
    }

    public void afficherR() {
        try {
            String inputPdf = "assets/regles_du_jeu_FR.pdf";
            Path tempOutput = Files.createTempFile("TempManual", ".pdf");
            tempOutput.toFile().deleteOnExit();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(inputPdf)) {
                Files.copy(is, tempOutput, StandardCopyOption.REPLACE_EXISTING);
            }
            Desktop.getDesktop().open(tempOutput.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}