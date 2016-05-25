package businesslogic;

import datastore.IngredientDAO;
import datastore.LoginDAO;
import datastore.OrderDAO;
import datastore.SupplierDAO;
import domain.Ingredient;
import domain.Order;
import domain.Supplier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Manager {
    private static SupplierDAO supDAO;
    private static IngredientDAO ingDAO;
    private static OrderDAO orderDAO;
    private static LoginDAO loginDAO;
    private int employeeId;
    private Set<Supplier> supList;
    private Set<Ingredient> ingList;
    private Set<Order> orderList;
   
    public Manager() {
        supDAO = new SupplierDAO();
        ingDAO = new IngredientDAO();
        orderDAO = new OrderDAO();
	loginDAO = new LoginDAO();
        employeeId = 0;
        supList = new HashSet<>();
        ingList = new HashSet<>();
        orderList = new HashSet<>();
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
        Ingredient ingredient = null;
        for(Ingredient i : ingList){
                if(i.getId() == a){
                    ingredient = new Ingredient(i.getId(),i.getName(),i.getInStock(),i.getMinStock(),i.getMaxStock());
                }
            }
        if(ingredient != null){
            return ingredient;
        } else {
            return ingDAO.getIngredient(a);
        } 
    }

    /**
     * voegt ingrediënt toe aan de database
     *
     * @param newIngredient
     */
    public void addIngredient(Ingredient newIngredient) {
        ingList.add(newIngredient); 
        ingDAO.addIngredient(newIngredient);
    }

    /**
     * geeft het ingredient dat geupdate moet worden door aan de met het ID
     *
     * @param id
     * @param updateIng
     */
    public void updateIngredient(int id, Ingredient updateIng) {
        ingList.stream().filter((i) -> (i.getId() == id)).map((i) -> {
            i.setName(updateIng.getName());
            return i;
        }).map((i) -> {
            i.setAttInt("inStock",updateIng.getInStock());
            return i;
        }).map((i) -> {
            i.setAttInt("minStock",updateIng.getMinStock());
            return i;
        }).forEach((i) -> {
            i.setAttInt("maxStock",updateIng.getMaxStock());
        });
        ingDAO.updateIngredient(updateIng, id);
    }

    /**
     * Verwijdert een ingredient uit de database aan de hand van het ID.
     *
     * @param id Het id dat gebruikt wordt om een ingredient te verwijderen.
     */
    public void deleteIngredient(int id) {
        ingList.stream().filter((i) -> (i.getId() == id)).forEach((i) -> {
            ingList.remove(i);
            });
        ingDAO.deleteIngredient(id);
    }
    
    /**
     *
     * @return Ingredient set
     */
    public Set<Ingredient> updateTableIng(){
        ingList = ingDAO.updateIngredients();
        return ingList;
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
        Supplier supplier = null;
        for(Supplier i : supList){
            if(i.getId() == a){
                supplier = new Supplier(i.getId(),i.getName(),i.getAddress(),i.getPostalCode(),i.getContactName(),i.getEmail(),i.getPhoneNo());
            }
        }
        if(supplier != null) {
            return supplier;
        } else {
            return supDAO.getSupplier(a);  
        }
    }
    
    public Supplier getSupplier(String name) {
        Supplier supplier = null;
        for(Supplier i : supList){
                if(i.getName().equals(name)){
                    supplier = new Supplier(i.getId(),i.getName(),i.getAddress(),i.getPostalCode(),i.getContactName(),i.getEmail(),i.getPhoneNo());
                }
            }
        if(supplier != null) {
            return supplier;
        } else {
        return supDAO.getSupplier(name);
        }
    }
    /**
     * voegt deze leverancier toe aan de database
     *
     * @param newSupplier
     */
    public void addSupplier(Supplier newSupplier) {
        supList.add(newSupplier);
        supDAO.addSupplier(newSupplier);
    }

    /**
     * wijzigt een leverancier in de database met bijbehorend id
     *
     * @param id -> Naam
     * @param updateSupplier -> Adres
     */
    public void updateSupplier(int id, Supplier updateSupplier) {
        supList.stream().filter((i) -> (i.getId() == id)).map((i) -> {
            i.setAttString("name",updateSupplier.getName());
            return i;
        }).map((i) -> {
            i.setAttString("address",updateSupplier.getAddress());
            return i;
        }).map((i) -> {
            i.setAttString("postalCode",updateSupplier.getPostalCode());
            return i;
        }).map((i) -> {
            i.setAttString("contactName",updateSupplier.getContactName());
            return i;
        }).map((i) -> {
            i.setAttString("email",updateSupplier.getEmail());
            return i;
        }).forEach((i) -> {
            i.setAttString("phoneNo",updateSupplier.getPhoneNo());
        });
        supDAO.updateSupplier(updateSupplier, id);
    }

    /**
     * Verwijdert een leverancier uit de database aan de hand van het ID.
     *
     * @param id Het id dat gebruikt wordt om een leverancier te verwijderen.
     */
    public void deleteSupplier(int id) {
        supList.stream().filter((i) -> (i.getId() == id)).forEach((i) -> {
            supList.remove(i);
        });
        supDAO.deleteSupplier(id);
    }
    
    /**
     *
     * @return Set of Suppliers
     */
    public Set<Supplier> updateTableSup(){
        supList = supDAO.updateSuppliers();
        return supList;
    }
    
    public ArrayList<Supplier> getSearchedSup(String what, String attribute){
        return supDAO.getSearchedSuppliers(what, attribute);
    }
	
	public boolean checkLoginInfo(String username, String password) {
        
        return loginDAO.checkLoginInfo(username, password);
    }
        
    public ArrayList<String> getSupplierNames(){
        ArrayList<String> supNames = new ArrayList<String>();
        for(Supplier sup : supList){
            supNames.add(sup.getName());
        }
        
        return supNames;
    }
        
     //-------------------* Bestelling Info *------------------------------
    public Order getOrder(int a) {
        Order order = null;
        for(Order i : orderList){
            if(i.getNr() == a){
                order = new Order(i.getNr(),i.getDate(),i.getStatusId(),i.getEmployeeId(),i.getFkey());
                //order.setSupplier(i.get);
            }
        }
        if(order != null) {
            return order;
        } else {
            return orderDAO.getOrder(a);
        }
    }

    /**
     * voegt deze bestelling toe aan de database
     *
     * @param newOrder
     */
    public void addOrder(Order newOrder) {
        orderList.add(newOrder); 
        orderDAO.addOrder(newOrder);
    }

    /**
     * wijzigt een leverancier in de database met bijbehorend id
     *
     * @param id -> Naam
     * @param updateOrder
     */
    public void updateOrder(int id, Order updateOrder) {
        orderList.stream().filter((i) -> (i.getNr() == id)).map((i) -> {
            i.setNr(updateOrder.getNr());
            return i;
        }).map((i) -> {
            i.setDate(updateOrder.getDate());
            return i;
        }).map((i) -> {
            i.setStatusId(updateOrder.getStatusId());
            return i;
        }).forEach((i) -> {
            i.setEmployeeId(updateOrder.getEmployeeId());
        });
        orderDAO.updateOrder(updateOrder, id);
    }

    /**
     * Verwijdert een leverancier uit de database aan de hand van het ID.
     *
     * @param id Het id dat gebruikt wordt om een leverancier te verwijderen.
     */
    public void deleteOrder(int id) {
        orderList.stream().filter((i) -> (i.getNr() == id)).forEach((i) -> {
            orderList.remove(i);
        });
        orderDAO.deleteOrder(id);
    }
    
    public Set<Order> getSearchedOrder(String what, String attribute){
        return orderDAO.getSearchedOrders(what, attribute);
    }
        
    public Set<Order> updateTableOrder(){
        orderList = orderDAO.updateOrders();
        return orderList;
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
