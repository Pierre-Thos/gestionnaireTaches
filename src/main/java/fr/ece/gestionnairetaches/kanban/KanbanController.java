package fr.ece.gestionnairetaches.kanban;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KanbanController {

    @FXML
    private Button nouvelleTacheBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private HBox columns;

    @FXML
    private VBox colAFaire;

    @FXML
    private AnchorPane rootPane;

    private double offsetX;
    private double offsetY;

    @FXML
    private void initialize() {
        nouvelleTacheBtn.setOnAction(e -> creerTache());
    }

    private void creerTache() {
        //Creer le fond du conteneur de tache
        VBox task = new VBox();
        task.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
        task.setPrefSize(130, 80);

        //Ajoute une ellipse sur laquelle cliquer
        HBox bar = new HBox();
        bar.setStyle("-fx-background-color: #d9d9d9; -fx-padding: 3;");
        bar.setPrefHeight(15);
        bar.setAlignment(Pos.CENTER);

        Label ellipsis = new Label("⋯");
        bar.getChildren().add(ellipsis);


        TextArea content = new TextArea("Nouvelle tâche");
        content.setWrapText(true);
        content.setPrefSize(120, 55);

        task.getChildren().addAll(bar, content);
        colAFaire.getChildren().add(task);

        makeDraggable(task, bar);
    }

    //Fonction pour que le tache soit deplacable
    private void makeDraggable(Node node, Node handle) {
        handle.setOnMousePressed(e -> {
            offsetX = e.getSceneX() - node.localToScene(0,0).getX();
            offsetY = e.getSceneY() - node.localToScene(0,0).getY();
            if (node.getParent() instanceof VBox parent) {
                parent.getChildren().remove(node);
                rootPane.getChildren().add(node);
                Bounds b = node.localToScene(node.getBoundsInLocal());
                node.setLayoutX(b.getMinX());
                node.setLayoutY(b.getMinY());
            }
        });

        //Màj de la position de la tache quand elle est deplacee
        handle.setOnMouseDragged(e -> {
            node.setLayoutX(e.getSceneX() - offsetX);
            node.setLayoutY(e.getSceneY() - offsetY);
        });

        //Code pour supprimer une tache / l'aligner a une colonne
        handle.setOnMouseReleased(e -> {
            if (isOverDelete(node)) {
                rootPane.getChildren().remove(node);
            } else {
                snapToColumn(node);
            }
        });
    }

    //Detecte si la tache est suspendue sur le bouton delete
    private boolean isOverDelete(Node sticky) {
        Bounds s = sticky.localToScene(sticky.getBoundsInLocal());
        Bounds d = deleteBtn.localToScene(deleteBtn.getBoundsInLocal());
        return d.intersects(s);
    }


    //Choisit dans quelle colonne mettre la tache
    private void snapToColumn(Node sticky) {
        Bounds sb = sticky.localToScene(sticky.getBoundsInLocal());
        VBox best = null;
        double bestArea = 0;

        for (Node n : columns.getChildren()) {
            if (n instanceof VBox col) {
                Bounds cb = col.localToScene(col.getBoundsInLocal());
                double a = overlap(cb, sb);
                if (a > bestArea) {
                    bestArea = a;
                    best = col;
                }
            }
        }

        if (best != null) {
            rootPane.getChildren().remove(sticky);
            best.getChildren().add(sticky);
        }
    }


    private double overlap(Bounds a, Bounds b) {
        double x = Math.max(0, Math.min(a.getMaxX(), b.getMaxX()) - Math.max(a.getMinX(), b.getMinX()));
        double y = Math.max(0, Math.min(a.getMaxY(), b.getMaxY()) - Math.max(a.getMinY(), b.getMinY()));
        return x * y;
    }
}
