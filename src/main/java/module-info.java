module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example.UIController to javafx.fxml;
    exports com.example;

    opens com.example.UIController to javafx.fxml;
    exports com.example.UIController;
}
