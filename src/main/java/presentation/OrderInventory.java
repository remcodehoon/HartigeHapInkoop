/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businesslogic.Manager;
import domain.InventoryItem;
import domain.IvItemTable;
import domain.Supplier;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Mart
 */
public class OrderInventory extends JPanel {
    private final JLabel label1;
    private final TextField field1;
    private final JButton button1, button2, button3, button4;
    private final JComboBox box1, box2;
    private Set<InventoryItem> list;
    Controller c;
    Manager m;
    private final JTable table;
    private final JScrollPane spTable;
    private final DefaultTableModel model;

    public OrderInventory(Controller controller, Manager m) {
        c = controller;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Ingredientnaam: [max 45 char]", 150, 100, 200, 30, "left"));
        field1 = new TextField();
        field1.setBounds(150, 140, 200, 30);
        add(field1);
        button1 = new JButton("Voeg Toe");
        ButtonHandler1 kh1 = new ButtonHandler1();
        button1.addActionListener(kh1);
        button1.setBounds(150, 180, 95, 30);
        add(button1);
        button2 = new JButton("Verwijder");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(250, 180, 95, 30);
        add(button2);
        
        add(c.createLabel("Inventaris item bij leverancier:", 150, 250, 200, 30, "left"));
        ArrayList<String> arrayNames = m.getInventoryItemNames();
        String[] names = arrayNames.stream().toArray(String[]::new);
        box1 = new JComboBox(names);
        box1.setBounds(150, 280, 200, 30);
        add(box1);
        ArrayList<String> supNames = m.getSupplierNames();
        String[] suppnames = supNames.stream().toArray(String[]::new);
        box2 = new JComboBox(suppnames);
        box2.setBounds(150, 330, 200, 30);
        add(box2);
        
        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            //Cellen kunnen niet aangepast worden
            public boolean isCellEditable(int row, int column) {
                if(column >= 2){
                    return true;
                } else
                    return false;
            }
        ;
        }; 
        String[] colName = {"Item", "Leverancier","Per Hoeveelheid", "Prijs", "Aantal"};
        model.setColumnIdentifiers(colName);
        int[] colWidth = new int[5];
        colWidth[0] = 150;
        colWidth[1] = 200;
        colWidth[2] = 150;
        colWidth[3] = 50;
        colWidth[4] = 50;

        table = new JTable(model);
        //this.refreshTable();
        spTable = new JScrollPane(table);
        spTable.setBounds(400, 140, 600, 345);
        add(spTable);

        TableColumn column;
        for (int i = 0; i < colWidth.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(colWidth[i]);
        }
        
        button3 = new JButton("Voeg Toe");
        ButtonHandler3 kh3 = new ButtonHandler3();
        button3.addActionListener(kh3);
        button3.setBounds(150, 370, 95, 30);
        add(button3);
        button4 = new JButton("Verwijder geselecteerde rij in tabel");
        ButtonHandler4 kh4 = new ButtonHandler4();
        button4.addActionListener(kh4);
        button4.setBounds(250, 370, 95, 30);
        add(button4);
        
        label1 = new JLabel();
        label1.setBounds(100, 410, 300, 30);
        add(label1);
    }
    
    
    private class ButtonHandler1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String string1 = field1.getText();
                if(string1.length() <= 0 || string1.length() > 45 )
                    throw new Exception("Fout in Naam.");
                InventoryItem newItem = new InventoryItem(m.getNewId("InventoryItem") ,string1);
                m.addInventoryItem(newItem);
                updateBox();
                label1.setText("Inventaris Item toegevoegd");
            }   catch (Exception ex) {
                label1.setText(ex.getMessage());
            }
        }
    }
    
    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String string1 = field1.getText();
                if(string1.length() <= 0 || string1.length() > 45 )
                    throw new Exception("Fout in Naam.");
                InventoryItem newItem = new InventoryItem(0 ,string1);
                m.deleteInventoryItem(newItem);
                updateBox();
                label1.setText("Inventaris Item verwijderd");
            }   catch (Exception ex) {
                label1.setText(ex.getMessage());
            }
        }
    }
    
    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String string1 = (String) box1.getSelectedItem();
                String string2 = (String) box1.getSelectedItem();
                model.addRow(new Object[]{string1,string2, 0,0,0});
                table.setModel(model);
                model.fireTableDataChanged();
            }   catch (Exception ex) {
                Logger.getLogger(OrderInventory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class ButtonHandler4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int row = table.getSelectedRow();
                if (row != -1) {
                    String index = table.getValueAt(row, 0).toString();
                    int[] selectedRows = table.getSelectedRows();
                    if (selectedRows.length > 0) {
                        for (int i = selectedRows.length - 1; i >= 0; i--) {
                            model.removeRow(selectedRows[i]);
                            model.fireTableDataChanged();
                        }
                    }
                } else {
                    label1.setText("Selecteer eerst een regel!");
                }
            }   catch (Exception ex) {
                label1.setText(ex.getMessage());
            }
        }
    }
        
    public void updateBox(){
        ArrayList<String> arrayNames = m.getInventoryItemNames();
        box1.removeAllItems();
        for(String str : arrayNames) {
           box1.addItem(str);
        }
    }
    
    public void makeTable(){
        model.setRowCount(0);
        for(IvItemTable o : m.getInventoryTable()) {
            model.addRow(new Object[]{o.getName(),m.getSupplier(o.getSupId()).getName(), o.getAmount(), o.getPrice(),""});
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }


}
