package fr.ece.gestionnairetaches.controller;

import fr.ece.gestionnairetaches.model.dao.UtilisateurDAO;
import fr.ece.gestionnairetaches.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignupController {

    // 👇 AJOUT DES NOUVEAUX CHAMPS
    @FXML private TextField nomField;
    @FXML private TextField prenomField;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UtilisateurDAO dao = new UtilisateurDAO();

    @FXML
    public void handleSignup() {
        // 1. On récupère les 4 valeurs
// .trim() enlève les espaces inutiles avant et après
// .toUpperCase() met le nom en majuscule
        String nom = nomField.getText().trim().toUpperCase();

// Met la première lettre du prénom en majuscule, le reste en minuscule
        String prenomBrut = prenomField.getText().trim();
        String prenom = prenomBrut.substring(0, 1).toUpperCase() + prenomBrut.substring(1).toLowerCase();

        String email = emailField.getText().trim().toLowerCase(); // Email toujours en minuscule
        String password = passwordField.getText();


        // 2. Vérification que tout est rempli
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Remplissez tout !");
            return;
        }
        // Vérifie si l'email ressemble à xxxx@xxxx.xx
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorLabel.setText("Format d'email invalide !");
            return;
        }

// Vérifie si le mot de passe est assez fort (min 6 caractères)
        if (password.length() < 6) {
            errorLabel.setText("Le mot de passe doit faire 6 caractères minimum.");
            return;
        }

        // 3. Appel de la méthode inscrire avec les 4 ARGUMENTS
        if (dao.inscrire(nom, prenom, email, password)) {
            // Succès
            SceneManager.changeScene("LoginView.fxml", "Compte créé ! Connectez-vous.");
        } else {
            // Échec
            errorLabel.setText("Erreur : Email déjà pris ?");
        }
    }

    @FXML
    public void backToLogin() {
        SceneManager.changeScene("LoginView.fxml", "Connexion");
    }
}