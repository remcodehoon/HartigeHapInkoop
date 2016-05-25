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

        try {
            for(Order i : list){
                if(i.getNr() == a){
                    order = new Order(i.getNr(),i.getDate(),i.getStatusId(),i.getEmployeeId());
                }
            }
            return order;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
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

        boolean succes = true;
        try {
            connection.openConnection();
            String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_order` (`orderNo`,`orderDate`, `statusId`, `employeeId`) VALUES("
                + order.getNr() + ",'" + order.getDate() + "','" + order.getStatusId() + "','" + order.getEmployeeId() + "');";
            connection.executeSQLInsertStatement(selectSQL);
        } catch (Exception e) {
            succes = false;
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            if(succes)
                list.add(order); 
            connection.closeConnection();
        } 
    }

    public void updateOrder(Order order, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            int valueID = order.getNr();
            if (valueID == id) {
                connection.openConnection();
                log.log(Level.SEVERE, "ID's match, query is executed");
                String selectSQL = "UPDATE `martkic145_stunt`.`dhh_order` SET `orderNo` =" + valueID
                    + ",`orderDate` = '" + order.getDate() + "', `statusId` = " + order.getStatusId()
                    + ", `employeeId` = " + order.getEmployeeId() + " WHERE `dhh_order`.`orderNo` = " + id;
                connection.executeSQLInsertStatement(selectSQL);
                list.stream().filter((i) -> (i.getNr() == id)).map((i) -> {
                    i.setNr(id);
                    return i;
                }).map((i) -> {
                    i.setDate(order.getDate());
                    return i;
                }).map((i) -> {
                    i.setStatusId(order.getStatusId());
                    return i;
                }).forEach((i) -> {
                    i.setEmployeeId(order.getEmployeeId());
                });
            }
        } catch(Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
    }

    public void deleteOrder(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            connection.openConnection();
            String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_order` WHERE `orderNo` = " + id;
            boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
            if (resultset) {
                log.log(Level.SEVERE, "Bestelling deleted");
            }
            list.stream().filter((i) -> (i.getNr() == id)).forEach((i) -> {
                list.remove(i);
            });
        } catch(Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
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
