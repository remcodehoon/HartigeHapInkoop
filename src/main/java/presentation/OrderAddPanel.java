package presentation;


import businesslogic.Manager;
import domain.Order;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OrderAddPanel extends JPanel {

    private final JLabel label1;
    private final TextField field2, field3;
    private final JButton button1, button2;
    private final JComboBox box1;
    Controller controller;
    Manager m;

    public OrderAddPanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Bestellingnummer:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:", 25, 220, 200, 30, "right"));
        add(c.createLabel("[max 25 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[datum jjjj-mm-dd]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[max 1 getal]", 460, 220, 200, 30, "left"));

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
        String[] options = {"Aangemaakt", "Geaccepteerd", "Besteld", "Geleverd"};
        box1 = new JComboBox(options);
        box1.setBounds(250, 220, 200, 30);
        add(box1);

        button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(325, 400, 200, 50);
        add(button1);

        button2 = new JButton("Voeg toe");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(25, 400, 200, 50);
        add(button2);

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("");
            controller.makeVisible("Order_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String string1, string2, string3;
            string1 = field2.getText();
            string2 = field3.getText();
            string3 = (String) box1.getSelectedItem();
            int status;
            switch(string3){
                default:
                    status = 0;
                    break;
                
                case "Aangemaakt":
                    status = 0;
                    break;
                    
                case "Geaccepteerd":
                    status = 1;
                    break;    
                    
                case "Besteld":
                    status = 2;
                    break;    
                    
                case "Geleverd":
                    status = 3;
                    break;    
            }
            int empId = m.getEmployeeId();
            Order newOrder = new Order(Integer.parseInt(string1), string2, status, empId);
            m.addOrder(newOrder);
            label1.setText("Bestelling toegevoegd");
        }
    }

}
