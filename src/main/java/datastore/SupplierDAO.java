package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Supplier;
import java.util.ArrayList;

public class SupplierDAO {

    public Supplier getSupplier(int a) {
        Supplier sup = null;
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query

        String selectSQL = "SELECT * FROM dhh_supplier WHERE id = " + String.valueOf(a);
        //System.out.println(selectSQL);

        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                sup = fetchItem(resultset);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return sup;
    }

    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> supplierList = new ArrayList<>();
        Supplier sup = null;
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
                supplierList.add(sup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            System.out.println(e);
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
        System.out.println(selectSQL);
        // Execute query
        boolean resultset = connection.executeSQLInsertStatement(selectSQL);
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
            System.out.println("ID's DONT match, query is NOT executed");
        }
        connection.closeConnection();
    }

    public void deleteSupplier(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_supplier` WHERE `id` = " + id;
        boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
        if (resultset) {
            System.out.println("Supplier deleted.");
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
        //System.out.println(resultset);
        try {
            if (resultset2.first()) {

                ID = resultset2.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return ID;
    }
}
