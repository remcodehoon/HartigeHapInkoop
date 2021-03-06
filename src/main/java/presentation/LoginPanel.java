package presentation;

import businesslogic.Manager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {

    private final JLabel usernameLabel, passwordLabel, notificationLabel;
    private final TextField username;
    private final JPasswordField password;
    private final JButton login;
    Controller controller;
    final Manager m;
    private final LoginHandler loginHandler;

    public LoginPanel(Controller c, Manager m) {
        controller = c;
	this.m = m;
        
        usernameLabel = new JLabel("Gebruikersnaam:", SwingConstants.CENTER);
        usernameLabel.setBounds(0,0,330,30);
        username = new TextField(20);
        username.setBounds(90,30,150,30);
        passwordLabel = new JLabel("Wachtwoord:", SwingConstants.CENTER);
        passwordLabel.setBounds(0,60,330,30);
        password = new JPasswordField(20);
        password.setEchoChar('*');
        password.setBounds(90,90,150,30);
        login = new JButton("Login");
        loginHandler = new LoginHandler();
        login.addActionListener(loginHandler);
        login.setBounds(105,130,120,30);
        notificationLabel = new JLabel("",SwingConstants.CENTER);
        notificationLabel.setBounds(0,170,330,30);
    }

    public void createButtons() {
        setLayout(null);
        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(login);
        getRootPane().setDefaultButton(login);
        add(notificationLabel);
    }
    
    private class LoginHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            char[] passwordInput = password.getPassword();
            String p = new String(passwordInput);
            String u = username.getText();
            
            if(m.checkLoginInfo(u, p)) {
                if(m.getEmployeeFunctionId(u, p) == 7 || m.getEmployeeFunctionId(u, p) == 8){
                    m.setEmployeeId(m.getEmployeeId(u, p));
                    notificationLabel.setText("Welkom");
                    controller.createFrames();
                    controller.makeVisible("Mainmenu");
                } else {
                    notificationLabel.setText("U bent niet bevoegd om dit systeem te betreden");
                }
            } else 
                notificationLabel.setText("Gebruiksnaam/wachtwoord combinatie onjuist");
            }
        }
    }


