package datastore;

import businesslogic.Manager;
import domain.Ingredient;
import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Order;
import domain.OrderRow;
import domain.Supplier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());
    private Manager m;
    
    public OrderDAO(Manager m){
        this.m = m;
    }
    
    public int getNextId() {        
        int ID = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'stockorder'";

        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        //System.out.println(resultset);
        try {
            if (resultset.first()) {
                ID = resultset.getInt("auto_increment");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return ID;
    }
    
    public int getOrderId(int orderNo) {
        int order = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT id FROM stockorder WHERE orderNo = " + orderNo;
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                order = resultset.getInt("id");
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return order;
    }
    
    
    public Order getOrderWithNr(int a) {
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
    
    public Order getOrderWithId(int a) {
        Order order = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM stockorder WHERE id = " + a;
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
    
    public Set<Order> getAllOrders() {
        Set<Order> list = new HashSet<>();
        Order order;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT stockorder.* FROM stockorder";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String nr = resultset.getString("orderNo");
                String date = resultset.getString("orderDate");
                int statusId = resultset.getInt("statusId");
                int employeeId = resultset.getInt("employeeId");
                order = new Order(id, nr, date, statusId, employeeId);
                list.add(order);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }
    
    public Set<OrderRow> getAllOrderRows() {
        Set<OrderRow> list = new HashSet<>();
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM stockorder_ingredient";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int ingId = resultset.getInt("ingredientId");
                Ingredient newIng = m.getIngredient(ingId);
                int orderId = resultset.getInt("stockorderId");
                Order newOrder = this.getOrderWithId(orderId);
                int supId = resultset.getInt("supplierId");
                Supplier sup = m.getSupplier(supId);
                int quantity = resultset.getInt("quantity");
                OrderRow orderRow = new OrderRow(newIng, newOrder, sup, quantity);
                list.add(orderRow);
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
            int id = resultset.getInt("id");
            String nr = resultset.getString("orderNo");
            String date = resultset.getString("orderDate");
            int statusId = resultset.getInt("statusId");
            int employeeId = resultset.getInt("employeeId");
            // Create product
            order = new Order(id,nr, date, statusId, employeeId);

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return order;
    }

    public void addOrder(Order order) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `23ivp4a`.`stockorder` (`orderNo`,`orderDate`, `statusId`, `employeeId`) VALUES('"
            + order.getNr() + "','" + order.getDate() + "'," + order.getStatusId() + "," + order.getEmployeeId() + ");";
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
        System.out.print(selectSQL);
    }
    
    public void addOrderRow(Order order) {
        int id = order.getId();
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `23ivp4a`.`stockorder_ingredient` (`ingredientId`,`stockorderId`, `supplierId`, `quantity`) VALUES ";
        Set<OrderRow> orderRowList = order.getOrderRows();
        if(!orderRowList.isEmpty()){
            for(OrderRow i : orderRowList){
                selectSQL += "(" + i.getIngredient().getId() + "," + id + "," + i.getSupplier().getId() + "," + i.getAmount() + "),";
            }
            selectSQL = selectSQL.substring(0, selectSQL.length()-1) + ";";
        }
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
        System.out.print(selectSQL);
    }
    

    public void updateOrder(Order order, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        log.log(Level.SEVERE, "ID's match, query is executed");
        String selectSQL = "UPDATE `23ivp4a`.`stockorder` SET `orderNo` ='" + order.getNr()
            + "',`orderDate` = '" + order.getDate() + "', `statusId` = " + order.getStatusId()
            + ", `employeeId` = " + order.getEmployeeId() + " WHERE `stockorder`.`id` = " + id;
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }
    public void updateOrderRow(Set<OrderRow> list, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE FROM `23ivp4a`.`stockorder_ingredient` WHERE `stockorderId` = " + id;
        System.out.println(selectSQL);
        connection.executeSQLDeleteStatement(selectSQL);
        selectSQL = "INSERT INTO `23ivp4a`.`stockorder_ingredient` (`ingredientId`, `stockorderId`, `supplierId`, `quantity`) VALUES";
        for(OrderRow i : list){
                selectSQL += "(" + i.getSupplier().getId() + "," + i.getOrder().getId() + "," + i.getIngredient().getId() + "," + i.getAmount()+ "),";
        }
        selectSQL = selectSQL.substring(0, selectSQL.length()-1) + ";";
        System.out.println(selectSQL);
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
        String selectSQL = "DELETE FROM `23ivp4a`.`stockorder_ingredient` WHERE `stockorderId` = " + id;
        connection.executeSQLDeleteStatement(selectSQL);
        
        selectSQL = "DELETE FROM `23ivp4a`.`stockorder` WHERE `id` = " + id;
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
                int id = resultset.getInt("id");
                String nr = resultset.getString("orderNo");
                String date = resultset.getString("orderDate");
                int statusId = resultset.getInt("statusId");
                int employeeId = resultset.getInt("employeeId");
                // Create product
                order = new Order(id, nr, date, statusId, employeeId);
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
