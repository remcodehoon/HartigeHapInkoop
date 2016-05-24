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
    private final Set<Ingredient> list;
    
    public IngredientDAO(){
        list = new HashSet<>();
    }
    
    public Ingredient getIngredient(int a) {
        Ingredient ingredient = null;
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query

        String selectSQL = "SELECT * FROM dhh_ingredient WHERE id = " + String.valueOf(a);

        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                ingredient = fetchItem(resultset);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return ingredient;
    }
    
    public Set<Ingredient> getAllIngredients() {
         return list;
    }
    
    public void updateIngredients() {
        list.clear();
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
        
        String valueName = ingredient.getName();
        int valueInStock = ingredient.getInStock();
        int valueMinstock = ingredient.getMinStock();
        int valueMaxstock = ingredient.getMaxStock();

        String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_ingredient` (`ingredientName`, `inStock`, `minStock`, `maxStock`) VALUES('"
                + valueName + "','" + valueInStock + "','" + String.valueOf(valueMinstock) + "'," + String.valueOf(valueMaxstock) + ");";
        
        // Execute query
        connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }

    public void updateIngredient(Ingredient ingredient, int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        int valueID = ingredient.getId();
        if (valueID == id) {
            log.log(Level.SEVERE, "ID's match, query is executed");
            String valueName = ingredient.getName();
            int valueInStock = ingredient.getInStock();
            int valueMinstock = ingredient.getMinStock();
            int valueMaxstock = ingredient.getMaxStock();

            String selectSQL = "UPDATE `martkic145_stunt`.`dhh_ingredient` SET `id` =" + String.valueOf(valueID)
                    + ",`ingredientName` = '" + valueName + "', `inStock` = '" + String.valueOf(valueInStock)
                    + "', `minStock` = '" + String.valueOf(valueMinstock) + "', `maxStock` = '"
                    + String.valueOf(valueMaxstock) + "' WHERE `dhh_ingredient`.`id` = " + String.valueOf(id);

            
            boolean resultset = connection.executeSQLInsertStatement(selectSQL);
        } else {
            log.log(Level.SEVERE, "ID's DONT match, query is NOT executed");
            connection.closeConnection();
        }
    }

    public void deleteIngredient(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();

        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_ingredient` WHERE `id` = " + id;
        boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
        if (resultset) {
            log.log(Level.SEVERE, "Ingredient deleted");
        }
        connection.closeConnection();
    }

    public int getMaxID() {
        int id = 0;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT * FROM dhh_ingredient WHERE id = (SELECT max(id) FROM dhh_ingredient)";

        // Execute query
        ResultSet resultset2 = connection.executeSQLSelectStatement(selectSQL);
        
        try {
            if (resultset2.first()) {

                id = resultset2.getInt("id");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return id;
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
