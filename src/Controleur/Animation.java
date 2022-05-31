package Controleur;

public abstract class Animation {
    private final int lenteur;
    private int decompte;

    public Animation(int l) {
        lenteur = l;
        decompte = l;
    }

    public void temps() {
        decompte--;

        if (decompte <= 0) {
            decompte = lenteur;
            miseAJour();
        }
    }

    public abstract void miseAJour();

    public boolean terminee() {
        return false;
    }
}
