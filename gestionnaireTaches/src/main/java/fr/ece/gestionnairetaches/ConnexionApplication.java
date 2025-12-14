package fr.ece.gestionnairetaches;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConnexionApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ConnexionApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Connexion - Kanban Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
