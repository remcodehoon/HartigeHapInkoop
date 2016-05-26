package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import domain.Order;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Mart
 */

public class OrderOverviewPanel extends JPanel {

    private final JButton button1, button2, button3, button4, button5;
    private final TextField field1;
    private final JComboBox box1;
    private JLabel label1;
    Controller controller;
    private final JTable table;
    private final JScrollPane spTable;
    Manager m;
    private final DefaultTableModel model;

    public OrderOverviewPanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        label1 = new JLabel("");
        label1.setBounds(200, 450, 600, 30);
        add(label1);

        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            //Cellen kunnen niet aangepast worden
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        ;
        }; 
        // Kolommen voor het model worden aangemaakt
        String[] colName = {"Bestelling nummer", "Datum", "Status", "Gebruiker nummer","Leverancier"};
        model.setColumnIdentifiers(colName);
        // Breedte van de kolommen wordt gedefinieerd
        int[] colWidth = new int[5];
        colWidth[0] = 130;
        colWidth[1] = 130;
        colWidth[2] = 130;
        colWidth[3] = 130;
        colWidth[4] = 130;

        table = new JTable(model);
        this.refreshTable();
        spTable = new JScrollPane(table);
        spTable.setBounds(25, 55, 750, 345);
        add(spTable);

        TableColumn column;
        for (int i = 0; i < colWidth.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(colWidth[i]);
        }

        button1 = new JButton("Voeg Toe");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(25, 400, 150, 50);
        add(button1);

        button2 = new JButton("Wijzig");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(225, 400, 150, 50);
        add(button2);

        button3 = new JButton("Verwijder");
        ButtonHandler3 kh3 = new ButtonHandler3();
        button3.addActionListener(kh3);
        button3.setBounds(425, 400, 150, 50);
        add(button3);

        button4 = new JButton("Terug");
        ButtonHandler4 kh4 = new ButtonHandler4();
        button4.addActionListener(kh4);
        button4.setBounds(600, 10, 80, 40);
        add(button4);

        field1 = new TextField(100);
        field1.setBounds(50, 10, 280, 40);
        add(field1);

        box1 = new JComboBox(colName);
        box1.setBounds(340, 10, 150, 40);
        add(box1);

        button5 = new JButton("Zoek");
        ButtonHandler5 kh5 = new ButtonHandler5();
        button5.addActionListener(kh5);
        button5.setBounds(500, 10, 90, 40);
        add(button5);

    }

    /**
     * Refreshes the JTable.
     */
    public void refreshTable() {
        label1.setText("");
        model.setRowCount(0);
        Set<Order> orderList = m.updateTableOrder();
        for(Order o : orderList) {
            model.addRow(new Object[]{o.getNr(), o.getDate(), m.getOrderStatus(o.getStatusId()), o.getEmployeeId(), m.getSupplier(o.getFkey()).getName()});
        }
        table.setModel(model);
        model.fireTableDataChanged();

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Order_add");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                if(table.getValueAt(row, 2).equals("Geaccepteerd")){
                   label1.setText("Geaccepteerde orders kunnen niet gewijzigd worden"); 
                } else {
                    int index = Integer.parseInt(table.getValueAt(row, 0).toString());
                    Order sel = m.getOrder(index);
                    controller.makeVisible("Order_update", sel);
                }
            } else {
                label1.setText("Selecteer eerst een order om te updaten!");
            }
        }
    }

    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int index = Integer.parseInt(table.getValueAt(row, 0).toString());
                Order selOrder = m.getOrder(index);
                controller.makeVisible("Order_delete", selOrder);
            }
            label1.setText("Selecteer eerst een order om te deleten!");
        }
    }

    private class ButtonHandler4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Mainmenu");
        }
    }

    private class ButtonHandler5 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String what = field1.getText();
            String attribute = (String) box1.getSelectedItem();

            if(what.equals("")) {
                label1.setText("Vul een zoekwoord in!");
            } else {
                label1.setText("");
                model.setRowCount(0);
                Set<Ingredient> ingredientList = m.getSearchedIng(what,attribute);
                // Per ingredient: stop de waarden in een rij (row) van het model.
                for(Ingredient i : ingredientList) {
                   model.addRow(new Object[]{i.getId(), i.getName(), i.getInStock(), i.getMinStock(), i.getMaxStock()});
                }
                table.setModel(model);
                model.fireTableDataChanged();
            }
        }
    }

}

