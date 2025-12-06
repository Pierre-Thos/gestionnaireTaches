package fr.ece.gestionnairetaches.tableaudebord;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TableauDeBordController {

    @FXML
    private Button kanbanBtn;

    @FXML
    private Button connexionBtn;

    @FXML
    private Button projetsBtn;

    @FXML
    private Button formulairesBtn;

    @FXML
    private void initialize() {
        kanbanBtn.setOnAction(e -> open("/fr/ece/gestionnairetaches/kanban/kanban-view.fxml", kanbanBtn));
        connexionBtn.setOnAction(e -> open("/fr/ece/gestionnairetaches/connexion-view.fxml", connexionBtn));
        projetsBtn.setOnAction(e -> open("/fr/ece/gestionnairetaches/projets-view.fxml", projetsBtn));
        formulairesBtn.setOnAction(e -> open("/fr/ece/gestionnairetaches/formulaires-view.fxml", formulairesBtn));
    }

    private void open(String fxmlPath, Button btn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene newScene = new Scene(loader.load());
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(newScene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
