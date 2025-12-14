package fr.ece.gestionnairetaches;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent; 

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

    
    @FXML
    private Button btnAjouter; 
    
    @FXML
    private Button btnSupprimer; 
    

   
    private final ObservableList<Projet> projets = FXCollections.observableArrayList();



    @FXML
    public void initialize() {
     
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
      
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        

      
        tableProjets.setItems(projets);
        
 
        projets.addAll(
                new Projet("Projet Java", "Application gestionnaire de tâches"),
                new Projet("Projet Réseau", "Rapport sur le routage OSPF")
        );
    }
    

     
    @FXML
    private void handleAjouterProjet(ActionEvent event) {
        String nom = txtNom.getText();
        String description = txtDescription.getText();

      
        if (nom == null || nom.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Le nom du projet est obligatoire !", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        
   
        Projet nouveauProjet = new Projet(nom, description);
        projets.add(nouveauProjet); 

      
        txtNom.clear();
        txtDescription.clear();
    }
    

    @FXML
    private void handleSupprimerProjet(ActionEvent event) {
    
        Projet projetSelectionne = tableProjets.getSelectionModel().getSelectedItem();

        if (projetSelectionne != null) {
         
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                                    "Êtes-vous sûr de vouloir supprimer le projet : " + projetSelectionne.getNom() + " ?",
                                    ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    projets.remove(projetSelectionne);
                }
            });
        } else {
        
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez sélectionner un projet à supprimer.", ButtonType.OK);
            alert.showAndWait();
        }
    }



 
    public static class Projet {
        private String nom;
        private String description;

        public Projet(String nom, String description) {
            this.nom = nom;
            this.description = description;
        }

      
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        @Override
        public String toString() {
            return nom;
        }
    }
    
   
    public void ajouterProjet(String nom, String description) {
        projets.add(new Projet(nom, description));
    }
}
