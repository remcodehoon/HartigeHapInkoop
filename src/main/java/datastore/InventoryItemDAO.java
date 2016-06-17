/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore;

import domain.InventoryItem;
import domain.IvItemTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mart
 */
public class InventoryItemDAO {
    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());
    
    public InventoryItemDAO(){
        
    }
    
    public int getNextId() {            
        int ID = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'inventoryitem'";
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                ID = resultset.getInt("auto_increment");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            connection.closeConnection();
        }
        return ID;
    }
    
    public InventoryItem getInventoryItem(int a) {
        InventoryItem item = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM inventoryitem WHERE id = " + String.valueOf(a);
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                item = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return item;
    }
    
    public InventoryItem getInventoryItem(String naam) {
        InventoryItem item = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM ingredient WHERE inventoryitem = '" + naam + "';";
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                item = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return item;
    }
    
    public Set<InventoryItem> getAllInventoryItems() {
        Set<InventoryItem> list = new HashSet<>();
        InventoryItem item;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM inventoryitem";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("itemName");
                // Create product
                item = new InventoryItem(id, name);
                list.add(item);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }

    private InventoryItem fetchItem(ResultSet resultset) {
        // Instantiate
        InventoryItem item = null;
        try {
            int id = resultset.getInt("id");
            String name = resultset.getString("itemName");
            item = new InventoryItem(id, name);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return item;
    }

    public void addInventoryItem(InventoryItem item) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `23ivp4a`.`inventoryitem` (`itemName`) VALUES('" + item.getName() + "');";
        //System.out.println(selectSQL);
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }

    public void updateInventoryItem(InventoryItem item, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        int valueID = item.getId();
        if (valueID == id) {
            connection.openConnection();
            String selectSQL = "UPDATE `23ivp4a`.`inventoryitem` SET `id` =" + valueID + ",`itemName` = '" + item.getName() + "' WHERE `inventoryitem`.`id` = " + id;
            connection.executeSQLInsertStatement(selectSQL);
            connection.closeConnection();
        }
    }

    public void deleteInventoryItem(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE FROM `23ivp4a`.`inventoryitem` WHERE `id` = " + id;
        connection.executeSQLDeleteStatement(selectSQL);
        connection.closeConnection();
    }



    public Set<InventoryItem> getSearchedInventoryItems(String what, String att) {
        String selectSQL = "SELECT * FROM ingredient WHERE ";
        switch(att){
            case "ID":
                selectSQL += "id=" + what +";";
                break;
        
            case "Naam":
                selectSQL += "ingredientName LIKE '%" + what + "%';";
                break;
                
            default:
                selectSQL += att + "=" + what;
                break;
        }
        Set<InventoryItem> list;
        list = new HashSet<>();
        InventoryItem item;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
 
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);     
        try {
            while(resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("itemName");
                // Create product
                item = new InventoryItem(id, name);
                list.add(item);
            }
        }
        catch (SQLException e) {
            log.log( Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }
    
    public Set<IvItemTable> getInventoryItemTable(){
        Set<IvItemTable> list = new HashSet<>();
        IvItemTable item;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT supplier_inventoryitem.inventoryItemId,supplier_inventoryitem.supplierId,"
                + "supplier_inventoryitem.price,supplier_inventoryitem.quantity," +
                "inventoryitem.itemName FROM supplier_inventoryitem,inventoryitem" +
                " WHERE supplier_inventoryitem.inventoryItemId = inventoryitem.id ";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int inventoryItemId = resultset.getInt("inventoryItemId");
                int supplierId = resultset.getInt("supplierId");
                double price = resultset.getDouble("price");
                int quantity = resultset.getInt("quantity");
                String name = resultset.getString("itemName");
                // Create product
                item = new IvItemTable(inventoryItemId, supplierId, price, quantity, name);
                list.add(item);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }
}