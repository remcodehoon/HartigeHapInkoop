package presentation;

import businesslogic.Manager;
import domain.Order;
import domain.Supplier;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OrderUpdatePanel extends JPanel {

    private final JLabel label1,label2;
    private final TextField field2, field3, field5;
    private final JButton button1, button2;
    private final JComboBox box1,box2;
    Controller controller;
    Manager m;
    private int id = -1;

    public OrderUpdatePanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Bestelling nummer:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Leverancier:", 25, 260, 200, 30, "right"));
        add(c.createLabel("Medewerker nummer:", 25, 300, 200, 30, "right"));
        
        add(c.createLabel("[max 11 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[datum jjjj-mm-dd]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 220, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 260, 200, 30, "left"));
        add(c.createLabel("[max 1 getal]", 460, 300, 200, 30, "left"));
        

        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200, 450, 600, 30);
        add(label1);
        label2 = new JLabel("");
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setBounds(200, 290, 250, 9);
        add(label2);
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
        field5 = new TextField();
        field5.setBounds(250, 300, 200, 30);
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

    public void setOrder(Order selOrder) {
        id = selOrder.getNr();
        field2.setText(String.valueOf(id));
        field3.setText(String.valueOf(selOrder.getDate()));
        box1.setSelectedIndex(selOrder.getStatusId());
        field5.setText(String.valueOf(selOrder.getEmployeeId()));
        if(selOrder.getSupplier() != null)
            label2.setText(selOrder.getSupplier().getName());
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
            if (id > -1) {
                if(field2.getText().length() > 0 && field2.getText().length() <= 11 && m.checkNumbers(field2.getText()) 
                        && field3.getText().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")
                        && field5.getText().length() == 1 && m.checkNumbers(field5.getText())) { // heeft alle constrains gecheckt
                    Order updateOrder = m.getOrder(id);
                    updateOrder.setNr(Integer.parseInt(field2.getText()));
                    updateOrder.setDate(field3.getText());
                    int status = m.getOrderStatus((String) box1.getSelectedItem());
                    updateOrder.setStatusId(status);
                    updateOrder.setEmployeeId(Integer.parseInt(field5.getText()));
                    updateOrder.setSupplier(m.getSupplier((String) box2.getSelectedItem()));
                    updateOrder.SetFkey(m.getSupplier((String) box2.getSelectedItem()).getId());
                    m.updateOrder(id, updateOrder);
                    label1.setText("Bestelling is gewijzigd!");
                    id = Integer.parseInt(field2.getText());
                } else {
                    label1.setText("Fout in de velden!");
                }
            } else {
                label1.setText("Bestelling is niet geselecteerd, ga terug naar vorige scherm!");
            }
        }
    }

}
