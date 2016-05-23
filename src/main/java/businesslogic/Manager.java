package businesslogic;

import datastore.IngredientDAO;
import datastore.LoginDAO;
import datastore.SupplierDAO;
import domain.Ingredient;
import domain.Supplier;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Manager {
    SupplierDAO supDAO;
    IngredientDAO ingDAO;
    LoginDAO loginDAO;
    
    public Manager() {
        supDAO = new SupplierDAO();
        ingDAO = new IngredientDAO();
	loginDAO = new LoginDAO();
    }

    public Boolean checkNumbers(String invoer) {
        String regex = "[0-9]+";
        return invoer.matches(regex);
    }

    public JLabel createLabel(String text, int x, int y, int length, int width, String align) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, length, width);
        switch (align) {
            default:
                label.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "left":
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case "right":
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
        }
        return label;
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
    
    public ArrayList<Ingredient> updateTableIng(){
        ingDAO.updateIngredients();
        return ingDAO.getAllIngredients();
    }
    
    public ArrayList<Ingredient> getSearchedIng(String what, String attribute){
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
    
    public ArrayList<Supplier> updateTableSup(){
        supDAO.updateSuppliers();
        return supDAO.getAllSuppliers();
    }
    
    public ArrayList<Supplier> getSearchedSup(String what, String attribute){
        return supDAO.getSearchedSuppliers(what, attribute);
    }
	
	public boolean checkLoginInfo(String username, String password) {
        
        return loginDAO.checkLoginInfo(username, password);
    }
}
