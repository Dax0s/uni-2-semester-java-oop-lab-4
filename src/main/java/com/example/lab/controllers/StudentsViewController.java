package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.FileUtils;
import com.example.lab.student.Student;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.util.List;

public class StudentsViewController {
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
        nameColumn.setEditable(true);

        TableColumn<Student, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setOnEditCommit(
                e -> {
                    (e.getTableView().getItems().get(
                            e.getTablePosition().getRow())
                    ).setName(e.getNewValue());
                }
        );
        surnameColumn.setOnEditCommit(
                e -> (e.getTableView().getItems().get(
                        e.getTablePosition().getRow())
                ).setSurname(e.getNewValue())
        );

        nameColumn.setEditable(true);
        surnameColumn.setEditable(true);

        studentTableView.getColumns().add(nameColumn);
        studentTableView.getColumns().add(surnameColumn);
    }

    public void updateTable() {
        studentTableView.getItems().clear();
        for (Student student : students) {
            studentTableView.getItems().add(student);
        }
    }

    @FXML
    protected void onAddStudentButtonClick() {
        Student student = new Student(studentName.getText(), studentSurname.getText());

        studentName.setText("");
        studentSurname.setText("");

        studentTableView.getItems().add(student);
        students.add(student);
    }

    @FXML
    protected void onEditingModeButtonClick() {
        studentTableView.setEditable(!studentTableView.isEditable());
    }

    @FXML
    protected void onSaveButtonClick() {
        try {
            FileUtils fileUtils = new FileUtils();
            System.out.println(students.get(0).toString());
            fileUtils.saveStudentsToFile(students, singleton.getCsvFilename());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}