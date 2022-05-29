package Vue.JComposants;

import javax.swing.plaf.basic.BasicMenuUI;
import java.awt.*;

public class CMenuUI extends BasicMenuUI {

    public CMenuUI() {
        super.selectionBackground = new Color(0, 0, 0, 0);
        super.oldBorderPainted = false;
    }
}
