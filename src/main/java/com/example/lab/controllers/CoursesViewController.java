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

        TableColumn<Course, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> surnameColumn = new TableColumn<>("Amount of students");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("studentCount"));

        courseTableView.getColumns().clear();
        courseTableView.getColumns().add(nameColumn);
        courseTableView.getColumns().add(surnameColumn);

        courseTableView.getItems().clear();
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
