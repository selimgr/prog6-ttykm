package Controleur;

import Vue.Theme;

public class AnimationDemarrage extends Animation {
    private final ControleurMediateur c;
    private boolean termine;

    public AnimationDemarrage(int l, ControleurMediateur c) {
        super(l);
        this.c = c;
    }

    @Override
    public void miseAJour() {
        c.vues.afficherMenuPrincipal();
        Theme.instance();
        termine = true;
    }

    @Override
    public boolean terminee() {
        return termine;
    }
}
