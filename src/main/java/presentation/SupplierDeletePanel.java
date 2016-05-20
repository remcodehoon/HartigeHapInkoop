package presentation;

import businesslogic.Manager;
import domain.Supplier;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SupplierDeletePanel extends JPanel {

    private JLabel label1;
    private TextField /*field1,*/ field2, field3, field4, field5, field6, field7;
    private JButton Button1, Button2;
    Controller controller = null;
    private Manager m;
    private int id = -1;

    public SupplierDeletePanel(Controller c) {
        controller = c;
        m = new Manager();
        setLayout(null);

        //add(m.createLabel("ID: ", 25,100,200,30,"right"));
        add(m.createLabel("Naam:", 25, 140, 200, 30, "right"));
        add(m.createLabel("Adres", 25, 180, 200, 30, "right"));
        add(m.createLabel("Postcode:", 25, 220, 200, 30, "right"));
        add(m.createLabel("Contactpersoon:", 25, 260, 200, 30, "right"));
        add(m.createLabel("Emailadres:", 25, 300, 200, 30, "right"));
        add(m.createLabel("Telefoonnummer:", 25, 340, 200, 30, "right"));

        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200, 450, 600, 30);
        add(label1);

        //field1 = new TextField();
        //field1.setBounds(250,100,200,30);
        //field1.setEditable(false);
        //add(field1);
        field2 = new TextField();
        field2.setBounds(250, 140, 200, 30);
        field2.setEditable(false);
        add(field2);
        field3 = new TextField();
        field3.setBounds(250, 180, 200, 30);
        field3.setEditable(false);
        add(field3);
        field4 = new TextField();
        field4.setBounds(250, 220, 200, 30);
        field4.setEditable(false);
        add(field4);
        field5 = new TextField();
        field5.setBounds(250, 260, 200, 30);
        field5.setEditable(false);
        add(field5);
        field6 = new TextField();
        field6.setBounds(250, 300, 200, 30);
        field6.setEditable(false);
        add(field6);
        field7 = new TextField();
        field7.setBounds(250, 340, 200, 30);
        field7.setEditable(false);
        add(field7);

        Button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        Button1.addActionListener(kh);
        Button1.setBounds(325, 400, 200, 50);
        add(Button1);

        Button2 = new JButton("Verwijder");
        ButtonHandler2 kh2 = new ButtonHandler2();
        Button2.addActionListener(kh2);
        Button2.setBounds(25, 400, 200, 50);
        add(Button2);

    }

    public void setSupplier(Supplier selSup) {
        field2.setText(selSup.getName());
        field3.setText(selSup.getAddress());
        field4.setText(selSup.getPostalCode());
        field5.setText(selSup.getContactName());
        field6.setText(selSup.getEmail());
        field7.setText(selSup.getPhoneNo());
        id = selSup.getId();
        //field1.setText(String.valueOf(selSup.getId()));
    }

    private class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            label1.setText("");
            controller.makeVisible("Supplier_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (id > -1) {
                label1.setText("'" + field2.getText() + "' verwijderd!");
                m.deleteSupplier(id);
            } else {
                label1.setText("Leverancier is niet geselecteerd, ga terug naar vorige scherm!");
            }
        }
    }

}
