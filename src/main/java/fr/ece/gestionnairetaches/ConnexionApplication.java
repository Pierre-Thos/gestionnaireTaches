package fr.ece.gestionnairetaches;

import fr.ece.gestionnairetaches.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static fr.ece.gestionnairetaches.utils.SceneManager.stage;

public class ConnexionApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ConnexionApplication.class.getResource("LoginView.fxml"));
        SceneManager.setStage(stage);

        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Connexion - Kanban Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
