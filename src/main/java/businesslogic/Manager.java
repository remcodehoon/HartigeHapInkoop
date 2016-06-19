package businesslogic;

import datastore.IngredientDAO;
import datastore.InventoryItemDAO;
import datastore.LoginDAO;
import datastore.OrderDAO;
import datastore.SupplierDAO;
import domain.Ingredient;
import domain.InventoryItem;
import domain.IvItemTable;
import domain.Order;
import domain.OrderRow;
import domain.Supplier;
import domain.SupplierIngredient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Manager {
    private static SupplierDAO supDAO;
    private static IngredientDAO ingDAO;
    private static OrderDAO orderDAO;
    private static LoginDAO loginDAO;
    private static InventoryItemDAO ivItemDAO;
    private int employeeId;
    private Set<Supplier> supList;
    private Set<SupplierIngredient> supIngList;
    private Set<Ingredient> ingList;
    private Set<Order> orderList;
    private Set<OrderRow> orderRowList;
    private Set<InventoryItem> inventoryItemList;
    private Set<IvItemTable> ivItemTableList;
   
    public Manager() {
        supDAO = new SupplierDAO(this);
        ingDAO = new IngredientDAO();
        orderDAO = new OrderDAO(this);
        ivItemDAO = new InventoryItemDAO();
	loginDAO = new LoginDAO();
        employeeId = 0;
        supList = new HashSet<>();
        supIngList = new HashSet<>();
        ingList = new HashSet<>();
        orderList = new HashSet<>();
        orderRowList = new HashSet<>();
        inventoryItemList = new HashSet<>();
        ivItemTableList = new HashSet<>();
    }
    
    public void updateTables(){
        ingList = ingDAO.getAllIngredients();
        orderList = orderDAO.getAllOrders();
        supList = supDAO.getAllSuppliers();
        supIngList = supDAO.getAllSupplierIngredients();
        orderRowList = orderDAO.getAllOrderRows();
        inventoryItemList = ivItemDAO.getAllInventoryItems();
        ivItemTableList = ivItemDAO.getInventoryItemTable();
        updateSupplierIngredientList();
        updateOrderRows();
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
    
    public int getEmployeeFunctionId(String username, String password) {
        
        return loginDAO.getEmployeeFunctionId(username, password);
    }
    
    public int getEmployeeFunctionId(int id) {
        
        return loginDAO.getEmployeeFunctionId(id);
    }

    public int getNewId(String what){
        switch(what){
            default:
                return 0;
            
            case "Supplier":
                return supDAO.getNextId();
                
            case "Ingredient":
                return ingDAO.getNextId();    
                
            case "Order":
                return orderDAO.getNextId();  
                
            case "InventoryItem":
                return ivItemDAO.getNextId();      
        }      
    }

// ------------------------* Ingredient data *-----------------------  
    public Ingredient getIngredient(int a) {
        for(Ingredient i : ingList){
            if(i.getId() == a){
                return i;
            }
        }
        return ingDAO.getIngredient(a);
    }
    
    public Ingredient getIngredient(String naam) {
        for(Ingredient i : ingList){
            if(i.getName().equals(naam)){
                return i;
            }
        }
        return ingDAO.getIngredient(naam); 
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
        ingList = ingDAO.getAllIngredients();
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
        for(Ingredient ing : ingList){
            ingNames.add(ing.getName());
        }
        return ingNames;
    }
    

// ------------------------* Leverancier data *-----------------------
    public Supplier getSupplier(int a) {
        for(Supplier i : supList){
            if(i.getId() == a){
                return i;
            }
        }
        //System.out.println("DAO USED");
        return supDAO.getSupplier(a);  
    }
    
    public Supplier getSupplier(String name) {
        for(Supplier i : supList){
            if(i.getName().equals(name)){
                return i;
            }
        }
        //System.out.println("DAO USED");
        return supDAO.getSupplier(name);
    }
    
    /**
     * voegt deze leverancier toe aan de database
     *
     * @param newSupplier
     * @param orderList
     */
    public void addSupplier(Supplier newSupplier) {
        supList.add(newSupplier);
        supDAO.addSupplier(newSupplier);
        
    }
    
    public void addSupplierRows(Supplier newSupplier){
        for(SupplierIngredient i : newSupplier.getIngredientList()){
            
            i.setSupplier(newSupplier);
        }
        supDAO.addSupplierOrderList(newSupplier);
    }

    public int getSupplierMaxId(){
        return supDAO.getNextId();
    }
    
    /**
     * wijzigt een leverancier in de database met bijbehorend id
     *
     * @param id -> Naam
     * @param updateSupplier -> Adres
     */
    public void updateSupplier(int id, Supplier updateSupplier, Set<SupplierIngredient> list) {
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
        supDAO.updateSupplierIngredient(list, id);
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
                orderDAO.deleteOrderRow(id);
                supDAO.deleteSupplier(id);
            }
        }   
    }
    
    /**
     *
     * @return Set of Suppliers
     */
    public Set<Supplier> updateTableSup(){
        supList = supDAO.getAllSuppliers();
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
        
    public void updateSupplierIngredientList(){
        for(Supplier i : supList){
            Set<SupplierIngredient> newlist = new HashSet<>();
            for(SupplierIngredient o : supIngList){
                if(i.getId() == o.getSupplier().getId()){
                    newlist.add(o);
                }
            }
            i.setIngredientList(newlist);
        }
    }
    
    public void updateOrderRows(){
        for(Order i : orderList){
            Set<OrderRow> newlist = new HashSet<>();
            for(OrderRow o : orderRowList){
                if(i.getId() == o.getOrder().getId()){
                    newlist.add(o);
                    i.setSupplier(o.getSupplier());
                }
            }
            i.setOrderRows(newlist);
        }
    }
    
     //-------------------* Bestelling Info *------------------------------
    public Order getOrderWithNr(String a) {
        Order order = null;
        for(Order i : orderList){
            if(i.getNr().equals(a)){
                order = new Order(i.getId(), i.getNr(),i.getDate(),i.getStatusId(),i.getEmployeeId());
                //order.setSupplier(i.get);
            }
        }
        if(order != null) {
            return order;
        } else {
            return orderDAO.getOrderWithNr(a);
        }
    }
    
    public Order getOrderWithId(int a) {
        Order order = null;
        for(Order i : orderList){
            if(i.getId() == a){
                order = new Order(i.getId(), i.getNr(),i.getDate(),i.getStatusId(),i.getEmployeeId());
                //order.setSupplier(i.get);
            }
        }
        if(order != null) {
            return order;
        } else {
            return orderDAO.getOrderWithId(a);
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
     * @param list
     */
    public void updateOrder(int id, Order updateOrder, Set<OrderRow> list) {
        //orderDAO.updateOrderRow(updateOrder, id);
        orderList.stream().filter((i) -> (i.getId() == id)).map((i) -> {
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


        orderDAO.updateOrderRow(list, id);
        //orderDAO.updateOrder(updateOrder, id);
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
            if(o.getId() == id) {
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
        //orderList = orderDAO.getAllOrders();
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
                
            case "Geplaatst":
                returnstatus = 1;
                break;
                    
            case "Geaccepteerd":
                returnstatus = 2;
                break;    
                    
            case "Klaar voor bezorgen":
                returnstatus = 3;
                break;    
                    
            case "Bezorgd":
                returnstatus = 4;
               break;
               
            case "Wacht op afrekenen":
                returnstatus = 5;
                break;
                    
            case "Betaald":
                returnstatus = 6;
                break;    
                    
            case "Afwachten":
                returnstatus = 7;
                break;    
                    
            case "Wacht op plaatsing":
                returnstatus = 8;
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
                returnstatus = "Geplaatst";
                break;
                    
            case 2:
                returnstatus = "Geaccepteerd";
                break;    
                    
            case 3:
                returnstatus = "Klaar voor bezorgen";
                break;    
                    
            case 4:
                returnstatus = "Bezorgd";
               break;
               
            case 5:
                returnstatus = "Wacht op afrekenen";
                break;
                    
            case 6:
                returnstatus = "Betaald";
                break;    
                    
            case 7:
                returnstatus = "Afwachten";
                break;    
                    
            case 8:
                returnstatus = "Wacht op plaatsing";
                break;       
        }
        return returnstatus;
    }
    //-------------------* Order Row Info *------------------------------
    public Set<OrderRow> getAllOrderRows(){
        return orderRowList;
    }
    
    public void addOrderRow(OrderRow newOrderRow) {
        orderRowList.add(newOrderRow); 
    }
    
    public Set<SupplierIngredient> getSupplierIngredientList(){
        supIngList = supDAO.getAllSupplierIngredients();
        return supIngList;
    }
    
    public Set<SupplierIngredient> getSupplierIngredientList(Supplier sup){
        supIngList = supDAO.getAllSupplierIngredients();
        int id = sup.getId();
        Set<SupplierIngredient> list = new HashSet<>();
        for(SupplierIngredient i : supIngList){
            if(i.getSupplier().getId() == id){
                list.add(i);
            }
        }
        return list;
    }
    
    //--------------------------------- inventory items //////////////////////
    public void addInventoryItem(InventoryItem item){
        inventoryItemList.add(item);
        ivItemDAO.addInventoryItem(item);
    }
    
     public void deleteInventoryItem(InventoryItem item){
        Iterator<InventoryItem> i = inventoryItemList.iterator();
        while(i.hasNext()) {
            InventoryItem o = i.next();
            if(o.getName().equals(item.getName())) {
                i.remove();
                ivItemDAO.deleteInventoryItem(o.getId());
            }
        }   
    }
    
    public Set<InventoryItem> getAllInventoryItems(){
        return inventoryItemList;
    }
   
    public ArrayList<String> getInventoryItemNames(){
        ArrayList<String> names = new ArrayList<>();
        for(InventoryItem i : inventoryItemList){
            names.add(i.getName());
        }
        return names;
    }
    
    public Set<IvItemTable> getInventoryTable(){
        return ivItemTableList;
    }
}


