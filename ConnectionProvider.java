package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
    
    private static final String DB_NAME = "attendanceJframebd";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";
    
    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", DB_USERNAME, DB_PASSWORD);
            
            // Check if the database exists and create it if not
            if (!dataBaseExists(con, DB_NAME)) {
                createDatabase(con, DB_NAME);
            }
            
            // Reconnect to the specific database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
            return con;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static boolean dataBaseExists(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        return stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'").next();
    }
    
    private static void createDatabase(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        String query = "CREATE DATABASE " + dbName;
        stmt.executeUpdate(query);
        // Only the success message will be printed
        System.out.println("Database '" + dbName + "' created successfully.");
    }
}
