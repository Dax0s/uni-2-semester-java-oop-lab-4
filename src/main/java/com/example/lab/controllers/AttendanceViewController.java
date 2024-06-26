package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.*;
import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceViewController {
    @FXML
    ChoiceBox<OnlineCourse> courseChoiceBox;
    @FXML
    ChoiceBox<Student> studentChoiceBox;

    @FXML
    DatePicker datePicker;

    @FXML
    DatePicker datePickerFrom;
    @FXML
    DatePicker datePickerTo;

    @FXML
    TableView<StudentAttendanceDayDto> attendanceTableView;

    private final Singleton singleton = Singleton.getInstance();

    private List<OnlineCourse> cours;
    private List<Student> students;

    private ObservableList<OnlineCourse> onlineCourseChoicesList;
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
        cours = singleton.getCourses();
        students = singleton.getStudents();

        onlineCourseChoicesList = FXCollections.observableArrayList(cours);
        courseChoiceBox.setItems(onlineCourseChoicesList);
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
    }

    public AttendanceViewController updateCourseChoiceBox() {
        onlineCourseChoicesList = FXCollections.observableArrayList(cours.stream().filter(course -> !course.getSchedule().isEmpty()).toList());
        courseChoiceBox.setItems(onlineCourseChoicesList);

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

    @FXML
    protected void onFilterButtonClick() {
        if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null || datePickerFrom.getValue().isAfter(datePickerTo.getValue())) return;

        TableColumn<StudentAttendanceDayDto, String> fullNameColumn = new TableColumn<>("Full name");
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<StudentAttendanceDayDto, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        TableColumn<StudentAttendanceDayDto, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        attendanceTableView.getColumns().clear();
        attendanceTableView.getColumns().add(fullNameColumn);
        attendanceTableView.getColumns().add(courseColumn);
        attendanceTableView.getColumns().add(dateColumn);

        attendanceTableView.getItems().clear();
        for (Student student : students) {
            for (String key : student.getAttendance().keySet()) {
                for (LocalDate date : student.getAttendance().get(key)) {
                    if ((date.isAfter(datePickerFrom.getValue()) || date.isEqual(datePickerFrom.getValue()))
                            && (date.isBefore(datePickerTo.getValue()) || date.isEqual(datePickerTo.getValue()))) {
                        attendanceTableView.getItems().add(new StudentAttendanceDayDto(student.getName(), student.getSurname(), key, date.toString()));
                    }
                }
            }
        }
    }

    @FXML
    protected void onSaveToPdfButtonClick() {
        if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null || datePickerFrom.getValue().isAfter(datePickerTo.getValue())) return;

        try {
            FileUtils.saveStudentsToPdf(students, datePickerFrom.getValue(), datePickerTo.getValue());
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
