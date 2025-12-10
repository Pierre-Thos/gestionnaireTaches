package fr.ece.gestionnairetaches;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class FormulairesController {

    @FXML
    private ComboBox<String> projectCombo;

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private ListView<String> assigneesList;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        statusCombo.setItems(FXCollections.observableArrayList(
                "À faire",
                "En cours",
                "Terminé"
        ));

        projectCombo.setItems(FXCollections.observableArrayList(
                "Projet A",
                "Projet B",
                "Projet C"
        ));

        assigneesList.setItems(FXCollections.observableArrayList(
                "Nelson",
                "Pierre",
                "Yèmi",
                "Mahé"
        ));
        assigneesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setText("");
        }
    }

    @FXML
    private void onSave() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setText("");
        }

        String projet = projectCombo.getValue();
        String titre = titleField.getText();
        String statut = statusCombo.getValue();
        LocalDate echeance = dueDatePicker.getValue(); // non utilisé en base pour l'instant
        String description = descriptionArea.getText();
        ObservableList<String> assignees = assigneesList.getSelectionModel().getSelectedItems();

        if (projet == null || projet.isBlank()) {
            showError("Veuillez sélectionner un projet.");
            return;
        }
        if (titre == null || titre.isBlank()) {
            showError("Le titre de la tâche est obligatoire.");
            return;
        }
        if (statut == null || statut.isBlank()) {
            showError("Veuillez choisir un statut.");
            return;
        }

        String assigneesStr = "";
        if (assignees != null && !assignees.isEmpty()) {
            assigneesStr = String.join(", ", assignees);
        }

        String sql = "INSERT INTO tache (titre, description, statut, projet, assigne_a) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titre);
            ps.setString(2, description);
            ps.setString(3, statut);
            ps.setString(4, projet);
            ps.setString(5, assigneesStr);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur lors de l'enregistrement en base.");
            return;
        }

        titleField.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel() {
        titleField.getScene().getWindow().hide();
    }

    private void showError(String msg) {
        if (errorLabel != null) {
            errorLabel.setText(msg);
            errorLabel.setVisible(true);
        } else {
            System.err.println("Erreur: " + msg);
        }
    }
}
