package datastore;

import businesslogic.Manager;
import domain.Ingredient;
import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Supplier;
import domain.SupplierIngredient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDAO {

    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());
    private Manager m;
    
    public SupplierDAO(Manager m){
        this.m = m;
    }
    
    public int getMaxID() {        
        int ID = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT id FROM supplier WHERE id = (SELECT max(id) FROM supplier)";

        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        //System.out.println(resultset);
        try {
            if (resultset.first()) {
                ID = resultset.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return ID;
    }
    
    public Supplier getSupplier(int a) {
        Supplier supplier = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM supplier WHERE id = " + String.valueOf(a);
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                supplier = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return supplier;
    }
    
    public Supplier getSupplier(String name) {
        Supplier supplier = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM supplier WHERE name = " + name;
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                supplier = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return supplier;
    }
    
    public Set<Supplier> updateSuppliers() {
        Set<Supplier> list = new HashSet<>();
        Supplier sup;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM supplier";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String address = resultset.getString("address");
                String postalCode = resultset.getString("postalCode");
                String contactname = resultset.getString("contactName");
                String email = resultset.getString("email");
                String phoneNo = resultset.getString("phoneNo");
                // Create product
                sup = new Supplier(id, name, address, postalCode, contactname, email, phoneNo);
                list.add(sup);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }
    
    public Set<SupplierIngredient> updateSupplierIngredients() {
        Set<SupplierIngredient> list = new HashSet<>();
        Supplier sup;
        Ingredient ing;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM supplier_ingredient";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                sup = m.getSupplier(resultset.getInt("supplierId"));
                ing = m.getIngredient(resultset.getInt("ingredientId"));
                double price = resultset.getDouble("price");
                int quantity = resultset.getInt("quantity");
                SupplierIngredient newSupIng = new SupplierIngredient(ing,sup,quantity,price);
                list.add(newSupIng);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }

    private Supplier fetchItem(ResultSet resultset) {
        // Instantiate
        Supplier sup = null;
        try {
            // Get all data
            int id = resultset.getInt("id");
            String name = resultset.getString("Name");
            String address = resultset.getString("address");
            String postalCode = resultset.getString("postalCode");
            String contactname = resultset.getString("contactName");
            String email = resultset.getString("email");
            String phoneNo = resultset.getString("phoneNo");
            // Create product
            sup = new Supplier(id, name, address, postalCode, contactname, email, phoneNo);

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return sup;
    }

    public void addSupplier(Supplier sup) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `23ivp4a`.`supplier` (`name`, `address`, `postalCode`, `contactName`, `email`, `phoneNo`) VALUES('"
            + sup.getName() + "','" + sup.getAddress() + "','" + sup.getPostalCode() + "','"
            + sup.getContactName() + "','" + sup.getEmail() + "','" + sup.getPhoneNo() + "');";
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }
    
    public void addSupplierOrderList(Set<SupplierIngredient> list) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `23ivp4a`.`supplier_ingredient` (`supplierId`, `ingredientId`, `price`, `quantity`) VALUES";
        for(SupplierIngredient i : list){
                selectSQL += "(" + i.getSupplier().getId() + "," + i.getIngredient().getId() + "," + i.getPrice() + "," + i.getQuantity() + "),";
        }
        selectSQL = selectSQL.substring(0, selectSQL.length()-1) + ";";
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }

    public void updateSupplier(Supplier sup, int id) {
        DatabaseConnection connection = new DatabaseConnection();
            int valueID = sup.getId();
            if (valueID == id) {
                connection.openConnection();
                log.log(Level.SEVERE, "ID's match, query is executed");
                String selectSQL = "UPDATE `23ivp4a`.`supplier` SET `id` =" + String.valueOf(valueID)
                + ",`name` = '" + sup.getName() + "', `address` = '" + sup.getAddress() + "', `postalCode` = '" + sup.getPostalCode()
                    + "', `contactName` = '" + sup.getContactName() + "', `email` = '" + sup.getEmail() + "', `phoneNo` = '" + sup.getPhoneNo()
                    + "' WHERE `supplier`.`id` = " + id;
                connection.executeSQLInsertStatement(selectSQL);
                connection.closeConnection();
            }
    }

    public void deleteSupplier(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE FROM `23ivp4a`.`supplier` WHERE `id` = " + id;
        connection.executeSQLDeleteStatement(selectSQL);
        connection.closeConnection();
    }
    
    public ArrayList<Supplier> getSearchedSuppliers(String what, String att) {
        String selectSQL = "SELECT * FROM supplier WHERE ";
        switch(att){  
            case "ID":
                selectSQL += "id=" + what +";";
                break;
        
            case "Naam":
                selectSQL += "name LIKE '%" + what + "%';";
                break;
                
            case "Postcode":
                selectSQL += "postalCode LIKE '%" + what + "%';";
                break;
            
            case "Adres":
                selectSQL += "address LIKE " + what + "%';";
                break;
                
            case "Tel Nummer":
                selectSQL += "phoneNo LIKE '%" + what + "%';";
                break;
            
            case "Contact Persoon":
                selectSQL += "contactName LIKE '%" + what + "%';";
                break;
                
            case "E-mail":
                selectSQL += "email LIKE '%" + what + "%';";
                break;
                
            default:
                selectSQL += att + "=" + what;
                break;
        }
        ArrayList<Supplier> supplierList = new ArrayList<>();
        Supplier sup;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("Name");
                String address = resultset.getString("address");
                String postalCode = resultset.getString("postalCode");
                String contactname = resultset.getString("contactName");
                String email = resultset.getString("email");
                String phoneNo = resultset.getString("phoneNo");
                // Create product
                sup = new Supplier(id, name, address, postalCode, contactname, email, phoneNo);
                supplierList.add(sup);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return supplierList;
    }
}
