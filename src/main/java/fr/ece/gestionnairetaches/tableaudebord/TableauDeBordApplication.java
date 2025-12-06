package fr.ece.gestionnairetaches.tableaudebord;

import fr.ece.gestionnairetaches.kanban.KanbanApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TableauDeBordApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TableauDeBordApplication.class.getResource("tableaudebord-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Tableau de bord");
        stage.setScene(scene);
        stage.show();
    }
}
