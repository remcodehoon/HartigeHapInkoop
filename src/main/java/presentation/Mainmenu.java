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
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hoofdmenu inkoop");
        setContentPane(new MainmenuPanel(controller));
        setLayout(null);
    }
}

// Vult het frame in
class MainmenuPanel extends JPanel {

    private TextArea field1, field2, field3;
    private JButton button1, button2;
    Controller controller = null;

    public MainmenuPanel(Controller c) {
        controller = c;
        setLayout(new BorderLayout());

        field1 = new TextArea("", 1, 1, SCROLLBARS_NONE);
        field1.setBounds(25, 25, 550, 100);
        field1.setEditable(false);
        field1.setText("Welkom in het deelsysteem 'Inkoop' van de Hartige Hap\n"
                + "Hieronder staan de mogelijkheden voor het systeem"
        );
        add(field1);

        field2 = new TextArea();
        field2.setBounds(25, 170, 250, 200);
        field2.setEditable(false);
        field2.setText("Klik op de knop hieronder voor\nhet overzicht van de ingerediënten\n"
                + "Mogelijkheden:\n"
                + "- Ingrediënten bekijken\n"
                + "- Ingrediënt toevoegen\n"
                + "- Ingrediënt updaten\n"
                + "- Ingrediënt verwijderen\n");
        add(field2);

        field3 = new TextArea();
        field3.setBounds(325, 170, 250, 200);
        field3.setEditable(false);
        field3.setText("Klik op de knop hieronder voor \n het overzicht van de leveranciers \n "
                + "Mogelijkheden:\n"
                + "- Leveranciersoverzicht bekijken\n"
                + "- Leverancier toevoegen\n"
                + "- Leverancier updaten\n"
                + "- Leverancier verwijderen\n");
        add(field3);

        button1 = new JButton("Ingrediënten");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(25, 400, 200, 50);
        add(button1);

        button2 = new JButton("Leveranciers");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(325, 400, 200, 50);
        add(button2);
    }

    private class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Ingredient_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Supplier_overview");
        }
    }
}
