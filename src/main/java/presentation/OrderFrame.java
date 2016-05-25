package presentation;

import javax.swing.JFrame;

public class OrderFrame extends JFrame {

    Controller controller = null;

    public OrderFrame(Controller c) {
        controller = c;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bestelling overzicht");
        setLayout(null);
    }
}
