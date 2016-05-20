package businesslogic;

import datastore.IngredientDAO;
import datastore.SupplierDAO;
import domain.Ingredient;
import domain.Supplier;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Manager {
    
    public Manager(){

        
    }
    
    public Boolean checkNumbers(String invoer){
        String regex = "[0-9]+";
        return invoer.matches(regex);
    }
    
    public JLabel createLabel(String text, int x, int y, int length, int width, String align){
        JLabel label = new JLabel(text);
        label.setBounds(x,y,length,width);
        switch(align)
        {
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
    public Ingredient getIngredient(int a)
    {
        IngredientDAO dao = new IngredientDAO();
        return dao.getIngredient(a);
    }
    
    /**
     * voegt ingrediënt toe aan de database
     * @param newIngredient
     */
    public void addIngredient(Ingredient newIngredient){
        IngredientDAO dao = new IngredientDAO();
        //int id = dao.getMaxID() + 1;
        dao.addIngredient(newIngredient);
    }
    
    /**
     * geeft het ingredient dat geupdate moet worden door 
     * aan de met het ID
     * @param id
     * @param updateIng
     */
    public void updateIngredient(int id, Ingredient updateIng){
        IngredientDAO dao = new IngredientDAO();
        dao.updateIngredient(updateIng, id);
    }
    
    /**
     * Verwijdert een ingredient uit de database aan de hand van het ID.
     * @param id Het id dat gebruikt wordt om een ingredient te verwijderen.
     */
    public void deleteIngredient(int id) {
        IngredientDAO dao = new IngredientDAO();
        dao.deleteIngredient(id);
    }
    
// ------------------------* Leverancier data *-----------------------
    public Supplier getSupplier(int a)
    {
        SupplierDAO dao = new SupplierDAO();
        return dao.getSupplier(a);
    }
    /**
     * voegt deze leverancier toe aan de database
     * @param newSupplier
     */
    public void addSupplier(Supplier newSupplier){
        SupplierDAO dao = new SupplierDAO();
        dao.addSupplier(newSupplier);
    }
    
    /**
     * wijzigt een leverancier in de database
     * met bijbehorend id
     * @param id -> Naam
     * @param updateSupplier -> Adres
     */
    public void updateSupplier(int id, Supplier updateSupplier){
        SupplierDAO dao = new SupplierDAO();
        dao.updateSupplier(updateSupplier, id);
    }
    
    /**
     * Verwijdert een leverancier uit de database aan de hand van het ID.
     * @param id Het id dat gebruikt wordt om een leverancier te verwijderen.
     */
    public void deleteSupplier(int id) {
        SupplierDAO dao = new SupplierDAO();
        dao.deleteSupplier(id);
    }


}

