package fr.ece.gestionnairetaches.kanban;

import fr.ece.gestionnairetaches.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.*;

public class KanbanController {

    @FXML private AnchorPane rootPane;
    @FXML private VBox todoCol;
    @FXML private VBox doingCol;
    @FXML private VBox doneCol;

    private double offsetX;
    private double offsetY;

    @FXML
    private void initialize() {
        loadTasks();
    }

    private void loadTasks() {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id, texte, colonne FROM tache")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String texte = rs.getString("texte");
                int col = rs.getInt("colonne");

                VBox sticky = createSticky(id, texte);
                getColumn(col).getChildren().add(sticky);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createSticky(int id, String texte) {
        Label label = new Label(texte);
        label.setWrapText(true);

        VBox box = new VBox(label);
        box.setUserData(id);
        box.setStyle("-fx-background-color: #f5f5a0; -fx-padding: 8; -fx-border-color: black;");
        box.setPrefWidth(180);

        makeDraggable(box);
        return box;
    }

    private void makeDraggable(Node node) {
        node.setOnMousePressed(e -> {
            offsetX = e.getSceneX() - node.localToScene(0, 0).getX();
            offsetY = e.getSceneY() - node.localToScene(0, 0).getY();
            ((VBox) node.getParent()).getChildren().remove(node);
            rootPane.getChildren().add(node);
        });

        node.setOnMouseDragged(e -> {
            node.setLayoutX(e.getSceneX() - offsetX);
            node.setLayoutY(e.getSceneY() - offsetY);
        });

        node.setOnMouseReleased(e -> snapToColumn(node));
    }

    private void snapToColumn(Node sticky) {
        Bounds sb = sticky.localToScene(sticky.getBoundsInLocal());
        VBox best = null;
        int bestCol = -1;
        double bestArea = 0;

        VBox[] cols = { todoCol, doingCol, doneCol };

        for (int i = 0; i < cols.length; i++) {
            Bounds cb = cols[i].localToScene(cols[i].getBoundsInLocal());
            double a = overlap(cb, sb);
            if (a > bestArea) {
                bestArea = a;
                best = cols[i];
                bestCol = i;
            }
        }

        if (best != null) {
            rootPane.getChildren().remove(sticky);
            best.getChildren().add(sticky);
            updateColumn((int) sticky.getUserData(), bestCol);
        }
    }

    private void updateColumn(int id, int col) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE tache SET colonne = ? WHERE id = ?")) {
            ps.setInt(1, col);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double overlap(Bounds a, Bounds b) {
        double x = Math.max(0, Math.min(a.getMaxX(), b.getMaxX()) - Math.max(a.getMinX(), b.getMinX()));
        double y = Math.max(0, Math.min(a.getMaxY(), b.getMaxY()) - Math.max(a.getMinY(), b.getMinY()));
        return x * y;
    }

    private VBox getColumn(int c) {
        return c == 0 ? todoCol : c == 1 ? doingCol : doneCol;
    }
}
