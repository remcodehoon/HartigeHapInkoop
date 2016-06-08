package presentation;


import businesslogic.Manager;
import domain.Ingredient;
import domain.Order;
import domain.OrderRow;
import domain.Supplier;
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
    private final TextField field2, field3, field4;
    private final JButton button1, button2, button3;
    private final JComboBox box1, box2, box3;
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
        
        add(c.createLabel("Kies een ingrediënt", 630, 140, 100, 30, "left"));
        add(c.createLabel("Aantal", 630, 220, 70, 30, "left"));
        
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
        String[] options = {"Aangemaakt", "Geaccepteerd", "Geleverd", "Afgerond"};
        box1 = new JComboBox(options);
        box1.setBounds(250, 220, 200, 30);
        add(box1);
        ArrayList<String> supplierNames = m.getSupplierNames();
        String[] suppliers = supplierNames.stream().toArray(String[]::new);
        box2 = new JComboBox(suppliers);
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
        
        button3 = new JButton("Voeg toe");
        ButtonHandler3 kh3 = new ButtonHandler3();
        button3.addActionListener(kh3);
        button3.setBounds(630, 300, 100, 30);
        add(button3);
        
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
                return false;
            }
        ;
        }; 
        // Kolommen voor het model worden aangemaakt
        String[] colName = {"Ingrediënt", "Hoeveelheid", "prijs"};
        model.setColumnIdentifiers(colName);
        // Breedte van de kolommen wordt gedefinieerd
        int[] colWidth = new int[3];
        colWidth[0] = 120;
        colWidth[1] = 100;
        colWidth[2] = 50;

        table = new JTable(model);
        //this.refreshTable();
        spTable = new JScrollPane(table);
        spTable.setBounds(850, 140, 270, 345);
        add(spTable);

        TableColumn column;
        for (int i = 0; i < colWidth.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(colWidth[i]);
        }

    }
    
    public void refreshOrderRow() {
        model.setRowCount(0);
        for(OrderRow o : list) {
            model.addRow(new Object[]{o.getIngredient().getName(), o.getAmount(), o.getPrize()});
        }
        table.setModel(model);
        model.fireTableDataChanged();
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
                int fkey = sup.getId();
                int empId = m.getEmployeeId();
                Order newOrder = new Order(Integer.parseInt(string1), string2, status, empId, fkey);
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
    
    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Order newOrder = new Order(0, "", 0, 0, 0);
            //Order updateOrder = m.getOrder(id);
            Ingredient checkIngredient = m.getIngredient((String) box3.getSelectedItem());
            int amount = Integer.parseInt(field4.getText());
            int added = (int) list.stream().filter(o -> o.getIngredient().getId() == checkIngredient.getId()).count();
            list.stream().filter(o -> o.getIngredient().getId() == checkIngredient.getId()).forEach(
                    o -> {
                        o.setAmount(o.getAmount() + amount);
                });
            if(added == 0){
                OrderRow orderRow = new OrderRow(checkIngredient,amount,19.95,newOrder);
                list.add(orderRow);
            }
            refreshOrderRow();
        }
    }

}
