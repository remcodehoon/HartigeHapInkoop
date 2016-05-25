package domain;

/**
 *
 * @author Mart
 */
public class Order {
    private int nr, statusId, employeeId;
    private String date;

    public Order(int nr, String date, int statusId, int employeeId) {
        this.nr = nr;
        this.date = date;
        this.statusId = statusId;
        this.employeeId = employeeId;
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

}
