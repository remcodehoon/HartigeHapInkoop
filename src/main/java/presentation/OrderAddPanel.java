package presentation;


import businesslogic.Manager;
import domain.Order;
import domain.Supplier;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OrderAddPanel extends JPanel {

    private final JLabel label1;
    private final TextField field2, field3;
    private final JButton button1, button2;
    private final JComboBox box1,box2;
    Controller controller;
    Manager m;

    public OrderAddPanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Bestellingnummer:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Leverancier:", 25, 260, 200, 30, "right"));

        add(c.createLabel("[max 11 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[datum jjjj-mm-dd]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 220, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 260, 200, 30, "left"));
        
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
        String[] options = {"Aangemaakt", "Geaccepteerd", "Geleverd", "Afgerond"};
        box1 = new JComboBox(options);
        box1.setBounds(250, 220, 200, 30);
        add(box1);
        ArrayList<String> supplierNames = m.getSupplierNames();
        String[] suppliers = supplierNames.stream().toArray(String[]::new);
        box2 = new JComboBox(suppliers);
        box2.setBounds(250, 260, 200, 30);
        add(box2);
        
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
            String string1, string2;
            string1 = field2.getText();
            string2 = field3.getText();
            int status = m.getOrderStatus((String) box1.getSelectedItem());
            if(string1.length() > 0 && string1.length() <= 11 && m.checkNumbers(string1) 
                    && string2.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) { // heeft alle constrains gecheckt
                Supplier sup = m.getSupplier((String) box2.getSelectedItem());
                int fkey = sup.getId();
                int empId = m.getEmployeeId();
                Order newOrder = new Order(Integer.parseInt(string1), string2, status, empId, fkey);
                newOrder.setSupplier(sup);
                m.addOrder(newOrder);
                label1.setText("Bestelling toegevoegd");
            } else {
                label1.setText("Fout in de velden");
            }
        }
    }

}
