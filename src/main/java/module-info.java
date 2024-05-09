module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires opencsv;
    requires java.sql;

    opens com.example.lab to javafx.fxml;
    exports com.example.lab;
    opens com.example.lab.controllers to javafx.fxml;
    exports com.example.lab.controllers;
    opens com.example.lab.student to javafx.fxml;
    exports com.example.lab.student;
}