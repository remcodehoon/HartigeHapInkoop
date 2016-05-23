package presentation;

import businesslogic.Manager;
import javax.swing.JFrame;

/**
 *
 * @author Mart
 */
public class SupplierFrame extends JFrame {

    Controller controller = null;

    public SupplierFrame(Controller c, Manager m) {
        controller = c;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Leveranciers overzicht");
    }
}
