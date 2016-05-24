package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Supplier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDAO {

    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());
    private final Set<Supplier> list;
    
    public SupplierDAO(){
        list = new HashSet<>();
    }
    
    public Supplier getSupplier(int a) {
        Supplier sup = null;
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query

        String selectSQL = "SELECT * FROM dhh_supplier WHERE id = " + String.valueOf(a);
        
        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                sup = fetchItem(resultset);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return sup;
    }

    public Set<Supplier> getAllSuppliers() {
         return list;
    }
    
    public void updateSuppliers() {
        list.clear();
        Supplier sup;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM dhh_supplier";

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
                list.add(sup);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
    }

    public ArrayList<Supplier> getSearchedSuppliers(String what, String att) {
        String selectSQL = "SELECT * FROM dhh_supplier WHERE ";
        switch(att){
            default:
                selectSQL += att + "=" + what;
                break;
                
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
        //int valueID = sup.getId();
        String valueName = sup.getName();
        String valueAddress = sup.getAddress();
        String valuePostalCode = sup.getPostalCode();
        String valueContactName = sup.getContactName();
        String valueEmail = sup.getEmail();
        String valuePhoneNo = sup.getPhoneNo();

        String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_supplier` (`name`, `address`, `postalCode`, `contactName`, `email`, `phoneNo`) VALUES('"
                + valueName + "','" + valueAddress + "','" + valuePostalCode + "','"
                + valueContactName + "','" + valueEmail + "','" + valuePhoneNo + "');";
        
        // Execute query
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }

    public void updateSupplier(Supplier sup, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        int valueID = sup.getId();
        if (valueID == id) {
            String valueName = sup.getName();
            String valueAddress = sup.getAddress();
            String valuePostalCode = sup.getPostalCode();
            String valueContactName = sup.getContactName();
            String valueEmail = sup.getEmail();
            String valuePhoneNo = sup.getPhoneNo();

            String selectSQL = "UPDATE `martkic145_stunt`.`dhh_supplier` SET `id` =" + String.valueOf(valueID)
                    + ",`name` = '" + valueName + "', `address` = '" + valueAddress + "', `postalCode` = '" + valuePostalCode
                    + "', `contactName` = '" + valueContactName + "', `email` = '" + valueEmail + "', `phoneNo` = '" + valuePhoneNo
                    + "' WHERE `dhh_supplier`.`id` = " + String.valueOf(id);
            boolean resultset = connection.executeSQLInsertStatement(selectSQL);
        } else {
            log.log(Level.SEVERE, "ID's DONT match, query is NOT executed");
        }
        connection.closeConnection();
    }

    public void deleteSupplier(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_supplier` WHERE `id` = " + id;
        boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
        if (resultset) {
            log.log(Level.SEVERE, "Supplier deleted.");
        }
        connection.closeConnection();
    }

    public int getMaxID() {
        int ID = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM dhh_supplier WHERE id = (SELECT max(id) FROM dhh_supplier)";

        // Execute query
        ResultSet resultset2 = connection.executeSQLSelectStatement(selectSQL);
        
        try {
            if (resultset2.first()) {

                ID = resultset2.getInt("id");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return ID;
    }
}
