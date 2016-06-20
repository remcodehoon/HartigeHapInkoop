package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class IngredientDeletePanel extends JPanel {

    private final JLabel label1;
    private final TextField /*field1,*/ field2, field3, field4, field5;
    private final JButton button1, button2;
    Controller controller;
    Manager m;
    private int id = -1;

    public IngredientDeletePanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200, 450, 600, 30);
        add(label1);

        add(c.createLabel("Naam:", 25, 140, 200, 30, "right"));
        add(c.createLabel("In voorraad:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Minimale voorraad:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Maximale voorraad:", 25, 260, 200, 30, "right"));

        field2 = new TextField("");
        field2.setBounds(250, 140, 200, 30);
        field2.setEditable(false);
        add(field2);
        field3 = new TextField("");
        field3.setBounds(250, 180, 200, 30);
        field3.setEditable(false);
        add(field3);
        field4 = new TextField("");
        field4.setBounds(250, 220, 200, 30);
        field4.setEditable(false);
        add(field4);
        field5 = new TextField("");
        field5.setBounds(250, 260, 200, 30);
        field5.setEditable(false);
        add(field5);

        button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(325, 400, 200, 50);
        add(button1);

        button2 = new JButton("Verwijder");
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
            if (id > -1) {
                try {
                    if(m.checkIngredientInDish(id)){
                        throw new Exception("Ingrediënt wordt gebruikt in gerecht en kan niet worden verwijderd");
                    }
                    m.deleteIngredient(id);
                    label1.setText("'" + field2.getText() + "' verwijderd!");
                } catch (Exception ex){
                    label1.setText(ex.getMessage());
                }                
            } else {
                label1.setText("Ingredient is niet geselecteerd, ga terug naar vorige scherm!");
            }
        }
    }

}
