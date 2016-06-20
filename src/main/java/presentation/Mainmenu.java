package presentation;

import java.awt.BorderLayout;
import java.awt.TextArea;
import static java.awt.TextArea.SCROLLBARS_NONE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Geeft de waardes aan het frame Mainmenu mee
public class Mainmenu extends JFrame {

    Controller controller = null;

    public Mainmenu(Controller c) {
        controller = c;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hoofdmenu inkoop");
        setContentPane(new MainmenuPanel(controller));
        setLayout(null);
    }
}

class MainmenuPanel extends JPanel {

    private final TextArea field1, field2, field3, field4;
    private final JButton button1, button2, button3;
    Controller controller;

    public MainmenuPanel(Controller c) {
        controller = c;
        setLayout(new BorderLayout());

        field1 = new TextArea("", 1, 1, SCROLLBARS_NONE);
        field1.setBounds(25, 25, 775, 100);
        field1.setEditable(false);
        field1.setText("Welkom in het deelsysteem 'Inkoop' van de Hartige Hap\n"
                + "Hieronder staan de mogelijkheden voor het systeem"
        );
        add(field1);

        field2 = new TextArea();
        field2.setBounds(25, 200, 250, 200);
        field2.setEditable(false);
        field2.setText("Klik op de knop hieronder voor\nhet overzicht van de ingerediënten\n"
                + "Mogelijkheden:\n"
                + "- Ingrediënten bekijken\n"
                + "- Ingrediënt toevoegen\n"
                + "- Ingrediënt updaten\n"
                + "- Ingrediënt verwijderen\n");
        add(field2);

        field3 = new TextArea();
        field3.setBounds(285, 200, 250, 200);
        field3.setEditable(false);
        field3.setText("Klik op de knop hieronder voor \n het overzicht van de leveranciers \n "
                + "Mogelijkheden:\n"
                + "- Leveranciersoverzicht bekijken\n"
                + "- Leverancier & Bestelregels toevoegen\n"
                + "- Leverancier & Bestelregels updaten\n"
                + "- Leverancier & Bestelregels verwijderen\n");
        add(field3);
        
        field4 = new TextArea();
        field4.setBounds(545, 200, 250, 200);
        field4.setEditable(false);
        field4.setText("Klik op de knop hieronder voor \n het overzicht van de bestellingen \n "
                + "Mogelijkheden:\n"
                + "- Bestellingsoverzicht bekijken\n"
                + "- Bestelling & Bestelregels toevoegen\n"
                + "- Bestelling & Bestelregels updaten\n"
                + "- Bestelling & Bestelregels verwijderen\n"
                + "\n"
                + "- Bestellijst inventaris maken\n"
                + "- Bestellijst inventaris printen"
                + "");
        add(field4);

        button1 = new JButton("Ingrediënten");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(25, 400, 250, 50);
        add(button1);

        button2 = new JButton("Leveranciers");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(285, 400, 250, 50);
        add(button2);
        
        button3 = new JButton("Bestellingen");
        ButtonHandler3 kh3 = new ButtonHandler3();
        button3.addActionListener(kh3);
        button3.setBounds(545, 400, 250, 50);
        add(button3);
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Ingredient_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Supplier_overview");
        }
    }
    
    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Order_overview");
        }
    }
}

