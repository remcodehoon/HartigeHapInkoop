package datastore;

import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Ingredient;
import static java.util.Comparator.comparing;
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

        try {
            for(Ingredient i : list){
                if(i.getId() == a){
                    ingredient = new Ingredient(i.getId(),i.getName(),i.getInStock(),i.getMinStock(),i.getMaxStock());
                }
            }
            return ingredient;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
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
        
        String valueName = ingredient.getName();
        int valueInStock = ingredient.getInStock();
        int valueMinstock = ingredient.getMinStock();
        int valueMaxstock = ingredient.getMaxStock();
        boolean succes = true;
        try {
            connection.openConnection();
            String selectSQL = "INSERT INTO `martkic145_stunt`.`dhh_ingredient` (`ingredientName`, `inStock`, `minStock`, `maxStock`) VALUES('"
                + valueName + "','" + valueInStock + "','" + String.valueOf(valueMinstock) + "'," + String.valueOf(valueMaxstock) + ");";
            connection.executeSQLInsertStatement(selectSQL);
        } catch (Exception e) {
            succes = false;
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            if(succes)
                list.add(ingredient); 
            connection.closeConnection();
        }
        
        
    }

    public void updateIngredient(Ingredient ingredient, int id) {
        DatabaseConnection connection = new DatabaseConnection();
            try {
                int valueID = ingredient.getId();
                if (valueID == id) {
                    connection.openConnection();
                    log.log(Level.SEVERE, "ID's match, query is executed");
                    String valueName = ingredient.getName();
                    int valueInStock = ingredient.getInStock();
                    int valueMinstock = ingredient.getMinStock();
                    int valueMaxstock = ingredient.getMaxStock();
                    String selectSQL = "UPDATE `martkic145_stunt`.`dhh_ingredient` SET `id` =" + String.valueOf(valueID)
                    + ",`ingredientName` = '" + valueName + "', `inStock` = '" + String.valueOf(valueInStock)
                    + "', `minStock` = '" + String.valueOf(valueMinstock) + "', `maxStock` = '"
                    + String.valueOf(valueMaxstock) + "' WHERE `dhh_ingredient`.`id` = " + String.valueOf(id);
                    connection.executeSQLInsertStatement(selectSQL);
                    list.stream().filter((i) -> (i.getId() == id)).map((i) -> {
                        i.setName(valueName);
                        return i;
                    }).map((i) -> {
                        i.setAttInt("inStock",valueInStock);
                        return i;
                    }).map((i) -> {
                        i.setAttInt("minStock",valueMinstock);
                        return i;
                    }).forEach((i) -> {
                        i.setAttInt("maxStock",valueMaxstock);
                    });
                }
            } catch(Exception e) {
                log.log(Level.SEVERE, e.toString(), e);
            } finally {
                connection.closeConnection();
            }
    }

    public void deleteIngredient(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            connection.openConnection();
            String selectSQL = "DELETE FROM `martkic145_stunt`.`dhh_ingredient` WHERE `id` = " + id;
            boolean resultset = connection.executeSQLDeleteStatement(selectSQL);
            if (resultset) {
                log.log(Level.SEVERE, "Ingredient deleted");
            }
            list.stream().filter((i) -> (i.getId() == id)).forEach((i) -> {
                list.remove(i);
            });
        } catch(Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        
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
