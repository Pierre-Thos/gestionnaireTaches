package fr.ece.gestionnairetaches;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // À adapter à ta config locale MySQL
    private static final String URL =
            "jdbc:mysql://localhost:4022/gestionnaire_taches?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";          // ton user
    private static final String PASSWORD = "root"; // ton mot de passe

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
