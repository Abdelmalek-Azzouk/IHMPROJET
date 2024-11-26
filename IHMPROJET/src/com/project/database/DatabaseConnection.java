package com.project.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:C:/Users/AmaTek/eclipse-workspace/IHMPROJET/src/DBfiles/vehicle_rental.db";

    // Method to get the database connection
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }
}
