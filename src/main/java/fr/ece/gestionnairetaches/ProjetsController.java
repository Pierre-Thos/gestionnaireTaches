package fr.ece.gestionnairetaches;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ProjetsController {

    @FXML
    private TableView<Projet> tableProjets;

    @FXML
    private TableColumn<Projet, String> colNom;

    @FXML
    private TableColumn<Projet, String> colDescription;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtDescription;

    private final ObservableList<Projet> projets = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableProjets.setItems(projets);

        chargerProjetsDepuisBDD();
    }

    //Ajout
    @FXML
    private void handleAjouterProjet() {
        String nom = txtNom.getText();
        String description = txtDescription.getText();

        if (nom == null || nom.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Le nom du projet est obligatoire.");
            return;
        }

        String sql = "INSERT INTO projet (nom, description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nom);
            ps.setString(2, description);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                projets.add(new Projet(id, nom, description));
            }

            txtNom.clear();
            txtDescription.clear();

        } catch (SQLIntegrityConstraintViolationException e) {
            showAlert(Alert.AlertType.ERROR, "Un projet avec ce nom existe déjà.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du projet.");
        }
    }

    //Suppresion
    @FXML
    private void handleSupprimerProjet() {
        Projet selected = tableProjets.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.INFORMATION, "Veuillez sélectionner un projet.");
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Supprimer le projet \"" + selected.getNom() + "\" ?",
                ButtonType.YES, ButtonType.NO
        );

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                supprimerProjetBDD(selected);
            }
        });
    }

    private void supprimerProjetBDD(Projet projet) {
        String sql = "DELETE FROM projet WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projet.getId());
            ps.executeUpdate();
            projets.remove(projet);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la suppression.");
        }
    }

    //Chargement
    private void chargerProjetsDepuisBDD() {
        projets.clear();

        String sql = "SELECT id, nom, description FROM projet ORDER BY nom";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                projets.add(new Projet(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Impossible de charger les projets.");
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg, ButtonType.OK).showAndWait();
    }

    //Modele
    public static class Projet {
        private final int id;
        private final String nom;
        private final String description;

        public Projet(int id, String nom, String description) {
            this.id = id;
            this.nom = nom;
            this.description = description;
        }

        public int getId() { return id; }
        public String getNom() { return nom; }
        public String getDescription() { return description; }

        @Override
        public String toString() {
            return nom;
        }
    }
}
