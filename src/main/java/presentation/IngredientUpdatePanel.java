package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class IngredientUpdatePanel extends JPanel {

    private final JLabel label1;
    private final TextField /*field1,*/ field2, field3, field4, field5;
    private final JButton button1, button2;
    Controller controller;
    Manager m;
    private int id = -1;

    public IngredientUpdatePanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Naam:*", 25, 140, 200, 30, "right"));
        add(c.createLabel("In voorraad:*", 25, 180, 200, 30, "right"));
        add(c.createLabel("Minimale voorraad:*", 25, 220, 200, 30, "right"));
        add(c.createLabel("Maximale voorraad:*", 25, 260, 200, 30, "right"));
        add(c.createLabel("[max 45 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[max 11 getallen]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[max 11 getallen]", 460, 220, 200, 30, "left"));
        add(c.createLabel("[max 11 getallen]", 460, 260, 200, 30, "left"));

        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200, 450, 600, 30);
        add(label1);

        field2 = new TextField();
        field2.setBounds(250, 140, 200, 30);
        add(field2);
        field3 = new TextField();
        field3.setBounds(250, 180, 200, 30);
        add(field3);
        field4 = new TextField();
        field4.setBounds(250, 220, 200, 30);
        add(field4);
        field5 = new TextField();
        field5.setBounds(250, 260, 200, 30);
        add(field5);

        button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(325, 400, 200, 50);
        add(button1);

        button2 = new JButton("Wijzig");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(25, 400, 200, 50);
        add(button2);

    }

    public void setIngredient(Ingredient selIng) {
        field2.setText(selIng.getName());
        field3.setText(String.valueOf(selIng.getInStock()));
        field4.setText(String.valueOf(selIng.getMinStock()));
        field5.setText(String.valueOf(selIng.getMaxStock()));
        id = selIng.getId();
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("");
            controller.makeVisible("Ingredient_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "";
            try{
                String string2,string3,string4,string5;
                string2 = field2.getText();
                string3 = field3.getText();
                string4 = field4.getText();
                string5 = field5.getText();
                if (id > -1) {
                if(string2.length() <= 0 || string2.length() > 45 )
                    throw new Exception("Fout in Naam.");
                if(string3.length() <= 0 || !m.checkNumbers(string3) || string3.length() > 11)
                    throw new Exception("Fout in Voorraad.");
                if(string4.length() <= 0 || !m.checkNumbers(string4) || string4.length() > 11)
                    throw new Exception("Fout in Minimum Voorraad.");
                if(string5.length() <= 0 || !m.checkNumbers(string5) || string5.length() > 11)
                    throw new Exception("Fout in Maximum Voorraad.");
                Ingredient updateIngredient = m.getIngredient(id);
                updateIngredient.setAttInt("id", id);
                updateIngredient.setName(string2);
                updateIngredient.setAttInt("inStock", Integer.parseInt(string3));
                updateIngredient.setAttInt("minStock", Integer.parseInt(string4));
                updateIngredient.setAttInt("maxStock", Integer.parseInt(string5));
                m.updateIngredient(id, updateIngredient);
                message = ("Ingredient is gewijzigd!");
                } else {
                message = ("Ingredient is niet geselecteerd, ga terug naar vorige scherm!");
                }
            } catch (Exception f){
                message = f.getMessage();
            } finally {
                label1.setText(message);
            }
        }
    }

}
