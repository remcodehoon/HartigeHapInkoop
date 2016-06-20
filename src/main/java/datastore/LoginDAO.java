/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore;

import domain.Employee;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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
        String selectSQL = "SELECT id FROM employee WHERE userName = \"" + username + "\" AND password = \"" + password + "\" LIMIT 1";
        
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
    
    public int getEmployeeFunctionId(String username, String password) {
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query
        String selectSQL = "SELECT functionId FROM employee WHERE userName = \"" + username + "\" AND password = \"" + password + "\" AND (functionId = 7 OR functionId = 8) LIMIT 1";
        
        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                return resultset.getInt("functionId");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
            return 0;
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return 0;
    }
    
    public int getEmployeeFunctionId(int id) {
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query
        String selectSQL = "SELECT functionId FROM employee WHERE id = " + id + " AND (functionId = 7 OR functionId = 8) LIMIT 1";
        
        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                return resultset.getInt("functionId");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
            return 0;
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return 0;
    }
    
    
    
    public int getEmployeeId(String username, String password) {
        // Instantiate
        DatabaseConnection connection = new DatabaseConnection();
        // Open connection
        connection.openConnection();
        // Query
        String selectSQL = "SELECT id FROM employee WHERE userName = \"" + username + "\" AND password = \"" + password + "\" LIMIT 1";
        
        // Execute query
        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            if (resultset.first()) {
                return resultset.getInt("id");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
            return 0;
        } finally {
            // Close the connection to the database
            connection.closeConnection();
        }
        return 0;
    }
    
    public Set<Employee> getEmployees() {
        Set<Employee> list = new HashSet<>();
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection();
        String selectSQL = "SELECT id,userName,firstName FROM employee WHERE functionId = 7 OR functionId = 8";

        ResultSet resultset = connection.executeSQLSelectStatement(selectSQL);
        try {
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String userName = resultset.getString("userName");
                String name = resultset.getString("firstName");
                Employee emp = new Employee(id, userName, name);
                list.add(emp);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.toString(), e);
        } finally {
            connection.closeConnection();
        }
        return list;
    }
    
}