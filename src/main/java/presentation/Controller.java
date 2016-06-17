package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import domain.Order;
import domain.Supplier;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// De Controller bepaalt welk frame er zichtbaar is
public class Controller {
    Manager m;
    SupplierFrame frame2;
    SupplierOverviewPanel panel20;
    SupplierAddPanel panel21;
    SupplierUpdatePanel panel22;
    SupplierDeletePanel panel23;

    Mainmenu frame1;

    IngredientFrame frame3;
    IngredientOverviewPanel panel30;
    IngredientAddPanel panel31;
    IngredientUpdatePanel panel32;
    IngredientDeletePanel panel33;
	
    LoginFrame frame4;
    LoginPanel frame40;
    
    OrderFrame frame5;
    OrderOverviewPanel panel50;
    OrderAddPanel panel51;
    OrderUpdatePanel panel52;
    OrderDeletePanel panel53;
    OrderInventory panel54;
    
    Controller controller;

// Maakt de controller aan
    public Controller() {
        m = new Manager();
        
        frame4 = new LoginFrame(this,m);
        frame40 = new LoginPanel(this,m);
        
        this.controller = this;
    }

    public void createFrames() {
        m.updateTables();
        frame1 = new Mainmenu(controller);

        frame2 = new SupplierFrame(controller);
        panel20 = new SupplierOverviewPanel(controller,m);
        panel21 = new SupplierAddPanel(controller,m);
        panel22 = new SupplierUpdatePanel(controller,m);
        panel23 = new SupplierDeletePanel(controller,m);

        frame3 = new IngredientFrame(controller);
        panel30 = new IngredientOverviewPanel(controller,m);
        panel31 = new IngredientAddPanel(controller,m);
        panel32 = new IngredientUpdatePanel(controller,m);
        panel33 = new IngredientDeletePanel(controller,m);
        
        frame5 = new OrderFrame(controller);
        panel50 = new OrderOverviewPanel(controller,m);
        panel51 = new OrderAddPanel(controller,m);
        panel52 = new OrderUpdatePanel(controller,m);
        panel53 = new OrderDeletePanel(controller,m);
        panel54 = new OrderInventory(controller,m);
    }
    
// Maakt een bepaald frame zichtbaar
    public void makeVisible(String framenaam) {
        if(m.getEmployeeId() != 0) {
            frame1.setVisible(false);
            frame2.setVisible(false);
            frame3.setVisible(false);
            frame4.setVisible(false);
            frame5.setVisible(false);
        }
        
        switch (framenaam) {
            case "Mainmenu":
                showMainMenu();
                break;

            case "Supplier_overview":
                showSupplierOverview();
                break;

            case "Supplier_add":
                showSupplierAdd();
                break;

            case "Supplier_update":
                showSupplierUpdate();
                break;

            case "Supplier_delete":
                showSupplierDelete();
                break;

            case "Ingredient_overview":
                showIngredientOverview();
                break;

            case "Ingredient_add":
                showIngredientAdd();
                break;

            case "Ingredient_update":
                showIngredientUpdate();
                break;

            case "Ingredient_delete":
                showIngredientDelete();
                break;
		
            case "Order_overview":
                showOrderOverview();
                break;    
                
            case "Order_add":
                showOrderAdd();
                break;

            case "Order_update":
                showOrderUpdate();
                break;

            case "Order_delete":
                showOrderDelete();
                break;
                
            case "Order_inventoryItems":
                showOrderInvItems();
                break;   
                
            case "Login":
                showLogin();
                break;
                
            default:
                frame4.setVisible(true);
                break;
        }
    }

    private void showMainMenu() {
        frame1.setVisible(true);
    }
    
    private void showSupplierOverview() {
        //m.updateTables();
        frame2.setContentPane(panel20);
        panel20.refreshTable();
        panel20.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierAdd() {
        frame2.setContentPane(panel21);
        panel21.setSupplier();
        panel21.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierUpdate() {
        frame2.setContentPane(panel22);
        panel22.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierDelete() {
        frame2.setContentPane(panel23);
        panel23.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showIngredientOverview() {
        //m.updateTables();
        frame3.setContentPane(panel30);
        panel30.refreshTable();
        panel30.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showIngredientAdd() {
        frame3.setContentPane(panel31);
        panel31.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showIngredientUpdate() {
        frame3.setContentPane(panel32);
        panel32.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showIngredientDelete() {
        frame3.setContentPane(panel33);
        panel33.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showOrderOverview() {
        //m.updateTables();
        frame5.setContentPane(panel50);
        panel50.refreshTable();
        panel50.setVisible(true);
        frame5.setVisible(true);
    }
    
    private void showOrderAdd() {
        frame5.setContentPane(panel51);
        panel51.setOrder();
        panel51.setVisible(true);
        frame5.setVisible(true);
    }
    
    private void showOrderUpdate() {
        frame5.setContentPane(panel52);
        panel52.setVisible(true);
        frame5.setVisible(true);
    }
    
    private void showOrderDelete() {
        frame5.setContentPane(panel53);
        panel53.setVisible(true);
        frame5.setVisible(true);
    }
    
    private void showOrderInvItems() {
        frame5.setContentPane(panel54);
        panel54.makeTable();
        panel54.setVisible(true);
        frame5.setVisible(true);
    }
    
    private void showLogin() {
        frame4.setVisible(true);
        frame4.setContentPane(frame40);
        frame40.createButtons();
    }
    
    public void makeVisible(String framenaam, Ingredient ing) {
        switch (framenaam) {
            case "Ingredient_update":
                frame3.setContentPane(panel32);
                panel32.setVisible(true);
                frame3.setVisible(true);
                panel32.setIngredient(ing);
                break;

            case "Ingredient_delete":
                frame3.setContentPane(panel33);
                panel33.setVisible(true);
                frame3.setVisible(true);
                panel33.setIngredient(ing);
                break;
            
            default:
                frame3.setContentPane(panel30);
                panel30.refreshTable();
                panel30.setVisible(true);
                break;
        }
    }

    public void makeVisible(String framenaam, Supplier sup) {
        switch (framenaam) {
            case "Supplier_update":
                frame2.setContentPane(panel22);
                panel22.setVisible(true);
                frame2.setVisible(true);
                panel22.setSupplier(sup);
                break;

            case "Supplier_delete":
                frame2.setContentPane(panel23);
                panel23.setVisible(true);
                frame2.setVisible(true);
                panel23.setSupplier(sup);
                break;
              
            default:
                frame2.setContentPane(panel20);
                panel20.refreshTable();
                panel20.setVisible(true);
                break;
        }
    }
    
    public void makeVisible(String framenaam, Order order) {
        switch (framenaam) {
            case "Order_update":
                frame5.setContentPane(panel52);
                panel52.setVisible(true);
                frame5.setVisible(true);
                panel52.setOrder(order);
                break;

            case "Order_delete":
                frame5.setContentPane(panel53);
                panel53.setVisible(true);
                frame5.setVisible(true);
                panel53.setOrder(order);
                break;
              
            default:
                frame5.setContentPane(panel50);
                panel50.refreshTable();
                panel50.setVisible(true);
                break;
        }
    }
    
    public JLabel createLabel(String text, int x, int y, int length, int width, String align) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, length, width);
        switch (align) {
            case "left":
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case "right":
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            default:
                label.setHorizontalAlignment(SwingConstants.CENTER);
                break;
        }
        return label;
    }
}
