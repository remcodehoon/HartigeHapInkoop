package presentation;

import businesslogic.Manager;
import domain.Ingredient;
import domain.Supplier;

// De Controller bepaalt welk frame er zichtbaar is
public class Controller {
    Manager m;
    SupplierFrame frame2;
    SupplierOverviewPanel panel2_0;
    SupplierAddPanel panel2_1;
    SupplierUpdatePanel panel2_2;
    SupplierDeletePanel panel2_3;

    Mainmenu frame1;

    IngredientFrame frame3;
    IngredientOverviewPanel panel3_0;
    IngredientAddPanel panel3_1;
    IngredientUpdatePanel panel3_2;
    IngredientDeletePanel panel3_3;
	
    LoginFrame frame4;
    LoginPanel frame4_0;

// Maakt de controller aan
    public Controller() {
        m = new Manager();
        frame1 = new Mainmenu(this,m);

        frame2 = new SupplierFrame(this,m);
        panel2_0 = new SupplierOverviewPanel(this,m);
        panel2_1 = new SupplierAddPanel(this,m);
        panel2_2 = new SupplierUpdatePanel(this,m);
        panel2_3 = new SupplierDeletePanel(this,m);

        frame3 = new IngredientFrame(this,m);
        panel3_0 = new IngredientOverviewPanel(this,m);
        panel3_1 = new IngredientAddPanel(this,m);
        panel3_2 = new IngredientUpdatePanel(this,m);
        panel3_3 = new IngredientDeletePanel(this,m);
		
        frame4 = new LoginFrame(this,m);
        frame4_0 = new LoginPanel(this,m);
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
        frame2.setContentPane(panel2_0);
        panel2_0.refreshTable();
        panel2_0.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierAdd() {
        frame2.setContentPane(panel2_1);
        panel2_1.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierUpdate() {
        frame2.setContentPane(panel2_2);
        panel2_2.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showSupplierDelete() {
        frame2.setContentPane(panel2_3);
        panel2_3.setVisible(true);
        frame2.setVisible(true);
    }
    
    private void showIngredientOverview() {
        frame3.setContentPane(panel3_0);
        panel3_0.refreshTable();
        panel3_0.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showIngredientAdd() {
        frame3.setContentPane(panel3_1);
        panel3_1.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showIngredientUpdate() {
        frame3.setContentPane(panel3_2);
        panel3_2.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showIngredientDelete() {
        frame3.setContentPane(panel3_3);
        panel3_3.setVisible(true);
        frame3.setVisible(true);
    }
    
    private void showLogin() {
        frame4.setVisible(true);
        frame4.setContentPane(frame4_0);
        frame4_0.createButtons();
    }
    
    public void makeVisible(String framenaam, Ingredient ing) {
        switch (framenaam) {
            default:
                frame3.setContentPane(panel3_0);
                panel3_0.refreshTable();
                panel3_0.setVisible(true);
                break;

            case "Ingredient_update":
                frame3.setContentPane(panel3_2);
                panel3_2.setVisible(true);
                frame3.setVisible(true);
                panel3_2.setIngredient(ing);
                break;

            case "Ingredient_delete":
                frame3.setContentPane(panel3_3);
                panel3_3.setVisible(true);
                frame3.setVisible(true);
                panel3_3.setIngredient(ing);
                break;
        }
    }

    public void makeVisible(String framenaam, Supplier sup) {
        switch (framenaam) {
            default:
                frame2.setContentPane(panel2_0);
                panel2_0.refreshTable();
                panel2_0.setVisible(true);
                break;

            case "Supplier_update":
                frame2.setContentPane(panel2_2);
                panel2_2.setVisible(true);
                frame2.setVisible(true);
                panel2_2.setSupplier(sup);
                break;

            case "Supplier_delete":
                frame2.setContentPane(panel2_3);
                panel2_3.setVisible(true);
                frame2.setVisible(true);
                panel2_3.setSupplier(sup);
                break;
        }
    }
}
