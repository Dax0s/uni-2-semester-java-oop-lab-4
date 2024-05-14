package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.Course;
import com.example.lab.student.CourseStringConverter;
import com.example.lab.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AttendanceViewController {
    @FXML
    ChoiceBox<Course> courseChoiceBox;
    @FXML
    ChoiceBox<Student> studentChoiceBox;

    @FXML
    DatePicker datePicker;

    private final Singleton singleton = Singleton.getInstance();

    private List<Course> courses;
    private List<Student> students;

    private ObservableList<Course> courseChoicesList;
    private ObservableList<Student> studentChoicesList;

    private final DateCell emptyDateCell = new DateCell() {
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            setDisable(true);

            setStyle("-fx-background-color: #ffffff;");
        }
    };

    @FXML
    private void initialize() {
        courses = singleton.getCourses();
        students = singleton.getStudents();

        courseChoicesList = FXCollections.observableArrayList(courses);
        courseChoiceBox.setItems(courseChoicesList);
        courseChoiceBox.setConverter(new CourseStringConverter());

        studentChoiceBox.setDisable(false);
        datePicker.setDayCellFactory(param -> emptyDateCell);

        courseChoiceBox.setOnAction(actionEvent -> {
            if (courseChoiceBox.getValue() == null) {
                studentChoiceBox.setDisable(true);
                datePicker.setDayCellFactory(param -> emptyDateCell);
                return;
            }

            studentChoiceBox.setDisable(false);
            datePicker.setEditable(true);

            studentChoicesList = FXCollections.observableArrayList(courseChoiceBox.getValue().getStudents());
            studentChoiceBox.setItems(FXCollections.observableArrayList(studentChoicesList));

            datePicker.setDayCellFactory(param -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(true);

                    if (studentChoiceBox.getValue() != null
                            && studentChoiceBox.getValue().getAttendance().containsKey(courseChoiceBox.getValue().getTitle())
                            && studentChoiceBox.getValue().getAttendance().get(courseChoiceBox.getValue().getTitle()).contains(date))
                        setStyle("-fx-background-color: #ea798d;");

                    if (courseChoiceBox.getValue().getSchedule().contains(date.getDayOfWeek().toString().toLowerCase()))
                        setDisable(false);
                }
            });
        });

//        studentChoicesList = FXCollections.observableArrayList(students);
//        studentChoiceBox.setItems(studentChoicesList);
//        studentChoiceBox.setConverter(studentStringConverter);
//
//        removeCourseChoiceBox.setItems(courseChoicesList);
//        removeCourseChoiceBox.setConverter(courseStringConverter);
//
//        removeCourseChoiceBox.setOnAction(actionEvent -> {
//            if (removeCourseChoiceBox.getValue() == null) return;
//
//            removeStudentChoiceBox.setItems(FXCollections.observableArrayList(removeCourseChoiceBox.getValue().getStudents()));
//        });
    }

    public AttendanceViewController updateCourseChoiceBox() {
        courseChoicesList = FXCollections.observableArrayList(courses.stream().filter(course -> !course.getSchedule().isEmpty()).toList());
        courseChoiceBox.setItems(courseChoicesList);

        return this;
    }

    public AttendanceViewController updateStudentChoiceBox() {
        studentChoicesList = FXCollections.observableArrayList(students);
        studentChoiceBox.setItems(studentChoicesList);

        return this;
    }

    @FXML
    protected void onAttendedButtonClick() {
        if (courseChoiceBox.getValue() == null
                || studentChoiceBox.getValue() == null
                || datePicker.getValue() == null
                || !studentChoiceBox.getValue().getAttendance().containsKey(courseChoiceBox.getValue().getTitle()))
            return;

        studentChoiceBox.getValue().getAttendance().get(courseChoiceBox.getValue().getTitle()).remove(datePicker.getValue());
    }

    @FXML
    protected void onMissedButtonClick() {
        if (courseChoiceBox.getValue() == null || studentChoiceBox.getValue() == null || datePicker.getValue() == null)
            return;

        if (studentChoiceBox.getValue().getAttendance().containsKey(courseChoiceBox.getValue().getTitle()))
            studentChoiceBox.getValue().getAttendance().get(courseChoiceBox.getValue().getTitle()).add(datePicker.getValue());
        else
            studentChoiceBox.getValue().getAttendance().put(courseChoiceBox.getValue().getTitle(), new ArrayList<>(Collections.singletonList(datePicker.getValue())));
    }
}
