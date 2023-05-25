module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires json.simple;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example to javafx.fxml;
    exports com.example;

    opens com.example.UIController to javafx.fxml;
    exports com.example.UIController;
}
