/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import javax.swing.JFrame;

/**
 *
 * @author Remco
 */
public class LoginFrame extends JFrame {

    Controller controller;

    public LoginFrame(Controller c) {
        controller = c;
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setLayout(null);
        setLocationRelativeTo(null);
    }
}