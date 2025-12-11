package fr.ece.gestionnairetaches.model.dao;

import fr.ece.gestionnairetaches.model.entity.Role;
import fr.ece.gestionnairetaches.model.entity.Utilisateur;
import fr.ece.gestionnairetaches.utils.DatabaseConnection;
import fr.ece.gestionnairetaches.utils.PasswordHasher;
import java.sql.*;

public class UtilisateurDAO {

    // On ajoute nom et prenom dans les arguments
    public boolean inscrire(String nom, String prenom, String email, String password) {
        String sqlRole = "SELECT id FROM role WHERE nom = 'COLLABORATEUR'";
        // La requête SQL change pour inclure nom et prenom
        String sqlInsert = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            int roleId = 1;

            try (PreparedStatement stmt = conn.prepareStatement(sqlRole);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) roleId = rs.getInt("id");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, email);
                stmt.setString(4, PasswordHasher.hashPassword(password));
                stmt.setInt(5, roleId);

                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Utilisateur authentifier(String email, String password) {
        // On récupère aussi nom et prenom
        String sql = "SELECT u.id, u.nom, u.prenom, u.email, u.mot_de_passe, r.id as rid, r.nom as rnom " +
                "FROM utilisateur u JOIN role r ON u.role_id = r.id WHERE u.email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (PasswordHasher.checkPassword(password, rs.getString("mot_de_passe"))) {
                    // On crée l'utilisateur avec ses nouvelles infos
                    return new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            new Role(rs.getInt("rid"), rs.getString("rnom"))
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}