package org.hibernate.lesson5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/databasetodolist";
        String username = "root";
        String password = "MySQLPassword";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connection successful! You are connected to the MySQL database.");
        } catch (SQLException e) {
            System.out.println("Connection failed. Error message: " + e.getMessage());
        }
    }
}