package datastore;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnection {
    
    private Connection connection;
    private Statement statement;
    
    public DatabaseConnection()
    {
        connection = null;
        statement = null;
    }
    
    public boolean openConnection()
    {
        boolean result = false;
        if(connection == null)
        {
            Properties prop = new Properties();
            InputStream input = null;
        
            try
            {   
                input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);
                
                connection = DriverManager.getConnection(
                    "jdbc:mysql://" + prop.getProperty("ip") + "/" + prop.getProperty("dbname") , prop.getProperty("dbuser"), prop.getProperty("dbpassword"));

                if(connection != null)
                {
                    statement = connection.createStatement();
                }
                else
                
                result = true;
            }
            catch(SQLException e)
            {
                System.out.println(e);
                result = false;
            }
            catch (IOException ex) {
		ex.printStackTrace();
            } 
            finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
            }
        }
        else
        {
            // A connection was already initalized.
            result = true;
        }
        
        return result;
    }
    
    public boolean connectionIsOpen()
    {
        boolean open = false;
        
        if(connection != null && statement != null)
        {
            try
            {
                open = !connection.isClosed() && !statement.isClosed();
            }
            catch(SQLException e)
            {
                System.out.println(e);
                open = false;
            }
        }
        // Else, at least one the connection or statement fields is null, so
        // no valid connection.
        
        return open;
    }
    
    public void closeConnection()
    {
        try
        {
            statement.close();
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public ResultSet executeSQLSelectStatement(String query)
    {
        ResultSet resultset = null;
        
        // First, check whether a some query was passed and the connection with
        // the database.
        if(query != null && connectionIsOpen())
        {
            // Then, if succeeded, execute the query.
            try
            {
                resultset = statement.executeQuery(query);
            }
            catch(SQLException e)
            {
                System.out.println(e);
                resultset = null;
            }
        }
        
        return resultset;
    }
    
    public boolean executeSQLDeleteStatement(String query)
    {
        boolean result = false;
        
        // First, check whether a some query was passed and the connection with
        // the database.
        if(query != null && connectionIsOpen())
        {
            // Then, if succeeded, execute the query.
            try
            {
                statement.executeUpdate(query);
                result = true;
            }
            catch(SQLException e)
            {
                System.out.println(e);
                result = false;
            }
        }
        
        return result;
    }
    
    public boolean executeSQLInsertStatement(String query)
    {
        // First, check whether a some query was passed and the connection
        // with the database.
        boolean gelukt = false;
        if (query != null && connectionIsOpen()) {
            try {
                statement.executeUpdate(query);
                gelukt = true;
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return gelukt;
    }
}

