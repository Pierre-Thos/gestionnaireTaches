package fr.ece.gestionnairetaches.model.dao;

import fr.ece.gestionnairetaches.model.entity.Role;
import fr.ece.gestionnairetaches.model.entity.Utilisateur;
import fr.ece.gestionnairetaches.utils.DatabaseConnection;
import fr.ece.gestionnairetaches.utils.PasswordHasher;
import java.sql.*;

public class UtilisateurDAO {

    // On ajoute nom et prenom dans les arguments
    public boolean inscrire(String email, String password) {

        String sqlRole = "SELECT id FROM role WHERE nom = 'COLLABORATEUR'";
        String sqlInsert =
                "INSERT INTO utilisateur (email, mot_de_passe, role_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            int roleId;

            try (PreparedStatement stmt = conn.prepareStatement(sqlRole);
                 ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) return false;
                roleId = rs.getInt("id");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setString(1, email);
                stmt.setString(2, PasswordHasher.hashPassword(password));
                stmt.setInt(3, roleId);
                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Utilisateur authentifier(String email, String password) {

        String sql =
                "SELECT u.id, u.email, u.mot_de_passe, r.id AS rid, r.nom AS rnom " +
                        "FROM utilisateur u " +
                        "JOIN role r ON u.role_id = r.id " +
                        "WHERE u.email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("mot_de_passe");

                if (PasswordHasher.checkPassword(password, hash)) {
                    return new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("email"),
                            new Role(
                                    rs.getInt("rid"),
                                    rs.getString("rnom")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}