package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import domain.Order;
import domain.OrderRow;
import domain.Supplier;
import domain.SupplierIngredient;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class OrderUpdatePanel extends JPanel {

    private final JLabel label1,label2;
    private final TextField field2, field3, field4, field5;
    private final JButton button1, button2,button3;
    private final JComboBox box1,box2,box3;
    Controller controller;
    Manager m;
    private int id = -1;
    private final JTable table;
    private final JScrollPane spTable;
    private final DefaultTableModel model;
    private Set<OrderRow> list;

    public OrderUpdatePanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Bestelling nummer:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Leverancier:", 25, 260, 200, 30, "right"));
        add(c.createLabel("Medewerker nummer:", 25, 300, 200, 30, "right"));
        
        add(c.createLabel("[max 11 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[datum jjjj-mm-dd]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 220, 120, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 260, 120, 30, "left"));
        add(c.createLabel("[max 1 getal]", 460, 300, 200, 30, "left"));
        
        add(c.createLabel("Kies een ingrediënt", 630, 140, 100, 30, "left"));
        add(c.createLabel("Aantal", 630, 220, 70, 30, "left"));
        

        label1 = new JLabel("");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        label1.setBounds(200, 450, 600, 30);
        add(label1);
        label2 = new JLabel("");
        label2.setHorizontalAlignment(SwingConstants.LEFT);
        label2.setBounds(200, 290, 250, 9);
        add(label2);
        field2 = new TextField();
        field2.setBounds(250, 140, 200, 30);
        add(field2);
        field3 = new TextField();
        field3.setBounds(250, 180, 200, 30);
        add(field3);
        String[] options = {"Aangemaakt", "Geaccepteerd", "Geleverd", "Afgerond"};
        box1 = new JComboBox(options);
        box1.setBounds(250, 220, 200, 30);
        add(box1);
        ArrayList<String> supplierNames = m.getSupplierNames();
        String[] suppliers = supplierNames.stream().toArray(String[]::new);
        box2 = new JComboBox(suppliers);
        box2.setBounds(250, 260, 200, 30);
        box2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();
                //System.out.println(comboBox.getSelectedItem());
                if(comboBox.getSelectedItem() != null ){
                    Supplier sup = m.getSupplier((String) comboBox.getSelectedItem());
                    updateTable(sup);
                }
            }
        });
        add(box2);
        field5 = new TextField();
        field5.setBounds(250, 300, 200, 30);
        add(field5);

        ArrayList<String> ingNames = m.getIngredientNames();
        String[] ingredients = ingNames.stream().toArray(String[]::new);
        box3 = new JComboBox(ingredients);
        box3.setBounds(630, 180, 200, 30);
        add(box3);
        field4 = new TextField();
        field4.setBounds(630, 260, 100, 30);
        add(field4);
        
        list = new HashSet<>();
        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            //Cellen kunnen niet aangepast worden
            public boolean isCellEditable(int row, int column) {
                if(column == 3){
                    return true;
                } else
                    return false;
            }
        ;
        }; 
        // Kolommen voor het model worden aangemaakt
        String[] colName = {"Ingrediënt", "Per hoeveelheid", "prijs", "Aantal"};
        model.setColumnIdentifiers(colName);
        // Breedte van de kolommen wordt gedefinieerd
        int[] colWidth = new int[4];
        colWidth[0] = 200;
        colWidth[1] = 200;
        colWidth[2] = 70;
        colWidth[3] = 100;

        table = new JTable(model);
        //this.refreshTable();
        spTable = new JScrollPane(table);
        spTable.setBounds(850, 140, 570, 345);
        add(spTable);

        TableColumn column;
        for (int i = 0; i < colWidth.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(colWidth[i]);
        }
        
        button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(325, 400, 200, 50);
        add(button1);

        button2 = new JButton("Wijzig");
        ButtonHandler2 kh2 = new ButtonHandler2();
        button2.addActionListener(kh2);
        button2.setBounds(25, 400, 200, 50);
        add(button2);
        
        button3 = new JButton("Voeg toe");
        ButtonHandler3 kh3 = new ButtonHandler3();
        button3.addActionListener(kh3);
        button3.setBounds(630, 300, 100, 30);
        add(button3);

    }
    
    public void updateTable(Supplier sup){
        Set<OrderRow> entireList = m.getAllOrderRows();
        Set<SupplierIngredient> supIngList = sup.getIngredientList();
        model.setRowCount(0);
        for(OrderRow i : entireList){
            for(SupplierIngredient o : supIngList) {
                if(i.getIngredient().getId() == o.getIngredient().getId() && i.getSupplier().getId() == o.getSupplier().getId() 
                        && i.getOrder().getId() == id){
                    model.addRow(new Object[]{o.getIngredient().getName(), o.getQuantity(), o.getPrice(),i.getAmount()});
                }
            }
        }
        table.setModel(model);
        model.fireTableDataChanged();
    }

    public void setOrder(Order selOrder) {
        id = selOrder.getId();
        field2.setText(selOrder.getNr());
        field3.setText(String.valueOf(selOrder.getDate()));
        box1.setSelectedIndex(selOrder.getStatusId() - 1);
        
        field5.setText(String.valueOf(selOrder.getEmployeeId()));
        if(selOrder.getSupplier() != null){
            label2.setText(selOrder.getSupplier().getName());
            box2.setSelectedItem(selOrder.getSupplier().getName());
        }
    }

    public void makeListFromTable() {
        list.clear();
        Supplier sup = m.getSupplier((String) box2.getSelectedItem());
        Order testOrder = new Order(0,"", "", 0, 0);
        for (int count = 0; count < model.getRowCount(); count++){
            OrderRow newOrderRow = new OrderRow(m.getIngredient(String.valueOf(model.getValueAt(count, 0))),testOrder,
                    sup,Integer.parseInt(model.getValueAt(count, 3).toString()));
            list.add(newOrderRow);
        }
    }
    
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("");
            controller.makeVisible("Order_overview");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (id > -1) {
                try {
                    if(field2.getText().length() <= 0 || field2.getText().length() >= 20)
                        throw new Exception("Fout in Bestellingnummer.");
                    if(!field3.getText().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
                        throw new Exception("Fout in Datum.");
                    if(field5.getText().length() != 1 || !m.checkNumbers(field5.getText()))
                        throw new Exception("Fout in Medewerkernummer.");
                    
                    Order updateOrder = m.getOrderWithNr(id);
                    updateOrder.setNr(field2.getText());
                    updateOrder.setDate(field3.getText());
                    int status = m.getOrderStatus((String) box1.getSelectedItem() + 1);
                    updateOrder.setStatusId(status);
                    updateOrder.setEmployeeId(Integer.parseInt(field5.getText()));
                    updateOrder.setSupplier(m.getSupplier((String) box2.getSelectedItem()));
                    updateOrder.setOrderRows( list);
                    for(OrderRow i : list) {
                        i.setOrder(updateOrder);
                    }                 
                    m.updateOrder(id, updateOrder, list);
                    label1.setText("Bestelling is gewijzigd!");
                } catch (Exception ex) {
                    label1.setText("Error: " + ex.getMessage());
                }
            } else {
                label1.setText("Bestelling is niet geselecteerd, ga terug naar vorige scherm!");
            }
        }
    }
    
    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /*
            Order updateOrder = m.getOrder(id);
            Ingredient checkIngredient = m.getIngredient((String) box3.getSelectedItem());
            int amount = Integer.parseInt(field4.getText());
            int added = (int) list.stream().filter(o -> o.getIngredient().getId() == checkIngredient.getId()).count();
            list.stream().filter(o -> o.getIngredient().getId() == checkIngredient.getId()).forEach(
                    o -> {
                        o.setAmount(o.getAmount() + amount);
                });
            if(added == 0){
                OrderRow orderRow = new OrderRow(checkIngredient,amount,19.95,updateOrder);
                list.add(orderRow);
            }
            refreshOrderRow();
            */
        }
    }

}
