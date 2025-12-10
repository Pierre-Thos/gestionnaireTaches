package fr.ece.gestionnairetaches.controller;

import fr.ece.gestionnairetaches.model.entity.Utilisateur;
import fr.ece.gestionnairetaches.utils.SessionManager;
import fr.ece.gestionnairetaches.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        Utilisateur user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            // On affiche le Prénom et le Nom au lieu de l'email
            String nomComplet = user.getPrenom() + " " + user.getNom();
            welcomeLabel.setText("Bonjour " + nomComplet + " !");
        }
    }

    @FXML
    public void handleLogout() {
        SessionManager.getInstance().logout();
        SceneManager.changeScene("LoginView.fxml", "Connexion");
    }
}
