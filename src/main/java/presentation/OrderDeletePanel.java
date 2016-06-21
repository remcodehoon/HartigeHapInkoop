package presentation;

import businesslogic.Manager;
import domain.Order;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OrderDeletePanel extends JPanel {

    private final JLabel label1;
    private final TextField field2, field3, field4, field5, field6;
    private final JButton button1, button2;
    Controller controller;
    Manager m;
    private int id = -1;

    public OrderDeletePanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200, 450, 600, 30);
        add(label1);

        add(c.createLabel("Bestelling nummer:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Leverancier:", 25, 260, 200, 30, "right"));
        add(c.createLabel("Ingevoerd door medewerker:", 25, 300, 200, 30, "right"));

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
        field6 = new TextField("");
        field6.setBounds(250, 300, 200, 30);
        field6.setEditable(false);
        add(field6);
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

    public void setOrder(Order selOrder) {
        id = selOrder.getId();
        field2.setText(selOrder.getNr());
        field3.setText(String.valueOf(selOrder.getDate()));
        field4.setText(String.valueOf(m.getOrderStatus(selOrder.getStatusId())));
        field6.setText(String.valueOf(m.getEmployeeName(selOrder.getEmployeeId())));
        if(selOrder.getSupplier() != null)
            field5.setText(String.valueOf(selOrder.getSupplier().getName()));
        else
            field5.setText("Order heeft nog geen leverancier");
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
                label1.setText("Order " + field2.getText() + " verwijderd!");
                m.deleteOrder(id);
            } else {
                label1.setText("Order is niet geselecteerd, ga terug naar vorige scherm!");
            }
        }
    }

}
