module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.lab to javafx.fxml;
    exports com.example.lab;
}