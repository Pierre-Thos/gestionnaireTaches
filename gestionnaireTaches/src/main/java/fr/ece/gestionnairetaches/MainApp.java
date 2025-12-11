package fr.ece.gestionnairetaches;

import fr.ece.gestionnairetaches.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // On initialise le SceneManager
        SceneManager.setStage(primaryStage);
        // On lance la première vue : LOGIN
        SceneManager.changeScene("LoginView.fxml", "Connexion");
    }

    public static void main(String[] args) {
        launch(args);
    }
}