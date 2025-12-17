package fr.ece.gestionnairetaches.kanban;

import fr.ece.gestionnairetaches.DatabaseConnection;
import fr.ece.gestionnairetaches.model.entity.Utilisateur;
import fr.ece.gestionnairetaches.utils.SceneManager;
import fr.ece.gestionnairetaches.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

import java.sql.*;

public class KanbanController {

    @FXML public Button retourBtn;
    @FXML private AnchorPane rootPane;
    @FXML private VBox todoCol;
    @FXML private VBox doingCol;
    @FXML private VBox doneCol;

    private double offsetX;
    private double offsetY;

    @FXML
    private void initialize() {

        if (SessionManager.getInstance().getCurrentUser() == null) {
            SceneManager.changeScene("LoginView.fxml", "Connexion requise");
            return;
        }

        Utilisateur user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            loadTasks(user.getId());
        }
    }

    @FXML
    private void retourTableauDeBord() {
        SceneManager.changeScene("tableaudebord/tableaudebord-view.fxml", "Tableau de Bord");
    }

    private void loadTasks(int userId) {

        String sql = """
        SELECT id, texte, colonne, date_echeance
        FROM tache
        WHERE user_id = ?
        ORDER BY created_at
    """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String texte = rs.getString("texte");
                int col = rs.getInt("colonne");
                Date date = rs.getDate("date_echeance");

                VBox sticky = createSticky(
                        id,
                        texte,
                        date != null ? date.toLocalDate() : null
                );

                VBox column = getColumn(col);
                if (column != null) {
                    column.getChildren().add(sticky);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private VBox createSticky(int id, String texte, LocalDate dateEcheance) {

        Label title = new Label(texte);
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        title.setWrapText(true);


        Label deadline = new Label(
                dateEcheance != null ? "📅 " + dateEcheance : ""
        );
        deadline.setStyle("-fx-font-size: 10px; -fx-text-fill: #555;");

        VBox box = new VBox(4, title, deadline);
        box.setPadding(new Insets(8));
        box.setPrefWidth(180);
        box.setUserData(id);

        box.setStyle("""
        -fx-background-color: #fff8b5;
        -fx-border-color: #c2b900;
        -fx-border-radius: 5;
        -fx-background-radius: 5;
    """);

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
