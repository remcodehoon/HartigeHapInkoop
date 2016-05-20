package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Ingredient;
import java.util.ArrayList;

public class IngredientDAO {
    
    public Ingredient getIngredient(int a) {
        Ingredient ingredient = null;
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query
        
        String selectSQL = "SELECT * FROM dhh_ingredient WHERE id = " + String.valueOf(a);
        System.out.println(selectSQL);

        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                ingredient = fetchItem(resultset);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return ingredient;
    }
    
    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient = null;
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        
        String selectSQL = "SELECT * FROM dhh_ingredient";
        
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
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
        return ingredientList;
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
            System.out.println(e);
        }
        return ingredient;
    }

    public void addIngredient(Ingredient ingredient) {        
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        //int valueID = ingredient.getId();
        String valueName = ingredient.getName();
        int valueInStock = ingredient.getInStock();
        int valueMinstock = ingredient.getMinStock();
        int valueMaxstock = ingredient.getMaxStock();
        
        String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_ingredient` (`ingredientName`, `inStock`, `minStock`, `maxStock`) VALUES('"
                + valueName + "','" + valueInStock + "','" + String.valueOf(valueMinstock) + "'," + String.valueOf(valueMaxstock) + ");";
        //System.out.println(selectSQL);
        // Execute query
        boolean resultset = connection.executeSQLInsertStatement(selectSQL);
        connection.closeConnection();
    }
    
    public void updateIngredient(Ingredient ingredient, int id) {     
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        int valueID = ingredient.getId();
        if(valueID == id)
        {
            System.out.println("ID's match, query is executed");
            String valueName = ingredient.getName();
            int valueInStock = ingredient.getInStock();
            int valueMinstock = ingredient.getMinStock();
            int valueMaxstock = ingredient.getMaxStock();

            String selectSQL = "UPDATE `martkic145_stunt`.`dhh_ingredient` SET `id` =" + String.valueOf(valueID) + 
                    ",`ingredientName` = '" + valueName + "', `inStock` = '" + String.valueOf(valueInStock) + 
                    "', `minStock` = '" + String.valueOf(valueMinstock) + "', `maxStock` = '" + 
                    String.valueOf(valueMaxstock) + "' WHERE `dhh_ingredient`.`id` = " + String.valueOf(id);

            //System.out.println(selectSQL);
            boolean resultset = connection.executeSQLInsertStatement(selectSQL);
        }
        else {
            System.out.println("ID's DONT match, query is NOT executed");
            connection.closeConnection();
        }
    }
    
    public void deleteIngredient(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        
        String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_ingredient` WHERE `id` = " + id;
        boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
        if (resultset) {
            System.out.println("Ingredient deleted.");
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
        //System.out.println(resultset);
        try {
            if (resultset2.first()) {
                
                id = resultset2.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return id;
    }
}
