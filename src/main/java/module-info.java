module fr.ece.gestionnairetaches {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    //requires org.controlsfx.controls;
    //requires com.dlsc.formsfx;
    //requires org.kordamp.bootstrapfx.core;
    //requires fr.ece.gestionnairetaches;
    requires javafx.graphics;
    //requires fr.ece.gestionnairetaches;
    // Ouvre les contrôleurs à JavaFX pour qu'il puisse les charger
    opens fr.ece.gestionnairetaches.controller to javafx.fxml;

    opens fr.ece.gestionnairetaches to javafx.fxml;
    opens fr.ece.gestionnairetaches.kanban to javafx.fxml;
    opens fr.ece.gestionnairetaches.tableaudebord to javafx.fxml;

    exports fr.ece.gestionnairetaches.kanban;
    exports fr.ece.gestionnairetaches.tableaudebord;
    exports fr.ece.gestionnairetaches;

}