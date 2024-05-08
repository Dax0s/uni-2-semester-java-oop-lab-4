package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentViewController {
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentSurname;

    @FXML
    private TableView<Student> studentTableView = new TableView<>();

    private final Singleton singleton = Singleton.getInstance();
    private List<Student> students;

    @FXML
    private void initialize() {
        students = singleton.getStudents();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        studentTableView.getColumns().clear();
        studentTableView.getColumns().add(nameColumn);
        studentTableView.getColumns().add(surnameColumn);

        studentTableView.getItems().clear();
        for (Student student : students) {
            studentTableView.getItems().add(student);
        }

    }

    @FXML
    protected void onAddStudentButtonClick() {
        Student student = new Student(studentName.getText(), studentSurname.getText());

        studentTableView.getItems().add(student);
        students.add(student);
    }
}