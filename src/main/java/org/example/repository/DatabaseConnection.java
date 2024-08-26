package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/aston_servlets";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Qwerty123456";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver"); // without calling this method I get error: "No suitable driver found"
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
