module fr.ece.gestionnairetaches {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    //requires fr.ece.gestionnairetaches;
    requires javafx.graphics;

    opens fr.ece.gestionnairetaches to javafx.fxml;
    opens fr.ece.gestionnairetaches.kanban to javafx.fxml;

    exports fr.ece.gestionnairetaches.kanban;

}