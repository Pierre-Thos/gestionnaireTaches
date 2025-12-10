package fr.ece.gestionnairetaches.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        // Modifie root et "" selon ta config
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/kanban", "root", "");
    }
}