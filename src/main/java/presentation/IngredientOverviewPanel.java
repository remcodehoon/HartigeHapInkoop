package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class IngredientOverviewPanel extends JPanel {

    private final JButton button1, button2, button3, button4, button5;
    private final TextField field1;
    private final JComboBox box1;
    private JLabel label1;
    Controller controller;
    private final JTable table;
    private final JScrollPane spTable;
    Manager m;
    private final DefaultTableModel model;

    public IngredientOverviewPanel(Controller c, Manager m) {
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
        String[] colName = {"ID", "Naam", "Voorraad", "Minimum Voorraad", "Maximum Voorraad"};
        model.setColumnIdentifiers(colName);
        // Breedte van de kolommen wordt gedefinieerd
        int[] colWidth = new int[5];
        colWidth[0] = 75;
        colWidth[1] = 165;
        colWidth[2] = 165;
        colWidth[3] = 165;
        colWidth[4] = 165;

        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        
        this.refreshTable();
        spTable = new JScrollPane(table);
        spTable.setBounds(25, 55, 750, 345);
        add(spTable);

        TableColumn column;
        for (int i = 0; i < 5; i++) {
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
        
        //order the table with colomn name
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 1;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    /**
     * Refreshes the JTable.
     */
    public void refreshTable() {
        label1.setText("");
        model.setRowCount(0);
        Set<Ingredient> ingredientList = m.updateTableIng();
        for(Ingredient i : ingredientList) {
            model.addRow(new Object[]{i.getId(), i.getName(), i.getInStock(), i.getMinStock(), i.getMaxStock()});
        }
        table.setModel(model);
        model.fireTableDataChanged();

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.makeVisible("Ingredient_add");
        }
    }

    private class ButtonHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int index = Integer.parseInt(table.getValueAt(row, 0).toString());
                Ingredient sel = m.getIngredient(index);
                controller.makeVisible("Ingredient_update", sel);
            } else {
                label1.setText("Selecteer eerst een product om te updaten!");
            }
        }
    }

    private class ButtonHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int index = Integer.parseInt(table.getValueAt(row, 0).toString());
                Ingredient sel = m.getIngredient(index);
                controller.makeVisible("Ingredient_delete", sel);
            }
            label1.setText("Selecteer eerst een product om te deleten!");
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
                
                Set<Ingredient> ingredientList = m.getSearchedIng(what,attribute);
                if(!ingredientList.isEmpty())
                {
                    model.setRowCount(0);
                    for(Ingredient i : ingredientList) {
                       model.addRow(new Object[]{i.getId(), i.getName(), i.getInStock(), i.getMinStock(), i.getMaxStock()});
                    }
                    table.setModel(model);
                    model.fireTableDataChanged();
                    label1.setText("Gevonden zoekresultaten zijn weergegeven");
                }
                else{
                    refreshTable();
                    label1.setText("Geen resultaat gevonden");
                }
            }
        }
    }

}
