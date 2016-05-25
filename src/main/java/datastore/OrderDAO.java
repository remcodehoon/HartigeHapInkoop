package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Order;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());
    private Set<Order> list;
    
    public OrderDAO(){
        list = new HashSet<>();
    }
    
    public Order getOrder(int a) {
        Order order = null;
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query

        String selectSQL = "SELECT * FROM dhh_order WHERE orderNo = " + String.valueOf(a);

        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                order = fetchItem(resultset);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return order;
    }
    
    public Set<Order> getAllOrders() {
        return list;
    }
    
    public void updateOrders() {
        list.clear();
        Order order;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM dhh_order";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int nr = resultset.getInt("orderNo");
                String date = resultset.getString("orderDate");
                int statusId = resultset.getInt("statusId");
                int employeeId = resultset.getInt("employeeId");
                // Create product
                order = new Order(nr, date, statusId, employeeId);
                list.add(order);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
    }

    private Order fetchItem(ResultSet resultset) {
        // Instantiate
        Order order = null;
        try {
            // Get all data
            int nr = resultset.getInt("orderNo");
            String date = resultset.getString("orderDate");
            int statusId = resultset.getInt("statusId");
            int employeeId = resultset.getInt("employeeId");
            // Create product
            order = new Order(nr, date, statusId, employeeId);

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return order;
    }

    public void addOrder(Order order) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        
        int id = this.getMaxID() + 1;
        String date = order.getDate();
        int statusId = order.getStatusId();
        int employeeId = order.getEmployeeId();

        String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_order` (`orderNo`,`orderDate`, `statusId`, `employeeId`) VALUES("
                + String.valueOf(id) + ",'" + date + "','" + String.valueOf(statusId) + "','" + String.valueOf(employeeId) + "');";
        
        // Execute query
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }

    public void updateOrder(Order order, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        int valueID = order.getNr();
        String date = order.getDate();
        int statusId = order.getStatusId();
        int employeeId = order.getEmployeeId();

        String selectSQL = "UPDATE `martkic145_stunt`.`dhh_order` SET `orderNo` =" + String.valueOf(valueID)
            + ",`orderDate` = '" + date + "', `statusId` = " + String.valueOf(statusId)
            + ", `employeeId` = " + String.valueOf(employeeId) + " WHERE `dhh_order`.`orderNo` = " + String.valueOf(id);
            
        connection.executeSQLInsertStatement(selectSQL);
        log.log(Level.SEVERE, "ID's DONT match, query is NOT executed");
        connection.closeConnection();
    }

    public void deleteOrder(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_order` WHERE `orderNo` = " + id;
        boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
        if (resultset) {
            log.log(Level.SEVERE, "Bestelling deleted");
        }
        connection.closeConnection();
    }

    public int getMaxID() {
        int id = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM `martkic145_stunt`.`dhh_order` WHERE orderNo = (SELECT max(orderNo) FROM dhh_order)";

        // Execute query
        ResultSet resultset2 = connection.executeSQLSelectStatement(selectSQL);
        
        try {
            if (resultset2.first()) {

                id = resultset2.getInt("orderNo");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return id;
    }

    public Set<Order> getSearchedOrders(String what, String att) {
        String selectSQL = "SELECT * FROM dhh_order WHERE ";
        switch(att){
            case "Bestelling nummer":
                selectSQL += "orderNo=" + what +";";
                break;
        
            case "Datum":
                selectSQL += "orderDate LIKE '%" + what + "%';";
                break;
                
            case "Status":
                selectSQL += "statusId=" + what + ";";
                break;
            
            case "Gebruiker nummer Voorraad":
                selectSQL += "employeeId=" + what + ";";
                break;
                
            case "Maximum Voorraad":
                selectSQL += "maxStock=" + what + ";";
                break;
                
            default:
                selectSQL += att + "=" + what;
                break;
        }
        Set<Order> orderList;
        orderList = new HashSet<>();
        Order order;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        
        try {
            while(resultset.next()) {
                int nr = resultset.getInt("orderNo");
                String date = resultset.getString("orderDate");
                int statusId = resultset.getInt("statusId");
                int employeeId = resultset.getInt("employeeId");
                // Create product
                order = new Order(nr, date, statusId, employeeId);
                orderList.add(order);
            }
        }
        catch (SQLException e) {
            log.log( Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return orderList;
    }
}
