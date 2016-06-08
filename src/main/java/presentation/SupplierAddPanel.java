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

public class SupplierAddPanel extends JPanel {

    private final JLabel label1;
    private final TextField field2, field3, field4, field5, field6, field7;
    private final JButton button1, button2;
    Controller controller;
    private final Manager m;

    public SupplierAddPanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Naam:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Adres", 25, 180, 200, 30, "right"));
        add(c.createLabel("Postcode:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Contactpersoon:", 25, 260, 200, 30, "right"));
        add(c.createLabel("Emailadres:", 25, 300, 200, 30, "right"));
        add(c.createLabel("Telefoonnummer:", 25, 340, 200, 30, "right"));

        add(c.createLabel("[max 45 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[max 45 char]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[max 6 char]", 460, 220, 200, 30, "left"));
        add(c.createLabel("[max 45 char]", 460, 260, 200, 30, "left"));
        add(c.createLabel("[max 45 char]", 460, 300, 200, 30, "left"));
        add(c.createLabel("[max 14 getallen]", 460, 340, 200, 30, "left"));

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
        field6 = new TextField();
        field6.setBounds(250, 300, 200, 30);
        add(field6);
        field7 = new TextField();
        field7.setBounds(250, 340, 200, 30);
        add(field7);

        button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(325, 400, 200, 50);
        add(button1);

        button2 = new JButton("Voeg Toe");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(25, 400, 200, 50);
        add(button2);

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("");
            controller.makeVisible("Supplier_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "";
            try{
                String string2, string3, string4, string5, string6, string7;
                string2 = field2.getText();
                string3 = field3.getText();
                string4 = field4.getText();
                string5 = field5.getText();
                string6 = field6.getText();
                string7 = field7.getText();
                if(string2.length() <= 0 || string2.length() > 45 )
                    throw new Exception("Fout in Naam.");
                if(string3.length() <= 0 || string3.length() > 45)
                    throw new Exception("Fout in Adres.");
                if(string4.length() <= 0 || string4.length() > 6)
                    throw new Exception("Fout in Postcode.");
                if(string5.length() <= 0 || string5.length() > 45)
                    throw new Exception("Fout in Contactpersoon.");
                if(string5.length() <= 0 || string5.length() > 45)
                    throw new Exception("Fout in Email.");
                if(string5.length() <= 0 || string5.length() > 14)
                    throw new Exception("Fout in Telefoonnummer.");
                Supplier newSupplier = new Supplier(0, string2, string3, string4, string5, string6, string7);
                m.addSupplier(newSupplier);
                message = "Nieuwe leverancier is aangemaakt!";
            } catch (Exception f){
                message = f.getMessage();  
            } finally {
                label1.setText(message);
            }
        }
    }

}
