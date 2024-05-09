package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.Course;
import com.example.lab.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class CoursesViewController {
    @FXML
    private TextField courseName;

    @FXML
    private TableView<Course> courseTableView = new TableView<>();

    @FXML
    private ChoiceBox<Course> courseChoiceBox;
    @FXML
    private ChoiceBox<Student> studentChoiceBox;

    @FXML
    private ChoiceBox<Course> removeCourseChoiceBox;
    @FXML
    private ChoiceBox<Student> removeStudentChoiceBox;

    @FXML
    private ChoiceBox<Course> scheduleCourseChoiceBox;
    @FXML
    private ToggleButton monday;
    @FXML
    private ToggleButton tuesday;
    @FXML
    private ToggleButton wednesday;
    @FXML
    private ToggleButton thursday;
    @FXML
    private ToggleButton friday;

    private List<ToggleButton> scheduleButtons;

    private ObservableList<Course> courseChoicesList;
    private ObservableList<Student> studentChoicesList;
    private ObservableList<Student> removeStudentChoicesList;

    private final Singleton singleton = Singleton.getInstance();
    private List<Course> courses;
    private List<Student> students;

    @FXML
    private void initialize() {
        courses = singleton.getCourses();
        students = singleton.getStudents();

        StringConverter<Course> courseStringConverter = new StringConverter<>() {
            @Override
            public String toString(Course course) {
                if (course == null) return "";
                return course.getTitle();
            }

            @Override
            public Course fromString(String s) {
                return null;
            }
        };

        StringConverter<Student> studentStringConverter = new StringConverter<>() {
            @Override
            public String toString(Student student) {
                if (student == null) return "";
                return student.toString();
            }

            @Override
            public Student fromString(String s) {
                return null;
            }
        };

        courseChoicesList = FXCollections.observableArrayList(courses);
        courseChoiceBox.setItems(courseChoicesList);
        courseChoiceBox.setConverter(courseStringConverter);

        courseChoiceBox.setOnAction(actionEvent -> {
            if (courseChoiceBox.getValue() == null) return;

            studentChoicesList = FXCollections.observableArrayList(students);
            studentChoicesList.removeAll(courseChoiceBox.getValue().getStudents());
            studentChoiceBox.setItems(FXCollections.observableArrayList(studentChoicesList));
        });

        studentChoicesList = FXCollections.observableArrayList(students);
        studentChoiceBox.setItems(studentChoicesList);
        studentChoiceBox.setConverter(studentStringConverter);

        removeCourseChoiceBox.setItems(courseChoicesList);
        removeCourseChoiceBox.setConverter(courseStringConverter);

        removeCourseChoiceBox.setOnAction(actionEvent -> {
            if (removeCourseChoiceBox.getValue() == null) return;

            removeStudentChoiceBox.setItems(FXCollections.observableArrayList(removeCourseChoiceBox.getValue().getStudents()));
        });

        scheduleButtons = new ArrayList<>();
        scheduleButtons.add(monday);
        scheduleButtons.add(tuesday);
        scheduleButtons.add(wednesday);
        scheduleButtons.add(thursday);
        scheduleButtons.add(friday);

        disableScheduleButtons();

        scheduleCourseChoiceBox.setItems(courseChoicesList);
        scheduleCourseChoiceBox.setConverter(courseStringConverter);

        scheduleCourseChoiceBox.setOnAction(actionEvent -> {
            if (scheduleCourseChoiceBox.getValue() == null) return;

            enableScheduleButtons();

            monday.setSelected(scheduleCourseChoiceBox.getValue().getSchedule().get("monday"));
            tuesday.setSelected(scheduleCourseChoiceBox.getValue().getSchedule().get("tuesday"));
            wednesday.setSelected(scheduleCourseChoiceBox.getValue().getSchedule().get("wednesday"));
            thursday.setSelected(scheduleCourseChoiceBox.getValue().getSchedule().get("thursday"));
            friday.setSelected(scheduleCourseChoiceBox.getValue().getSchedule().get("friday"));
        });

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

    public void updateStudentChoiceBox() {
        studentChoicesList = FXCollections.observableArrayList(students);
        studentChoiceBox.setItems(studentChoicesList);
    }

    public void updateCourses() {
        courseTableView.getItems().clear();
        for (Course course : courses) {
            courseTableView.getItems().add(course);
        }

        courseChoicesList = FXCollections.observableArrayList(courses);
        courseChoiceBox.setItems(courseChoicesList);
        removeCourseChoiceBox.setItems(courseChoicesList);
        scheduleCourseChoiceBox.setItems(courseChoicesList);
    }

    @FXML
    protected void onCreateCourseButtonClick() {
        Course course = new Course(courseName.getText());

        courseName.setText("");

        courseTableView.getItems().add(course);
        courses.add(course);
        courseChoicesList.add(course);
    }

    @FXML
    protected void onAddToCourseButtonClick() {
        if (courseChoiceBox.getValue() == null || studentChoiceBox.getValue() == null) return;

        courseChoiceBox.getValue().addStudent(studentChoiceBox.getValue());
        courseTableView.refresh();

        if (removeCourseChoiceBox.getValue() != null)
            removeStudentChoiceBox.setItems(FXCollections.observableArrayList(removeCourseChoiceBox.getValue().getStudents()));

        if (courseChoiceBox.getValue() != null) {
            studentChoicesList.removeAll(courseChoiceBox.getValue().getStudents());
            studentChoiceBox.setItems(FXCollections.observableArrayList(studentChoicesList));
        }
    }

    @FXML
    protected void onRemoveFromCourseClick() {
        if (removeCourseChoiceBox.getValue() == null || removeStudentChoiceBox.getValue() == null) return;

        removeCourseChoiceBox.getValue().removeStudent(removeStudentChoiceBox.getValue());
        removeStudentChoiceBox.setItems(FXCollections.observableArrayList(removeCourseChoiceBox.getValue().getStudents()));

        courseTableView.refresh();

        if (courseChoiceBox.getValue() != null) {
            studentChoicesList = FXCollections.observableArrayList(students);
            studentChoicesList.removeAll(courseChoiceBox.getValue().getStudents());
            studentChoiceBox.setItems(FXCollections.observableArrayList(studentChoicesList));
        }
    }

    @FXML
    protected void onScheduleButtonClick(ActionEvent event) {
        if (scheduleCourseChoiceBox.getValue() == null) return;

        ToggleButton button = ((ToggleButton) event.getTarget());

        scheduleCourseChoiceBox.getValue().getSchedule().replace(button.getId(), button.isSelected());
    }

    private void disableScheduleButtons() {
        for (ToggleButton button: scheduleButtons) {
            button.setDisable(true);
        }
    }

    private void enableScheduleButtons() {
        for (ToggleButton button: scheduleButtons) {
            button.setDisable(false);
        }
    }
}
