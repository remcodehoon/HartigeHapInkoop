package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Ingredient;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientDAO {

    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());
    
    public IngredientDAO(){
        
    }
    
    public Ingredient getIngredient(int a) {
        Ingredient ingredient = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM dhh_ingredient WHERE id = " + String.valueOf(a);
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                ingredient = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return ingredient;
    }
    
    public Ingredient getIngredient(String naam) {
        Ingredient ingredient = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM dhh_ingredient WHERE ingredientName = '" + naam + "';";
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first())
                ingredient = fetchItem(resultset);
        } catch (SQLException f) {
            log.log(Level.SEVERE, f.toString(), f);
        } finally {
            connection.closeConnection();
        }
        return ingredient;
    }
    
    public Set<Ingredient> updateIngredients() {
        Set<Ingredient> list = new HashSet<>();
        Ingredient ingredient;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "SELECT * FROM dhh_ingredient";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);

        try {
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("ingredientName");
                int inStock = resultset.getInt("inStock");
                int minStock = resultset.getInt("minStock");
                int maxStock = resultset.getInt("maxStock");
                // Create product
                ingredient = new Ingredient(id, name, inStock, minStock, maxStock);
                list.add(ingredient);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }

    private Ingredient fetchItem(ResultSet resultset) {
        // Instantiate
        Ingredient ingredient = null;
        try {
            // Get all data
            int id = resultset.getInt("id");
            String name = resultset.getString("ingredientName");
            int inStock = resultset.getInt("inStock");
            int minStock = resultset.getInt("minStock");
            int maxStock = resultset.getInt("maxStock");
            // Create product
            ingredient = new Ingredient(id, name, inStock, minStock, maxStock);

        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
        return ingredient;
    }

    public void addIngredient(Ingredient ingredient) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_ingredient` (`ingredientName`, `inStock`, `minStock`, `maxStock`) VALUES('"
            + ingredient.getName() + "','" + ingredient.getInStock() + "','" + ingredient.getMinStock() + "'," + ingredient.getMaxStock() + ");";
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }

    public void updateIngredient(Ingredient ingredient, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        int valueID = ingredient.getId();
        if (valueID == id) {
            connection.openConnection();
            log.log(Level.SEVERE, "ID's match, query is executed");
            String selectSQL = "UPDATE `martkic145_stunt`.`dhh_ingredient` SET `id` =" + valueID
            + ",`ingredientName` = '" + ingredient.getName() + "', `inStock` = '" + ingredient.getInStock()
            + "', `minStock` = '" + ingredient.getMinStock() + "', `maxStock` = '"
            + ingredient.getMaxStock() + "' WHERE `dhh_ingredient`.`id` = " + id;
            connection.executeSQLInsertStatement(selectSQL);
            connection.closeConnection();
        }
    }

    public void deleteIngredient(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_ingredient` WHERE `id` = " + id;
        connection.executeSQLDeleteStatement(selectSQL);
        connection.closeConnection();
    }



    public Set<Ingredient> getSearchedIngredients(String what, String att) {
        String selectSQL = "SELECT * FROM dhh_ingredient WHERE ";
        switch(att){
            case "ID":
                selectSQL += "id=" + what +";";
                break;
        
            case "Naam":
                selectSQL += "ingredientName LIKE '%" + what + "%';";
                break;
                
            case "Voorraad":
                selectSQL += "inStock=" + what + ";";
                break;
            
            case "Minimum Voorraad":
                selectSQL += "minStock=" + what + ";";
                break;
                
            case "Maximum Voorraad":
                selectSQL += "maxStock=" + what + ";";
                break;
                
            default:
                selectSQL += att + "=" + what;
                break;
        }
        Set<Ingredient> ingredientList;
        ingredientList = new HashSet<>();
        Ingredient ingredient;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
 
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);     
        try {
            while(resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("ingredientName");
                int inStock = resultset.getInt("inStock");
                int minStock = resultset.getInt("minStock");
                int maxStock = resultset.getInt("maxStock");
                // Create product
                ingredient = new Ingredient(id, name, inStock, minStock, maxStock);
                ingredientList.add(ingredient);
            }
        }
        catch (SQLException e) {
            log.log( Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return ingredientList;
    }
}
