package presentation;

import javax.swing.JFrame;

/**
 *
 * @author Mart
 */
public class SupplierFrame extends JFrame {

    Controller controller = null;

    public SupplierFrame(Controller c) {
        controller = c;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Leveranciers overzicht");
    }
}
