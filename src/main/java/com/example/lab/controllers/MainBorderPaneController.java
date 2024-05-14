package com.example.lab.controllers;

import com.example.lab.Singleton;
import javafx.fxml.FXML;

public class MainBorderPaneController {
    private Singleton singleton = Singleton.getInstance();

    @FXML
    protected void onStudentsButtonClick() {
        singleton.getStudentsViewController().updateStudents();
        singleton.getMainBorderPane().setCenter(singleton.getStudentsAnchorPane());
    }
    @FXML
    protected void onCoursesButtonClick() {
        singleton.getCoursesViewController()
                .updateStudentChoiceBox()
                .updateCourses()
                .resetScheduleButtons();
        singleton.getMainBorderPane().setCenter(singleton.getCoursesAnchorPane());
    }

    @FXML
    protected void onFileManagerButtonClick() {
        singleton.getMainBorderPane().setCenter(singleton.getFileViewAnchorPane());
    }

    @FXML
    protected void onAttendanceButtonClick() {
        singleton.getAttendanceViewController()
                .updateCourseChoiceBox()
                .updateStudentChoiceBox();
        singleton.getMainBorderPane().setCenter(singleton.getAttendanceAnchorPane());
    }
}
