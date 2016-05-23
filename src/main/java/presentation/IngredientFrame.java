package presentation;

import businesslogic.Manager;
import javax.swing.JFrame;

public class IngredientFrame extends JFrame {

    Controller controller = null;

    public IngredientFrame(Controller c, Manager m) {
        controller = c;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Leveranciers overzicht");
        setLayout(null);
    }
}
