/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Remco
 */
public class LoginDAO {
    
    private static final Logger log = Logger.getLogger(LoginDAO.class.getName());
    
    public boolean checkLoginInfo(String username, String password) {
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query
        String selectSQL = "SELECT * FROM dhh_employee WHERE userName = \"" + username + "\" AND password = \"" + password + "\"";
        
        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                return true;
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
            return false;
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return false;
    }
    
}