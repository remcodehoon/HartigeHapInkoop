/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import javax.swing.JFrame;

/**
 *
 * @author Mart
 */
public class IngredientFrame extends JFrame {

    Controller controller = null;

    public IngredientFrame(Controller c) {
        controller = c;
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Leveranciers overzicht");
        setLayout(null);
    }
}
