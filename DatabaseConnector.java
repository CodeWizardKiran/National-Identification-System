package com.nis.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    // Provide your actual database credentials here
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Yout_Database_Name";
    private static final String DB_USERNAME = "Username";
    private static final String DB_PASSWORD = "password";

    // Private constructor to prevent instantiation
    private DatabaseConnector() {
    }

    // Static method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
