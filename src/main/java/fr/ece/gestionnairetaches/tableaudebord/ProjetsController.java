package fr.ece.gestionnairetaches;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjetsController {

    // --- Lien avec les éléments FXML ---
    @FXML
    private TableView<Projet> tableViewProjets;

    @FXML
    private TableColumn<Projet, String> colNom;

    @FXML
    private TableColumn<Projet, String> colDescription;

    // --- Liste observable qui alimente le tableau ---
    private final ObservableList<Projet> projets = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Associer colonnes ↔ attributs de la classe Projet
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Exemple de données (tu pourras supprimer)
        projets.addAll(
                new Projet("Projet Java", "Application gestionnaire de tâches"),
                new Projet("Projet Réseau", "Rapport sur le routage OSPF")
        );

        tableViewProjets.setItems(projets);
    }

    // --- Méthode pour ajouter un projet depuis un autre écran ---
    public void ajouterProjet(String nom, String description) {
        projets.add(new Projet(nom, description));
    }

    // --- Classe interne modèle Projet ---
    public static class Projet {
        private String nom;
        private String description;

        public Projet(String nom, String description) {
            this.nom = nom;
            this.description = description;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public int getIdChefProjet() { return idChefProjet; }
    public void setIdChefProjet(int idChefProjet) { this.idChefProjet = idChefProjet; }

    @Override
    public String toString() {
        return nom;
    }
}

