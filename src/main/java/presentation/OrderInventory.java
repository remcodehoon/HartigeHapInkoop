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
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
    private final JButton button1, button2, button3, button4, button5, button6, button7;
    private final JComboBox box1, box2;
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
        table.setAutoCreateRowSorter(true);
        spTable = new JScrollPane(table);
        spTable.setBounds(400, 140, 600, 410);
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
        button4 = new JButton("Verwijder");
        ButtonHandler4 kh4 = new ButtonHandler4();
        button4.addActionListener(kh4);
        button4.setBounds(250, 370, 95, 30);
        add(button4);
        button5 = new JButton("Terug");
        ButtonHandler5 kh5 = new ButtonHandler5();
        button5.addActionListener(kh5);
        button5.setBounds(150, 440, 200, 30);
        add(button5);
        button7 = new JButton("Sla lijst op");
        ButtonHandler7 kh7 = new ButtonHandler7();
        button7.addActionListener(kh7);
        button7.setBounds(150, 480, 200, 30);
        add(button7);
        button6 = new JButton("Print lijst");
        ButtonHandler6 kh6 = new ButtonHandler6();
        button6.addActionListener(kh6);
        button6.setBounds(150, 520, 200, 30);
        add(button6);
        
        label1 = new JLabel();
        label1.setBounds(150, 570, 300, 30);
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        add(label1);
    }
    
    
    private class ButtonHandler1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String string1 = field1.getText();
                if(string1.length() <= 0 || string1.length() > 45 )
                    throw new Exception("Fout in Naam!");
                InventoryItem newItem = new InventoryItem(m.getNewId("InventoryItem") ,string1);
                m.addInventoryItem(newItem);
                updateBox();
                label1.setText("Inventaris Item toegevoegd!");
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
                    throw new Exception("Fout in Naam!");
                InventoryItem newItem = new InventoryItem(0 ,string1);
                m.deleteInventoryItem(newItem);
                updateBox();
                label1.setText("Inventaris Item verwijderd!");
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
                String string2 = (String) box2.getSelectedItem();
                for (int count = 0; count < model.getRowCount(); count++){
                    if(string1.equals((String) model.getValueAt(count, 0)) && string2.equals((String) model.getValueAt(count, 1)))
                        throw new Exception("Combinatie item/leverancier is al gedefinieerd!");
                }
                model.addRow(new Object[]{string1,string2, 0,0,0});
                table.setModel(model);
                model.fireTableDataChanged();
                label1.setText("Bestelregel toegevoegd!");
            }   catch (Exception ex) {
                label1.setText(ex.getMessage());
            }
        }
    }
    
    private class ButtonHandler4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int row = table.getSelectedRow();
                if (row != -1) {
                    int[] selectedRows = table.getSelectedRows();
                    if (selectedRows.length > 0) {
                        for (int i = selectedRows.length - 1; i >= 0; i--) {
                            model.removeRow(selectedRows[i]);
                            model.fireTableDataChanged();
                            label1.setText("Bestelregel verwijderd!");
                        }
                    }
                } else {
                    label1.setText("Selecteer eerst een bestelregel!");
                }
            }   catch (Exception ex) {
                label1.setText(ex.getMessage());
            }
        }
    }
        
    private class ButtonHandler5 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("");
            c.makeVisible("Supplier_overview");
        }
    }
    
    private class ButtonHandler6 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("Start printen!");
            MessageFormat header = new MessageFormat("Inventaris bestellijst");
            MessageFormat footer = new MessageFormat("Pagina{0,number,integer}");
            
            try {
                table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                label1.setText("Bestellijst geprinten!");
            } catch (PrinterException ex) {
                label1.setText("Printerror: " + ex.getMessage());
            } 
        }
    }
    
    private class ButtonHandler7 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Set<IvItemTable> list = makeListFromTable();
            m.setInventoryItemList(list);
            label1.setText("Lijst is opgeslagen in de database!");
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

    private Set<IvItemTable> makeListFromTable(){
        Set<IvItemTable> list = new HashSet<>();
        for (int count = 0; count < model.getRowCount(); count++){
            int a = m.getInventoryItemid(String.valueOf(model.getValueAt(count, 0)));
            String e = String.valueOf(model.getValueAt(count, 1));
            Supplier sup = m.getSupplier(e);
            double f = Double.parseDouble(String.valueOf(model.getValueAt(count, 3)));
            int c1 = Integer.parseInt(String.valueOf(model.getValueAt(count, 2)));
            String d = String.valueOf(model.getValueAt(count, 0));
            IvItemTable newItem = new IvItemTable(a,sup.getId(),f,c1,d);
            if(newItem.getAmount() > 0)
                list.add(newItem);
        }
        return list;
    }
}
