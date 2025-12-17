package fr.ece.gestionnairetaches.controller;

import fr.ece.gestionnairetaches.model.dao.UtilisateurDAO;
import fr.ece.gestionnairetaches.model.entity.Utilisateur;
import fr.ece.gestionnairetaches.utils.SceneManager;
import fr.ece.gestionnairetaches.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button btnCreerCompte;
    @FXML private Button retourBtn;

    private final UtilisateurDAO dao = new UtilisateurDAO();

    @FXML
    public void handleLogin() {
        Utilisateur user = dao.authentifier(emailField.getText(), passwordField.getText());
        if (user != null) {
            SessionManager.getInstance().login(user);
            SceneManager.changeScene("tableaudebord/tableaudebord-view.fxml", "Tableau de Bord");
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
        }
    }

    @FXML
    public void retourTableau() {
        SceneManager.changeScene("tableaudebord/tableaudebord-view.fxml", "Tableau de Bord");
    }


    public void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fr/ece/gestionnairetaches/SignupView.fxml")
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Créer un compte");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
