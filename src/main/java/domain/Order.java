package domain;

/**
 *
 * @author Mart
 */
public class Order {
    private int nr, statusId, employeeId, fkey;
    private String date;
    private Supplier supplier;

    public Order(int nr, String date, int statusId, int employeeId, int fkey) {
        this.nr = nr;
        this.date = date;
        this.statusId = statusId;
        this.employeeId = employeeId;
        this.fkey = fkey;
        supplier = null;
    }

    public int getNr() {
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
    
    public int getFkey() {
        return fkey;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }

    public void setNr(int newnr) {
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
    
    public void SetFkey(int fkey) {
        this.fkey = fkey;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
}
