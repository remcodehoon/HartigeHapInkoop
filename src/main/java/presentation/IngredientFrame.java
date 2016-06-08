package presentation;

import javax.swing.JFrame;

public class IngredientFrame extends JFrame {

    Controller controller = null;

    public IngredientFrame(Controller c) {
        controller = c;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Ingredient overzicht");
        setLayout(null);
    }
}
