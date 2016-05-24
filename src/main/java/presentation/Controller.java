package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import domain.Supplier;

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

// Maakt de controller aan
    public Controller() {
        m = new Manager();
        frame1 = new Mainmenu(this);

        frame2 = new SupplierFrame(this);
        panel20 = new SupplierOverviewPanel(this,m);
        panel21 = new SupplierAddPanel(this,m);
        panel22 = new SupplierUpdatePanel(this,m);
        panel23 = new SupplierDeletePanel(this,m);

        frame3 = new IngredientFrame(this);
        panel30 = new IngredientOverviewPanel(this,m);
        panel31 = new IngredientAddPanel(this,m);
        panel32 = new IngredientUpdatePanel(this,m);
        panel33 = new IngredientDeletePanel(this,m);
		
        frame4 = new LoginFrame(this,m);
        frame40 = new LoginPanel(this,m);
    }

// Maakt een bepaald frame zichtbaar
    public void makeVisible(String framenaam) {

        frame1.setVisible(false);
        frame2.setVisible(false);
        frame3.setVisible(false);
        frame4.setVisible(false);

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
        frame2.setContentPane(panel20);
        panel20.refreshTable();
        panel20.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierAdd() {
        frame2.setContentPane(panel21);
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
}
