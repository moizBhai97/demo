module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires json.simple;
    requires com.microsoft.sqlserver.jdbc;
    requires javafx.media;
    requires org.controlsfx.controls;

    opens com.example to javafx.fxml;

    exports com.example;

    opens com.example.UIController to javafx.fxml;

    exports com.example.UIController;

}
