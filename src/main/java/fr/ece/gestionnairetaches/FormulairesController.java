package fr.ece.gestionnairetaches;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        // Statuts Kanban
        statusCombo.setItems(FXCollections.observableArrayList(
                "À faire",
                "En cours",
                "Terminé"
        ));

        // Projets (temporaire, juste pour tester l'UI)
        projectCombo.setItems(FXCollections.observableArrayList(
                "Projet A",
                "Projet B",
                "Projet C"
        ));

        // Utilisateurs / collaborateurs (temporaire)
        assigneesList.setItems(FXCollections.observableArrayList(
                "Alice",
                "Bob",
                "Charlie",
                "Nelson",
                "Pierre",
                "Yèmi",
                "Mahé"
        ));
        assigneesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        errorLabel.setVisible(false);
    }

    @FXML
    private void onSave() {
        errorLabel.setVisible(false);
        errorLabel.setText("");

        String projet = projectCombo.getValue();
        String titre = titleField.getText();
        String statut = statusCombo.getValue();
        LocalDate echeance = dueDatePicker.getValue();
        String description = descriptionArea.getText();
        var assignees = assigneesList.getSelectionModel().getSelectedItems();

        // Validation minimale
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

        // Pour l'instant, on simule juste un enregistrement en affichant en console
        System.out.println("===== Nouvelle tâche (simulation) =====");
        System.out.println("Projet     : " + projet);
        System.out.println("Titre      : " + titre);
        System.out.println("Statut     : " + statut);
        System.out.println("Échéance   : " + echeance);
        System.out.println("Assignée à : " + assignees);
        System.out.println("Description: " + description);
        System.out.println("=======================================");

        // TODO : ici tu appelleras ton DAO pour faire un INSERT en MySQL

        // Ferme la fenêtre après "enregistrement"
        titleField.getScene().getWindow().hide();
    }

    @FXML
    private void onCancel() {
        // Fermer la fenêtre sans rien faire
        titleField.getScene().getWindow().hide();
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}
