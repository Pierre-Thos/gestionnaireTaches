module fr.ece.gestionnairetaches {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    // Ouvre les contrôleurs à JavaFX pour qu'il puisse les charger
    opens fr.ece.gestionnairetaches.controller to javafx.fxml;

    // Ouvre le package principal
    opens fr.ece.gestionnairetaches to javafx.fxml;

    exports fr.ece.gestionnairetaches;
}