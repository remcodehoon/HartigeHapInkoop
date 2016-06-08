package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Order;
import domain.OrderRow;
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
        String selectSQL = "SELECT * FROM stockorder WHERE orderNo = " + a;
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

        String selectSQL = "SELECT stockorder.* FROM stockorder";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int nr = resultset.getInt("orderNo");
                String date = resultset.getString("orderDate");
                int statusId = resultset.getInt("statusId");
                int employeeId = resultset.getInt("employeeId");
                order = new Order(nr, date, statusId, employeeId, 1);
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
        String selectSQL = "INSERT INTO `23ivp4a`.`stockorder` (`orderNo`,`orderDate`, `statusId`, `employeeId`) VALUES("
            + order.getNr() + ",'" + order.getDate() + "'," + order.getStatusId() + "," + order.getEmployeeId() + ");";
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
        System.out.print(selectSQL);
    }
    
    public void addOrderRow(Order order) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `23ivp4a`.`stockorder_ingredient` (`ingredientId`,`amount`, `prize`, `orderNr`) VALUES ";
        int orderNr = order.getNr();
        Set<OrderRow> orderRowList = order.getOrderRows();
        if(!orderRowList.isEmpty()){
            for(OrderRow i : orderRowList){
                selectSQL += "(" + i.getIngredient().getId() + "," + i.getAmount() + "," + i.getPrize() + "," + orderNr + "),";
            }
            selectSQL = selectSQL.substring(0, selectSQL.length()-1) + ";";
        }
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
        System.out.print(selectSQL);
    }
    

    public void updateOrder(Order order, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        int valueID = order.getNr();
        connection.openConnection();
        log.log(Level.SEVERE, "ID's match, query is executed");
        String selectSQL = "UPDATE `23ivp4a`.`stockorder` SET `orderNo` =" + valueID
            + ",`orderDate` = '" + order.getDate() + "', `statusId` = " + order.getStatusId()
            + ", `employeeId` = " + order.getEmployeeId() + " WHERE `stockorder`.`orderNo` = " + id;
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }
    /*
    public void updateOrderRow(Order order, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        int valueID = order.getNr();
        connection.openConnection();
        String selectSQL = "DELETE from `23ivp4a`.`stockorder_ingredient` WHERE `stockorderId`=" + valueID;
        connection.executeSQLInsertStatement(selectSQL);
        selectSQL = "INSERT INTO `23ivp4a`.`stockorder_ingredient` (`ingredientId`,`quantity`, `prize`, `orderNr`) VALUES ";
        int orderNr = order.getNr();
        Set<OrderRow> orderRowList = order.getOrderRows();
        if(!orderRowList.isEmpty()){
            for(OrderRow i : orderRowList){
                selectSQL += "(" + i.getIngredient().getId() + "," + i.getAmount() + "," + i.getPrize() + "," + orderNr + "),";
            }
            selectSQL = selectSQL.substring(0, selectSQL.length()-1) + ";";
        }
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }
    */
    
    public void deleteOrder(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE FROM `23ivp4a`.`stockorder` WHERE `orderNo` = " + id;
        connection.executeSQLDeleteStatement(selectSQL);
        connection.closeConnection();
    }
    
    /*
    public void deleteOrderRow(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE from `23ivp4a`.`stockorder` WHERE `orderNr`=" + id;
        connection.executeSQLDeleteStatement(selectSQL);
        connection.closeConnection();
    }
    */

    public Set<Order> getSearchedOrders(String what, String att) {
        String selectSQL = "SELECT * FROM stockorder WHERE ";
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
            
            case "Gebruiker nummer":
                selectSQL += "employeeId=" + what + ";";
                break;
                
            case "Leverancier":
                selectSQL = "SELECT stockorder.* FROM stockorder, stockorder_ingredient, "
                        + "supplier WHERE stockorder.orderNo = stockorder_ingredient.stockorderId"
                        + " AND stockorder_ingredient.supplierId = supplier.name AND "
                        + "supplier.name = " + what + ";";
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
