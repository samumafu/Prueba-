package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/sakila";
    private static final String USER = "root";
    private static final String PASS = "Samumafuuff314.";

    // Devuelve una nueva conexión cada vez
    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
