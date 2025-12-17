package fr.ece.gestionnairetaches.controller;

import fr.ece.gestionnairetaches.model.dao.UtilisateurDAO;
import fr.ece.gestionnairetaches.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignupController {

    // 👇 AJOUT DES NOUVEAUX CHAMPS

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UtilisateurDAO dao = new UtilisateurDAO();

    @FXML
    public void handleSignup() {

        String email = emailField.getText().trim().toLowerCase();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Tous les champs sont obligatoires.");
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorLabel.setText("Format d'email invalide.");
            return;
        }

        if (password.length() < 6) {
            errorLabel.setText("Mot de passe trop court.");
            return;
        }

        if (dao.inscrire(email, password)) {
            SceneManager.changeScene("LoginView.fxml", "Compte créé !");
        } else {
            errorLabel.setText("Email déjà utilisé.");
        }
    }


    @FXML
    public void backToLogin() {
        SceneManager.changeScene("LoginView.fxml", "Connexion");
    }
}