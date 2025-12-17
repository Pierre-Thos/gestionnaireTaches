package fr.ece.gestionnairetaches;

import fr.ece.gestionnairetaches.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class FormulairesController {

    @FXML private ComboBox<String> projectCombo;
    @FXML private TextField titleField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<String> assigneeCombo;
    @FXML private TextArea descriptionArea;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        statusCombo.setItems(FXCollections.observableArrayList(
                "À faire", "En cours", "Terminé"
        ));

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT nom FROM projet");
            ObservableList<String> projets = FXCollections.observableArrayList();
            while (rs.next()) projets.add(rs.getString("nom"));
            projectCombo.setItems(projets);

            rs = st.executeQuery("SELECT email FROM utilisateur");
            ObservableList<String> users = FXCollections.observableArrayList();
            while (rs.next()) users.add(rs.getString("email"));
            assigneeCombo.setItems(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSave() {
        String titre = titleField.getText();
        String statut = statusCombo.getValue();
        String projetNom = projectCombo.getValue();
        String userEmail = assigneeCombo.getValue();
        LocalDate echeance = dueDatePicker.getValue();
        String description = descriptionArea.getText();

        if (titre == null || titre.isBlank() || statut == null || projetNom == null) {
            errorLabel.setText("Champs obligatoires manquants");
            return;
        }

        int colonne = switch (statut) {
            case "À faire" -> 0;
            case "En cours" -> 1;
            default -> 2;
        };

        try (Connection c = DatabaseConnection.getConnection()) {

            int projetId;
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id FROM projet WHERE nom = ?")) {
                ps.setString(1, projetNom);
                ResultSet rs = ps.executeQuery();
                rs.next();
                projetId = rs.getInt(1);
            }

            Integer userId = null;
            if (userEmail != null) {
                try (PreparedStatement ps = c.prepareStatement(
                        "SELECT id FROM utilisateur WHERE email = ?")) {
                    ps.setString(1, userEmail);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) userId = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO tache (texte, colonne, projet_id, user_id, date_echeance) VALUES (?, ?, ?, ?, ?)")) {
                ps.setString(1, titre);
                ps.setInt(2, colonne);
                ps.setInt(3, projetId);
                if (userId != null) ps.setInt(4, userId);
                else ps.setNull(4, Types.INTEGER);
                ps.setDate(5, echeance != null ? Date.valueOf(echeance) : null);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Erreur base de données");
            return;
        }

        SceneManager.changeScene("tableaudebord/tableaudebord-view.fxml", "Tableau de Bord");
        //titleField.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel() {
        SceneManager.changeScene("tableaudebord/tableaudebord-view.fxml", "Tableau de Bord");
    }
}
