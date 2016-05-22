package presentation;

import businesslogic.Manager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class LoginPanel extends JPanel {

    private final JLabel usernameLabel, passwordLabel;
    private final TextField username;
    private final JPasswordField password;
    private final JButton login;
    Controller controller;
    private final Manager m;
    private final LoginHandler loginHandler;

    public LoginPanel(Controller c) {
        controller = c;
        m = new Manager();
        
        usernameLabel = new JLabel("Gebruikersnaam:");
        passwordLabel = new JLabel("Wachtwoord:");
        username = new TextField(25);
        password = new JPasswordField(25);
        password.setEchoChar('*');
        login = new JButton("Login");
        
        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(login);
        loginHandler = new LoginHandler();
        login.addActionListener(loginHandler);

    }

    private class LoginHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            char[] passwordInput = password.getPassword();
            String p = new String(passwordInput);
            String u = username.getText();
            
            if(u.equals("naam") && p.equals("wachtwoord")) {
                controller.makeVisible("Mainmenu");
            }
        }
    }
}