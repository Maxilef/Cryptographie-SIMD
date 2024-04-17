module com.example.file_rouge {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    exports com.example.file_rouge.API;
    opens com.example.file_rouge.API to org.hibernate.orm.core;
    exports com.example.file_rouge.API.model;
    opens com.example.file_rouge.API.model to org.hibernate.orm.core;
}