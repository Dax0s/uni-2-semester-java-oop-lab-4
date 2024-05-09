package com.example.lab.controllers;

import com.example.lab.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainBorderPaneController {
    private Singleton singleton = Singleton.getInstance();

    @FXML
    protected void onStudentsButtonClick() {
        singleton.getStudentsViewController().updateTable();
        singleton.getMainBorderPane().setCenter(singleton.getStudentsAnchorPane());
    }
    @FXML
    protected void onCoursesButtonClick() {
        singleton.getCoursesViewController().updateStudentChoiceBox();
        singleton.getMainBorderPane().setCenter(singleton.getCoursesAnchorPane());
    }

    @FXML
    protected void onFileManagerButtonClick() {
        singleton.getMainBorderPane().setCenter(singleton.getFileViewAnchorPane());
    }
}
