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
        Supplier supplier = null;
        
        try {
            for(Supplier i : list){
                if(i.getId() == a){
                    supplier = new Supplier(i.getId(),i.getName(),i.getAddress(),i.getPostalCode(),i.getContactName(),i.getEmail(),i.getPhoneNo());
                }
            }
            return supplier;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
            DatabaseConnection connection = new DatabaseConnection();
            connection.openConnection();
            String selectSQL = "SELECT * FROM dhh_supplier WHERE id = " + String.valueOf(a);
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

        boolean succes = true;
        try {
            connection.openConnection();
            String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_supplier` (`name`, `address`, `postalCode`, `contactName`, `email`, `phoneNo`) VALUES('"
                + sup.getName() + "','" + sup.getAddress() + "','" + sup.getPostalCode() + "','"
                + sup.getContactName() + "','" + sup.getEmail() + "','" + sup.getPhoneNo() + "');";
            connection.executeSQLInsertStatement(selectSQL);
        } catch (Exception e) {
            succes = false;
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            if(succes)
                list.add(sup); 
            connection.closeConnection();
        }
    }

    public void updateSupplier(Supplier sup, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            int valueID = sup.getId();
            if (valueID == id) {
                connection.openConnection();
                log.log(Level.SEVERE, "ID's match, query is executed");
                String selectSQL = "UPDATE `martkic145_stunt`.`dhh_supplier` SET `id` =" + String.valueOf(valueID)
                + ",`name` = '" + sup.getName() + "', `address` = '" + sup.getAddress() + "', `postalCode` = '" + sup.getPostalCode()
                    + "', `contactName` = '" + sup.getContactName() + "', `email` = '" + sup.getEmail() + "', `phoneNo` = '" + sup.getPhoneNo()
                    + "' WHERE `dhh_supplier`.`id` = " + id;
                connection.executeSQLInsertStatement(selectSQL);
                list.stream().filter((i) -> (i.getId() == id)).map((i) -> {
                    i.setAttString("name",sup.getName());
                    return i;
                }).map((i) -> {
                    i.setAttString("address",sup.getAddress());
                    return i;
                }).map((i) -> {
                    i.setAttString("postalCode",sup.getPostalCode());
                    return i;
                }).map((i) -> {
                    i.setAttString("contactName",sup.getContactName());
                    return i;
                }).map((i) -> {
                    i.setAttString("email",sup.getEmail());
                    return i;
                }).forEach((i) -> {
                    i.setAttString("phoneNo",sup.getPhoneNo());
                });
            }
        } catch(Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
    }

    public void deleteSupplier(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            connection.openConnection();
            String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_supplier` WHERE `id` = " + id;
            boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
            if (resultset) {
                log.log(Level.SEVERE, "Supplier deleted");
            }
            list.stream().filter((i) -> (i.getId() == id)).forEach((i) -> {
                list.remove(i);
            });
        } catch(Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
    }
    
    public ArrayList<Supplier> getSearchedSuppliers(String what, String att) {
        String selectSQL = "SELECT * FROM dhh_supplier WHERE ";
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
