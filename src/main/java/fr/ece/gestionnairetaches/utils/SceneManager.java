package fr.ece.gestionnairetaches.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void changeScene(String fxmlFile, String title) {
        try {
            // Si le stage n'a pas encore été défini, on le récupère automatiquement
            if (stage == null) {
                Window window = Stage.getWindows()
                        .stream()
                        .filter(Window::isShowing)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Aucune fenêtre active trouvée"));

                stage = (Stage) window;
            }

            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/fr/ece/gestionnairetaches/" + fxmlFile)
            );

            Parent root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Impossible de charger la vue : " + fxmlFile);
        }
    }
}
