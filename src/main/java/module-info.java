module fr.ece.gestionnairetaches {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens fr.ece.gestionnairetaches to javafx.fxml;
    exports fr.ece.gestionnairetaches;
}