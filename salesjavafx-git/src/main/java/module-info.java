module com.example.salesjavafxgit {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
   // requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;



    opens com.example.model to javafx.fxml;
    exports com.example.model;


    opens com.example.salesjavafxgit to javafx.fxml;
    exports com.example.salesjavafxgit;
}