package businesslogic;

import datastore.IngredientDAO;
import datastore.LoginDAO;
import datastore.OrderDAO;
import datastore.SupplierDAO;
import domain.Employee;
import domain.Ingredient;
import domain.Order;
import domain.OrderRow;
import domain.Supplier;
import static java.lang.reflect.Array.set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Manager {
    private static SupplierDAO supDAO;
    private static IngredientDAO ingDAO;
    private static OrderDAO orderDAO;
    //private static OrderRowDAO orderRowDAO;
    private static LoginDAO loginDAO;
    private int employeeId;
    private Set<Supplier> supList;
    private Set<Ingredient> ingList;
    private Set<Order> orderList;
    private Set<OrderRow> orderRowList;
   
    public Manager() {
        supDAO = new SupplierDAO();
        ingDAO = new IngredientDAO();
        orderDAO = new OrderDAO();
        //orderRowDAO = new OrderRowDAO();
	loginDAO = new LoginDAO();
        employeeId = 0;
        supList = new HashSet<>();
        ingList = new HashSet<>();
        orderList = new HashSet<>();
        orderRowList = new HashSet<>();
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
    
    public boolean checkLoginInfo(String username, String password) {
        
        return loginDAO.checkLoginInfo(username, password);
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
    
    public Ingredient getIngredient(String naam) {
        Ingredient ingredient = null;
        for(Ingredient i : ingList){
                if(i.getName().equals(naam)){
                    ingredient = new Ingredient(i.getId(),i.getName(),i.getInStock(),i.getMinStock(),i.getMaxStock());
                }
            }
        if(ingredient != null){
            return ingredient;
        } else {
            return ingDAO.getIngredient(naam);
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
        Iterator<Ingredient> i = ingList.iterator();
        while(i.hasNext()) {
            Ingredient o = i.next();
            if(o.getId() == id) {
                i.remove();
                ingDAO.deleteIngredient(id);
            }
        }
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
        Set<Ingredient> ingSearchList = new HashSet<>();
        switch(attribute){  
            case "ID":
                ingList.stream().forEach((i) -> {
                    String search = ""+ i.getId();
                    if(search.matches(".*" + what +".*"))
                        ingSearchList.add(i);
                });
                break;
        
            case "Naam":
                ingList.stream().filter((i) -> (i.getName().matches(".*" + what +".*"))).forEach((i) -> {
                    ingSearchList.add(i);
                });
                break;
                
            case "Voorraad":
                ingList.stream().forEach((i) -> {
                    String search = ""+ i.getInStock();
                    if(search.matches(".*" + what +".*"))
                        ingSearchList.add(i);
                });
                break;
            
            case "Minimum Voorraad":
                ingList.stream().forEach((i) -> {
                    String voorraad = ""+ i.getMinStock();
                    if(voorraad.matches(".*" + what +".*"))
                        ingSearchList.add(i);
                });
                break;
                
            case "Maximum Voorraad":
                ingList.stream().forEach((i) -> {
                    String search = ""+ i.getMaxStock();
                    if(search.matches(".*" + what +".*"))
                        ingSearchList.add(i);
                });
                break;
                
            default:
                break;
        }
        if(!ingSearchList.isEmpty()) {
            return ingSearchList;
        } else {
            return ingDAO.getSearchedIngredients(what, attribute);
        }
    }
    
    public ArrayList<String> getIngredientNames(){
        ArrayList<String> ingNames = new ArrayList<>();
        ingList.stream().forEach((sup) -> {
            ingNames.add(sup.getName());
        });
        
        return ingNames;
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
        Iterator<Supplier> i = supList.iterator();
        while(i.hasNext()) {
            Supplier o = i.next();
            if(o.getId() == id) {
                i.remove();
                supDAO.deleteSupplier(id);
            }
        }   
    }
    
    /**
     *
     * @return Set of Suppliers
     */
    public Set<Supplier> updateTableSup(){
        supList = supDAO.updateSuppliers();
        return supList;
    }
    
    public Set<Supplier> getSearchedSup(String what, String attribute){
        Set<Supplier> supSearchList = new HashSet<>();
        switch(attribute){  
            case "ID":
                supList.stream().forEach((i) -> {
                    String search = ""+ i.getId();
                    if(search.matches(".*" + what +".*"))
                        supSearchList.add(i);
                });
                break;
        
            case "Naam":
                supList.stream().filter((i) -> (i.getName().matches(".*" + what +".*"))).forEach((i) -> {
                    supSearchList.add(i);
                });
                break;
                
            case "Postcode":
                supList.stream().filter((i) -> (i.getPostalCode().matches(".*" + what +".*"))).forEach((i) -> {
                    supSearchList.add(i);
                });
                break;
            
            case "Adres":
                supList.stream().filter((i) -> (i.getAddress().matches(".*" + what +".*"))).forEach((i) -> {
                    supSearchList.add(i);
                });
                break;
                
            case "Tel Nummer":
                supList.stream().filter((i) -> (i.getPhoneNo().matches(".*" + what +".*"))).forEach((i) -> {
                    supSearchList.add(i);
                });
                break;
            
            case "Contact Persoon":
                supList.stream().filter((i) -> (i.getContactName().matches(".*" + what +".*"))).forEach((i) -> {
                    supSearchList.add(i);
                });
                break;
                
            case "E-mail":
                supList.stream().filter((i) -> (i.getEmail().matches(".*" + what +".*"))).forEach((i) -> {
                    supSearchList.add(i);
                });
                break;
                
            default:
                break;
        }
       if(!supSearchList.isEmpty()) {
            return supSearchList;
        } else {
           supList = (Set<Supplier>) supDAO.getSearchedSuppliers(what, attribute);
            return supList;
        }
    }
   
    public ArrayList<String> getSupplierNames(){
        ArrayList<String> supNames = new ArrayList<>();
        supList.stream().forEach((sup) -> {
            supNames.add(sup.getName());
        });
        
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
        orderDAO.addOrderRow(newOrder);
    }

    /**
     * wijzigt een leverancier in de database met bijbehorend id
     *
     * @param id -> Naam
     * @param updateOrder
     */
    public void updateOrder(int id, Order updateOrder) {
        //orderDAO.updateOrderRow(updateOrder, id);
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
        Iterator<Order> i = orderList.iterator();
        while(i.hasNext()) {
            Order o = i.next();
            if(o.getNr()== id) {
                i.remove();
                //orderDAO.deleteOrderRow(id);
                orderDAO.deleteOrder(id);
            }
        }   
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
                
            case 1:
                returnstatus = "Aangemaakt";
                break;
                    
            case 2:
                returnstatus = "Geaccepteerd";
                break;    
                    
            case 3:
                returnstatus = "Geleverd";
                break;    
                    
            case 4:
                returnstatus = "Afgerond";
                break; 
        }
        return returnstatus;
    }
    //-------------------* Order Row Info *------------------------------
    public void addOrderRow(OrderRow newOrderRow) {
        orderRowList.add(newOrderRow); 
        //orderRowDAO.addOrder(newOrderRow);
    }
}
