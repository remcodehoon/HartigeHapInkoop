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
    
    public OrderDAO(){
        
    }
    
    public Order getOrder(int a) {
        Order order = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM dhh_order WHERE orderNo = " + a;
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                order = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return order;
    }
    
    public Set<Order> updateOrders() {
        Set<Order> list = new HashSet<>();
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
                int fkey = resultset.getInt("supplierId");
                order = new Order(nr, date, statusId, employeeId, fkey);
                list.add(order);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
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
            int fkey = resultset.getInt("supplierId");
            // Create product
            order = new Order(nr, date, statusId, employeeId, fkey);

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return order;
    }

    public void addOrder(Order order) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_order` (`orderNo`,`orderDate`, `statusId`, `employeeId`, `supplierId`) VALUES("
            + order.getNr() + ",'" + order.getDate() + "'," + order.getStatusId() + "," + order.getEmployeeId() + "," + order.getFkey() +");";
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
        System.out.print(selectSQL);
    }

    public void updateOrder(Order order, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        int valueID = order.getNr();
        connection.openConnection();
        log.log(Level.SEVERE, "ID's match, query is executed");
        String selectSQL = "UPDATE `martkic145_stunt`.`dhh_order` SET `orderNo` =" + valueID
            + ",`orderDate` = '" + order.getDate() + "', `statusId` = " + order.getStatusId()
            + ", `employeeId` = " + order.getEmployeeId() + ",`supplierId` = " + order.getFkey() + " WHERE `dhh_order`.`orderNo` = " + id;
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
        System.out.print(selectSQL);
    }

    public void deleteOrder(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_order` WHERE `orderNo` = " + id;
        connection.executeSQLDeleteStatement(selectSQL);
        connection.closeConnection();
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
                int fkey = resultset.getInt("supplierId");
                // Create product
                order = new Order(nr, date, statusId, employeeId, fkey);
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
