package fr.ece.gestionnairetaches.controller;

import fr.ece.gestionnairetaches.model.dao.UtilisateurDAO;
import fr.ece.gestionnairetaches.model.entity.Utilisateur;
import fr.ece.gestionnairetaches.utils.SceneManager;
import fr.ece.gestionnairetaches.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UtilisateurDAO dao = new UtilisateurDAO();

    @FXML
    public void handleLogin() {
        Utilisateur user = dao.authentifier(emailField.getText(), passwordField.getText());
        if (user != null) {
            SessionManager.getInstance().login(user);
            // Redirection vers le Dashboard
            SceneManager.changeScene("DashboardView.fxml", "Tableau de Bord");
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
        }
    }

    @FXML
    public void goToSignup() {
        SceneManager.changeScene("SignupView.fxml", "Créer un compte");
    }
}