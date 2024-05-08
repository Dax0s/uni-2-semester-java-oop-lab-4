package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.Course;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CoursesViewController {
    @FXML
    private TextField courseName;

    @FXML
    private TableView<Course> courseTableView = new TableView<>();

    private final Singleton singleton = Singleton.getInstance();
    private List<Course> courses;

    @FXML
    private void initialize() {
        courses = singleton.getCourses();

        TableColumn<Course, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Course, String> amountOfStudentsColumn = new TableColumn<>("Amount of students");
        amountOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("studentCount"));

        courseTableView.getColumns().add(titleColumn);
        courseTableView.getColumns().add(amountOfStudentsColumn);

        for (Course course : courses) {
            courseTableView.getItems().add(course);
        }

    }

    @FXML
    protected void onCreateCourseButtonClick() {
        Course course = new Course(courseName.getText());

        courseName.setText("");

        courseTableView.getItems().add(course);
        courses.add(course);
    }
}
