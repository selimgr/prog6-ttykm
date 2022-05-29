package Vue.JComposants;

import javax.swing.plaf.basic.BasicMenuItemUI;
import java.awt.*;

public class CMenuItemUI extends BasicMenuItemUI {
    public CMenuItemUI(boolean opaque) {
        super.selectionBackground = opaque ? Color.white: new Color(0, 0, 0, 0);
        super.selectionForeground = opaque ? Color.black: Color.white;
    }
}