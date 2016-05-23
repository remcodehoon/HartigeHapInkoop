package presentation;

import businesslogic.Manager;
import javax.swing.JFrame;

public class LoginFrame extends JFrame {

    Controller controller;
    Manager m;

    public LoginFrame(Controller c, Manager m) {
        this.m = m;
        controller = c;
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setLayout(null);
        setLocationRelativeTo(null);
    }
}