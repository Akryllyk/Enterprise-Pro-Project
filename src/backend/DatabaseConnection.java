/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Alex
 */
import java.sql.*;

class DatabaseConnection {

    private Connection connection = null;

    /**
     * Constructor that takes in the url, username and password to establish a connection
     *
     * @param url of the database
     * @param username the username required to access the database
     * @param password the password required to access the database
     */
    public DatabaseConnection(String url, String username, String password) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Constructor for no parameter entry
     */
    public DatabaseConnection() {
        this("jdbc:mysql://lamp.scim.brad.ac.uk:3306/", "s", "s");
    }

    /**
     * Accessor method to get the connection to the database
     *
     * @return returns a Connection object that can be used to connect to the
     * database
     */
    public Connection GetConnection() {
        return this.connection;
    }
}
