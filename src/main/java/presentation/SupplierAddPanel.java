package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import domain.OrderRow;
import domain.Supplier;
import domain.SupplierIngredient;
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

public class SupplierAddPanel extends JPanel {

    private final JLabel label1;
    private final TextField field2, field3, field4, field5, field6, field7;
    private final TextField field21,field22;
    private final JButton button1, button2, button3, button4;
    private final JComboBox box1;
    Controller controller;
    private final Manager m;
    private final JTable table;
    private final JScrollPane spTable;
    private final DefaultTableModel model;
    private Set<SupplierIngredient> list;

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
        add(c.createLabel("[max 6 char]", 460, 220, 160, 30, "left"));
        add(c.createLabel("[max 45 char]", 460, 260, 160, 30, "left"));
        add(c.createLabel("[max 45 char]", 460, 300, 160, 30, "left"));
        add(c.createLabel("[max 14 getallen]", 460, 340, 160, 30, "left"));
        
        add(c.createLabel("Kies een ingrediënt", 630, 140, 220, 30, "left"));
        add(c.createLabel("Aantal [max 11 getallen]", 630, 220, 220, 30, "left"));
        add(c.createLabel("Prijs [max 8 getallen]", 630, 300, 220, 30, "left"));

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
        
        list = new HashSet<>();
        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            //Cellen kunnen niet aangepast worden
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        ;
        }; 
        String[] colName = {"Ingrediënt", "Aantal", "Prijs"};
        model.setColumnIdentifiers(colName);
        table = new JTable(model);
        spTable = new JScrollPane(table);
        spTable.setBounds(850, 140, 350, 345);
        add(spTable);
        
        int[] colWidth = new int[3];
        colWidth[0] = 200;
        colWidth[1] = 100;
        colWidth[2] = 50;
        TableColumn column;
        for (int i = 0; i < colWidth.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(colWidth[i]);
        }
        
        ArrayList<String> ingNames = m.getIngredientNames();
        String[] ingredients = ingNames.stream().toArray(String[]::new);
        box1 = new JComboBox(ingredients);
        box1.setBounds(630, 180, 200, 30);
        add(box1);
        
        field21 = new TextField();
        field21.setBounds(630, 260, 200, 30);
        add(field21);
        field22 = new TextField();
        field22.setBounds(630, 340, 200, 30);
        add(field22);
        
        button3 = new JButton("Voeg Toe");
        ButtonHandler3 kh3 = new ButtonHandler3();
        button3.addActionListener(kh3);
        button3.setBounds(630, 380, 95, 30);
        add(button3);
        
        button4 = new JButton("Delete");
        ButtonHandler4 kh4 = new ButtonHandler4();
        button4.addActionListener(kh4);
        button4.setBounds(730, 380, 80, 30);
        add(button4);

    }
    
    public void setSupplier(){
        ArrayList<String> ingNames = m.getIngredientNames();
        String[] ingredients = ingNames.stream().toArray(String[]::new);
        box1.removeAllItems();
        for(String str : ingredients) {
           box1.addItem(str);
        }
    }
    
    public void refreshOrderRow() {
        model.setRowCount(0);
        for(SupplierIngredient o : list) {
            model.addRow(new Object[]{o.getIngredient().getName(), o.getQuantity(), o.getPrice()});
        }
        table.setModel(model);
        model.fireTableDataChanged();
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
                m.addSupplierRows(list);
                message = "Nieuwe leverancier is aangemaakt!";
            } catch (Exception f){
                message = f.getMessage();  
            } finally {
                label1.setText(message);
            }
        }
    }
    
    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String string1, string2;
                string1 = field21.getText();
                string2 = field22.getText();
                if(string1.length() > 11 || string1.length() < 1 || !m.checkNumbers(string1))
                    throw new Exception("Fout in Aantal.");
                if(string2.length() > 9 || string1.length() < 1)
                    throw new Exception("Fout in Prijs.");
                Supplier testSupplier = new Supplier(m.getSupplierMaxId(), "", "", "", "", "", "");
                Ingredient checkIngredient = m.getIngredient((String) box1.getSelectedItem());
                int amount = Integer.parseInt(field21.getText());
                double prise = Double.parseDouble(field22.getText());
                int added = (int) list.stream().filter(o -> o.getIngredient().getId() == checkIngredient.getId()).count();
                if(added == 0){
                    SupplierIngredient supIngredient = new SupplierIngredient(checkIngredient,testSupplier,amount,prise);
                    list.add(supIngredient);
                    label1.setText("Bestelregel aan bestellijst toegevoegd");
                    refreshOrderRow();
                } else if (added > 0){
                    label1.setText("De Bestelregel van dit ingrediënt is al gedefinieerd");
                }
            }catch (Exception ex) {
                label1.setText(ex.toString());
            }
        }
    }    
    
    private class ButtonHandler4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                String index = table.getValueAt(row, 0).toString();
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        model.removeRow(selectedRows[i]);
                    }
                }
                list.stream().forEach((i) -> {
                    if(i.getIngredient().getName().equals(index)){
                        list.remove(i);
                        label1.setText("Item uit bestellijst verwijderd");
                    }
                });
            } else {
                label1.setText("Selecteer eerst een product om te verwijderen!");
            }
        }
    }

}
