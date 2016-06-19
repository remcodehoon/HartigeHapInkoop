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

public class OrderAddPanel extends JPanel {

    private final JLabel label1;
    private final TextField field2, field3;
    private final JButton button1, button2;
    private final JComboBox box1, box2;
    Controller controller;
    Manager m;
    private final JTable table;
    private final JScrollPane spTable;
    private final DefaultTableModel model;
    private Set<OrderRow> list;

    public OrderAddPanel(Controller c, Manager m) {
        controller = c;
        this.m = m;
        setLayout(null);

        add(c.createLabel("Bestellingnummer:", 25, 140, 200, 30, "right"));
        add(c.createLabel("Datum:", 25, 180, 200, 30, "right"));
        add(c.createLabel("Status:", 25, 220, 200, 30, "right"));
        add(c.createLabel("Leverancier:", 25, 260, 200, 30, "right"));

        add(c.createLabel("[max 20 char]", 460, 140, 200, 30, "left"));
        add(c.createLabel("[datum jjjj-mm-dd]", 460, 180, 200, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 220, 120, 30, "left"));
        add(c.createLabel("[selecteer 1]", 460, 260, 120, 30, "left"));
        
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
        box2.setBounds(250, 260, 200, 30);
        add(box2);
        
        button1 = new JButton("Terug");
        ButtonHandler kh = new ButtonHandler();
        button1.addActionListener(kh);
        button1.setBounds(325, 400, 200, 50);
        add(button1);

        button2 = new JButton("Voeg toe");
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
    }
    
    public void updateTable(Supplier sup){
        Set<SupplierIngredient> orderRowList = m.getSupplierIngredientList(sup);
        model.setRowCount(0);
        for(SupplierIngredient o : orderRowList) {
            model.addRow(new Object[]{o.getIngredient().getName(), o.getQuantity(), o.getPrice(),""});
        }
        table.setModel(model);
        model.fireTableDataChanged();
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
    
    public void setOrder(){    
        ArrayList<String> supplierNames = m.getSupplierNames();
        String[] suppliers = supplierNames.stream().toArray(String[]::new);
        box2.removeAllItems();
        for(String str : suppliers) {
           box2.addItem(str);
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
            String message = "";
            try {
                String string1, string2;
                string1 = field2.getText();
                string2 = field3.getText();
                int status = m.getOrderStatus((String) box1.getSelectedItem());
                if(string1.length() <= 0 || string1.length() >= 20)
                    throw new Exception("Fout in Bestellingnummer.");
                if(!string2.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
                    throw new Exception("Fout in Datum.");
                Supplier sup = m.getSupplier((String) box2.getSelectedItem());
                int empId = m.getEmployeeId();
                Order newOrder = new Order(m.getNewId("Order"),string1, string2, status, empId);
                makeListFromTable();
                newOrder.setSupplier(sup);
                newOrder.setOrderRows(list);
                for(OrderRow i : list) {
                    i.setOrder(newOrder);
                }
                m.addOrder(newOrder);
                message = "Bestelleing toegevoegd.";
            } catch (Exception f){
                message = f.getMessage();
            } finally {
                label1.setText(message);
            }
        }
    }
}
