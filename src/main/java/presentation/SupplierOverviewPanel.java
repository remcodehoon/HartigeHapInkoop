/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businesslogic.Manager;
import datastore.SupplierDAO;
import domain.Supplier;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
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
public class SupplierOverviewPanel extends JPanel {

    private JButton button1, button2, button3, button4, button5;
    private TextField field1;
    private JLabel label1;
    Controller controller = null;
    private JTable table;
    private JScrollPane spTable;
    private Manager m;
    private DefaultTableModel model;

    public SupplierOverviewPanel(Controller c) {
        controller = c;
        m = new Manager();
        setLayout(null);

        label1 = new JLabel("");
        label1.setBounds(200, 450, 600, 30);
        add(label1);

        // Voegt een model toe die de vorm is van de JTable in een GUI.
        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        ;
        };
        // Kolommen voor het model worden aangemaakt
        String[] colName = {"ID", "Name", "Postal", "Address", "Phone Number", "Contact Person", "E-mail"};
        model.setColumnIdentifiers(colName);
        // Breedte van de kolommen wordt gedefinieerd
        int[] colWidth = new int[7];
        colWidth[0] = 75;
        colWidth[1] = 165;
        colWidth[2] = 165;
        colWidth[3] = 165;
        colWidth[4] = 165;
        colWidth[5] = 165;
        colWidth[6] = 165;

        table = new JTable();
        this.refreshTable();
        TableColumn column = null;
        for (int i = 0; i < 7; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(colWidth[i]);
        }

        spTable = new JScrollPane(table);
        spTable.setBounds(25, 55, 900, 345);
        add(spTable);

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
        button4.setBounds(500, 10, 80, 40);
        add(button4);

        field1 = new TextField(100);
        field1.setBounds(100, 10, 300, 25);
        add(field1);

        button5 = new JButton("Zoek");
        ButtonHandler5 kh5 = new ButtonHandler5();
        button5.addActionListener(kh5);
        button5.setBounds(400, 10, 90, 25);
        add(button5);
    }

    /**
     * Refreshes the JTable.
     */
    public void refreshTable() {
        //refresh ook het label
        label1.setText("");
        //zet het aantal rijen van de tabel op 0
        model.setRowCount(0);
        // Een lijst van leveranciers worden via een DAO opgehaald.
        SupplierDAO dao = new SupplierDAO();
        ArrayList<Supplier> supplierList = dao.getAllSuppliers();
        // Per leverancier: stop de waarden in een rij (row) van het model.
        for (Supplier s : supplierList) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getPostalCode(), s.getAddress(), s.getPhoneNo(), s.getContactName(), s.getEmail()});
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    private class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Supplier_add");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int index = Integer.parseInt(table.getValueAt(row, 0).toString());
                Supplier sel = m.getSupplier(index);
                controller.makeVisible("Supplier_update", sel);
            } else {
                label1.setText("Selecteer eerst een product om te updaten!");
            }
        }
    }

    private class ButtonHandler3 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int index = Integer.parseInt(table.getValueAt(row, 0).toString());
                Supplier sel = m.getSupplier(index);
                controller.makeVisible("Supplier_delete", sel);
            } else {
                label1.setText("Selecteer eerst een product om te deleten!");
            }
        }
    }

    private class ButtonHandler4 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Mainmenu");
        }
    }

    private class ButtonHandler5 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println("test");
        }
    }
}
