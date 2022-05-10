package Modele;

public class Case {
    int l, c;
    TypePlateau plateau;
    int contenu;

    Case(int l, int c, TypePlateau plateau, int contenu) {
        this.l = l;
        this.c = c;
        this.plateau = plateau;
        this.contenu = contenu;
    }

    public int ligne() {
        return l;
    }

    public int colonne() {
        return c;
    }

    public TypePlateau plateau() {
        return plateau;
    }

    public int contenu() {
        return contenu;
    }

    void setContenu(int cont) {
        contenu = cont;
    }

}
