package presentation;

import businesslogic.Manager;
import domain.Employee;
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

    private final JLabel label1;
    private final TextField field2, field3;
    private final JButton button1, button2;
    private final JComboBox box1,box2,box3;
    Controller controller;
    Manager m;
    private int id = -1;
    private Order updateOrder;
    private final JTable table;
    private final JScrollPane spTable;
    private final DefaultTableModel model;
    private Set<OrderRow> orderRowList;
    private Set<SupplierIngredient> supIngList;

    public OrderUpdatePanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);
        
        supIngList = new HashSet<>();
        orderRowList = new HashSet<>();
        updateOrder = null;
        add(c.createLabel("Bestelling nummer:*", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:*", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:*", 25, 220, 200, 30, "right"));
        add(c.createLabel("Leverancier:*", 25, 260, 200, 30, "right"));
        add(c.createLabel("Medewerker nummer:*", 25, 300, 200, 30, "right"));
        
        add(c.createLabel("[max 11 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[datum jjjj-mm-dd]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 220, 120, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 260, 120, 30, "left"));
        add(c.createLabel("[max 2 getallen]", 460, 300, 200, 30, "left"));

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
        String[] options = {"Geplaatst", "Geaccepteerd", "Klaar voor bezorgen", "Bezorgd","Wacht op afrekenen","Betaald","Afwachten","Wacht op plaatsing"}; 
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
                if(comboBox.getSelectedItem() != null ){
                    Supplier sup = m.getSupplier((String) comboBox.getSelectedItem());
                    updateTable(sup);
                    fillTable(sup);
                }
            }
        });
        add(box2);
        
        box3 = new JComboBox();
        box3.setBounds(250, 300, 200, 30);
        updateBox();
        add(box3);

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
        spTable.setBounds(630, 140, 570, 345);
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
    }
    
    public void updateBox(){
        box3.removeAllItems();
        for(Employee i : m.getEmployees()){
            box3.addItem(i.getName());
        }
    }
    
    public void updateTable(Supplier sup){
        
        Set<SupplierIngredient> supIngList = m.getSupplierIngredientList(sup);
        model.setRowCount(0);
        int supId = sup.getId();
        for(SupplierIngredient o : supIngList) {
            if(supId == o.getSupplier().getId()){
                model.addRow(new Object[]{o.getIngredient().getName(), o.getQuantity(), o.getPrice(),0});
            }
        }
        
        table.setModel(model);
        model.fireTableDataChanged();
    }
    
    public void fillTable(Supplier sup){
        Set<OrderRow> entireList = orderRowList;
        entireList.removeIf(o -> o.getOrder().getId() != id);
        int supId = sup.getId();

        for (int count = 0; count < model.getRowCount(); count++){
            for(OrderRow i : entireList){
                if(i.getIngredient().getName().equals(model.getValueAt(count, 0))){
                    model.setValueAt(i.getAmount(), count, 3);
                }
            }
        }
    }

    public void setOrder(Order selOrder) {
        
        id = selOrder.getId();
        updateOrder = m.getOrderWithId(id);
        field2.setText(selOrder.getNr());
        field3.setText(String.valueOf(selOrder.getDate()));
        box1.setSelectedIndex(selOrder.getStatusId() - 1);
        orderRowList = m.getAllOrderRows();
        orderRowList.removeIf(o -> o.getOrder().getId() != selOrder.getId());
                
        if(orderRowList.size() > 0){
            OrderRow test = orderRowList.stream().findFirst().get();
            updateOrder.setSupplier(test.getSupplier());
            supIngList = m.getSupplierIngredientList(test.getSupplier());
            selOrder.setSupplier(m.getOrderWithId(id).getSupplier());
            box2.setSelectedItem(test.getSupplier().getName());
            updateTable(test.getSupplier());
            fillTable(test.getSupplier());
        }
        
        String empName = m.getEmployeeName(selOrder.getEmployeeId());
        box3.setSelectedItem(empName);
    }

    public void makeListFromTable() {
        orderRowList.clear();
        Supplier sup = m.getSupplier((String) box2.getSelectedItem());
        Order testOrder = m.getOrderWithId(id);
        for (int count = 0; count < model.getRowCount(); count++){
            
            OrderRow newOrderRow = new OrderRow(m.getIngredient(String.valueOf(model.getValueAt(count, 0))),testOrder,
                    sup,Integer.parseInt(model.getValueAt(count, 3).toString()));
            if(newOrderRow.getAmount() > 0)
                orderRowList.add(newOrderRow);
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
                    
                    updateOrder.setNr(field2.getText());
                    updateOrder.setDate(field3.getText());
                    int status = m.getOrderStatus(String.valueOf(box1.getSelectedItem()));
                    updateOrder.setStatusId(status);
                    int empId = m.getEmployeeId(String.valueOf(box3.getSelectedItem()));
                    updateOrder.setEmployeeId(empId);
                    Supplier sup = m.getSupplier((String) box2.getSelectedItem());
                    updateOrder.setSupplier(sup);
                    makeListFromTable();
                    updateOrder.setOrderRows( orderRowList);
                    for(OrderRow i : orderRowList) {
                        i.setOrder(updateOrder);
                    }
                    if(m.checkTotalPrice(orderRowList) > 1000)
                        if(m.getEmployeeFunctionId(m.getEmployeeId()) != 8)
                            throw new Exception("Bestelling is te duur, log in als chefkok.");
                    m.updateOrder(id, updateOrder, orderRowList);
                    m.updateSupplierIngredientList();
                    label1.setText("Bestelling is gewijzigd!");
                } catch (Exception ex) {
                    label1.setText("Error: " + ex.getMessage());
                }
            } else {
                label1.setText("Bestelling is niet geselecteerd, ga terug naar vorige scherm!");
            }
        }
    }

}
