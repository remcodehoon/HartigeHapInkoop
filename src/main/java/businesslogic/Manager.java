package businesslogic;

import datastore.IngredientDAO;
import datastore.LoginDAO;
import datastore.OrderDAO;
import datastore.SupplierDAO;
import domain.Ingredient;
import domain.Order;
import domain.Supplier;
import java.util.ArrayList;
import java.util.Set;

public class Manager {
    private static SupplierDAO supDAO;
    private static IngredientDAO ingDAO;
    private static OrderDAO orderDAO;
    private static LoginDAO loginDAO;
    private int employeeId;
    
    public Manager() {
        supDAO = new SupplierDAO();
        ingDAO = new IngredientDAO();
        orderDAO = new OrderDAO();
	loginDAO = new LoginDAO();
        employeeId = 0;
    }

    public void setEmployeeId(int id) {
        this.employeeId = id;
    }
    
    public int getEmployeeId() {
        return this.employeeId;
    }
    
    public int getEmployeeId(String username, String password) {
        return loginDAO.getEmployeeId(username, password);
    }
    
    public Boolean checkNumbers(String invoer) {
        String regex = "[0-9]+";
        return invoer.matches(regex);
    }


// ------------------------* Ingredient data *-----------------------  
    public Ingredient getIngredient(int a) {
        return ingDAO.getIngredient(a);
    }

    /**
     * voegt ingrediënt toe aan de database
     *
     * @param newIngredient
     */
    public void addIngredient(Ingredient newIngredient) {
        ingDAO.addIngredient(newIngredient);
    }

    /**
     * geeft het ingredient dat geupdate moet worden door aan de met het ID
     *
     * @param id
     * @param updateIng
     */
    public void updateIngredient(int id, Ingredient updateIng) {
        ingDAO.updateIngredient(updateIng, id);
    }

    /**
     * Verwijdert een ingredient uit de database aan de hand van het ID.
     *
     * @param id Het id dat gebruikt wordt om een ingredient te verwijderen.
     */
    public void deleteIngredient(int id) {
        ingDAO.deleteIngredient(id);
    }
    
    /**
     *
     * @return Ingredient set
     */
    public Set<Ingredient> updateTableIng(){
        ingDAO.updateIngredients();
        return ingDAO.getAllIngredients();
    }
    
    /**
     *
     * @param what
     * @param attribute
     * @return Set of found ingredients
     */
    public Set<Ingredient> getSearchedIng(String what, String attribute){
        return ingDAO.getSearchedIngredients(what, attribute);
    }

// ------------------------* Leverancier data *-----------------------
    public Supplier getSupplier(int a) {
        return supDAO.getSupplier(a);
    }

    /**
     * voegt deze leverancier toe aan de database
     *
     * @param newSupplier
     */
    public void addSupplier(Supplier newSupplier) {
        supDAO.addSupplier(newSupplier);
    }

    /**
     * wijzigt een leverancier in de database met bijbehorend id
     *
     * @param id -> Naam
     * @param updateSupplier -> Adres
     */
    public void updateSupplier(int id, Supplier updateSupplier) {
        supDAO.updateSupplier(updateSupplier, id);
    }

    /**
     * Verwijdert een leverancier uit de database aan de hand van het ID.
     *
     * @param id Het id dat gebruikt wordt om een leverancier te verwijderen.
     */
    public void deleteSupplier(int id) {
        supDAO.deleteSupplier(id);
    }
    
    /**
     *
     * @return Set of Suppliers
     */
    public Set<Supplier> updateTableSup(){
        supDAO.updateSuppliers();
        return supDAO.getAllSuppliers();
    }
    
    public ArrayList<Supplier> getSearchedSup(String what, String attribute){
        return supDAO.getSearchedSuppliers(what, attribute);
    }
	
	public boolean checkLoginInfo(String username, String password) {
        
        return loginDAO.checkLoginInfo(username, password);
    }
        
    public ArrayList<String> getSupplierNames(){
        ArrayList<String> supNames = new ArrayList<String>();
        Set<Supplier> list = supDAO.getAllSuppliers();
        for(Supplier sup : list){
            supNames.add(sup.getName());
        }
        
        return supNames;
    }
        
     //-------------------* Bestelling Info *------------------------------
    public Order getOrder(int a) {
        return orderDAO.getOrder(a);
    }

    /**
     * voegt deze bestelling toe aan de database
     *
     * @param newOrder
     */
    public void addOrder(Order newOrder) {
        orderDAO.addOrder(newOrder);
    }

    /**
     * wijzigt een leverancier in de database met bijbehorend id
     *
     * @param id -> Naam
     * @param updateOrder
     */
    public void updateOrder(int id, Order updateOrder) {
        orderDAO.updateOrder(updateOrder, id);
    }

    /**
     * Verwijdert een leverancier uit de database aan de hand van het ID.
     *
     * @param id Het id dat gebruikt wordt om een leverancier te verwijderen.
     */
    public void deleteOrder(int id) {
        orderDAO.deleteOrder(id);
    }
    
    public Set<Order> getSearchedOrder(String what, String attribute){
        return orderDAO.getSearchedOrders(what, attribute);
    }
        
    public Set<Order> updateTableOrder(){
        orderDAO.updateOrders();
        return orderDAO.getAllOrders();
    }
    
    public int getOrderStatus(String status){
        int returnstatus;
        switch(status){
            default:
                returnstatus = 0;
                break;
            
            case "Fout":
                returnstatus = 0;
                break;    
                
            case "Aangemaakt":
                returnstatus = 1;
                break;
                    
            case "Geaccepteerd":
                returnstatus = 2;
                break;    
                    
            case "Geleverd":
                returnstatus = 3;
                break;    
                    
            case "Afgerond":
                returnstatus = 4;
               break;   
        }
        return returnstatus;
    }
    
    public String getOrderStatus(int status){
        String returnstatus;
        switch(status){ 
            default:
                returnstatus = "Fout";
                break;
                
            case 0:
                returnstatus = "Aangemaakt";
                break;
                    
            case 1:
                returnstatus = "Geaccepteerd";
                break;    
                    
            case 2:
                returnstatus = "Besteld";
                break;    
                    
            case 3:
                returnstatus = "Geleverd";
                break; 
        }
        return returnstatus;
    }
}
