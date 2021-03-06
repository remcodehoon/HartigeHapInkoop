package domain;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
/**
 *
 * @author Mart
 */
public class Order {
    private int id, statusId, employeeId;
    private String nr,date;
    private Supplier supplier;
    private Set<OrderRow> orders;

    public Order(int id, String nr, String date, int statusId, int employeeId) {
        this.id = id;
        this.nr = nr;
        this.date = date;
        this.statusId = statusId;
        this.employeeId = employeeId;
        this.supplier = null;
        orders = new HashSet<>();
    }

    public int getId(){
        return id;
    }
    
    public String getNr() {
        return nr;
    }

    public String getDate() {
        return date;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }
    
    public Set<OrderRow> getOrderRows() {
        return orders;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public void setNr(String newnr) {
        nr = newnr;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public void setStatusId(int status) {
        statusId = status;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }


    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    public void setOrderRows(Set<OrderRow> list) {
        orders = list;
    }
    
    public void addOrderRow(OrderRow row){
        orders.add(row);
    }
    
    public boolean deleteOrderRow(OrderRow row){
        if(orders.contains(row))
        {
            Iterator<OrderRow> i = orders.iterator();
            while(i.hasNext()) {
                OrderRow o = i.next();
                if(o == row) {
                    i.remove();
                    // HERE CODE TO REMOVE FROM DAO??
                    return true;
                }
            } 
        }
        return false;
    }
}
