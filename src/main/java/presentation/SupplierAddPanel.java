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


public class SupplierAddPanel extends JPanel{
    private JLabel label1;
    private TextField /*field1,*/field2,field3,field4,field5,field6,field7;
    private JButton button1,button2;
    Controller controller = null;
    private Manager m;
    
    public SupplierAddPanel(Controller c){
        controller = c;
        m = new Manager();
        setLayout(null);
        
        //add(m.createLabel("ID: ", 25,100,200,30,"right"));
        add(m.createLabel("Naam:", 25,140,200,30,"right"));
        add(m.createLabel("Adres", 25,180,200,30,"right"));
        add(m.createLabel("Postcode:", 25,220,200,30,"right"));
        add(m.createLabel("Contactpersoon:", 25,260,200,30,"right"));
        add(m.createLabel("Emailadres:", 25,300,200,30,"right"));
        add(m.createLabel("Telefoonnummer:", 25,340,200,30,"right"));
        
        add(m.createLabel("[max 50 char]", 460,140,200,30,"left"));
        add(m.createLabel("[max 35 char]", 460,180,200,30,"left"));
        add(m.createLabel("[max 6 char]", 460,220,200,30,"left"));
        add(m.createLabel("[max 25 char]", 460,260,200,30,"left"));
        add(m.createLabel("[max 40 char]", 460,300,200,30,"left"));
        add(m.createLabel("[max 15 getallen]", 460,340,200,30,"left"));  
        
        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200,450,600,30);
        add(label1);
        
        //field1 = new TextField();
        //field1.setBounds(250,100,200,30);
        //field1.setEditable(false);
        //add(field1);
        field2 = new TextField();
        field2.setBounds(250,140,200,30);
        add(field2);
        field3 = new TextField();
        field3.setBounds(250,180,200,30);
        add(field3);
        field4 = new TextField();
        field4.setBounds(250,220,200,30);
        add(field4);
        field5 = new TextField();
        field5.setBounds(250,260,200,30);
        add(field5);
        field6 = new TextField();
        field6.setBounds(250,300,200,30);
        add(field6);
        field7 = new TextField();
        field7.setBounds(250,340,200,30);
        add(field7);
        
        button1 = new JButton( "Terug" );
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener( kh );
        button1.setBounds(325,400,200,50);
        add(button1);
        
        button2 = new JButton( "Voeg Toe" );
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener( kh2 );
        button2.setBounds(25,400,200,50);
        add(button2);
        
    }
    
    private class ButtonHandler implements ActionListener {
        public void actionPerformed( ActionEvent e )
        {
            label1.setText("");
            controller.makeVisible("Supplier_overview");
        }
    }
    
    private class ButtonHandler2 implements ActionListener {
        public void actionPerformed( ActionEvent e )
        {
            String string2,string3,string4,string5,string6,string7;
            boolean b1 = false;
            boolean b2 = false;
            boolean b3 = false;
            boolean b4 = false;
            boolean b5 = false;
            boolean b6 = false;
            string2 = field2.getText();
            if(string2.length() > 0 && string2.length() <= 50)
                b1 = true;
            string3 = field3.getText();
            if(string3.length() > 0 && string3.length() <= 35)
                b2 = true;
            string4 = field4.getText();
            if(string4.length() > 0 && string4.length() == 6)
                b3 = true;
            string5 = field5.getText();
            if(string5.length() > 0 && string5.length() <= 25)
                b4 = true;
            string6 = field6.getText();
            if(string6.length() > 0 && string6.length() <= 40)
                b5 = true;
            string7 = field7.getText();
            if(string7.length() > 0 && string7.length() <= 15 && m.checkNumbers(string7))
                b6 = true;
            String message = "Vul de velden in: ";
                if(!b1)
                    message += "Naam : ";
                if(!b2)
                    message += "Adres : ";
                if(!b3)
                    message += "Postcode : ";
                if(!b4)
                    message += "Contactpersoon : ";
                if(!b5)
                    message += "Email : ";
                if(!b6)
                    message += "Tel nummer : ";
                message = message.substring(0, message.length() - 3);
            if(b1 && b2 && b3 && b4 && b5 && b6)
            {
                message = "Nieuwe leverancier is aangemaakt!";
                Supplier newSupplier = new Supplier(0,string2,string3,string4,string5,string6,string7);
                m.addSupplier(newSupplier);
            }
            label1.setText(message);
        }
    }
    
}
