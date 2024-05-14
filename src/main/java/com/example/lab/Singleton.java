package com.example.lab;

import com.example.lab.controllers.AttendanceViewController;
import com.example.lab.controllers.CoursesViewController;
import com.example.lab.controllers.StudentsViewController;
import com.example.lab.student.OnlineCourse;
import com.example.lab.student.Student;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Singleton {
    private static Optional<Singleton> instance = Optional.empty();

    private final List<Student> students = new ArrayList<>();
    private final List<OnlineCourse> courses = new ArrayList<>();

    private BorderPane mainBorderPane;
    private AnchorPane studentsAnchorPane;
    private AnchorPane coursesAnchorPane;
    private AnchorPane fileViewAnchorPane;

    private AnchorPane attendanceAnchorPane;

    private CoursesViewController coursesViewController;
    private StudentsViewController studentsViewController;
    private AttendanceViewController attendanceViewController;

    private CurrentTab currentTab = CurrentTab.STUDENTS;

    private String csvFilename;

    public enum CurrentTab {
        STUDENTS,
        COURSES
    }

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance.isEmpty()) {
            instance = Optional.of(new Singleton());
        }

        return instance.get();
    }

    public List<OnlineCourse> getCourses() {
        return courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public AnchorPane getStudentsAnchorPane() {
        return studentsAnchorPane;
    }

    public void setStudentsAnchoPane(AnchorPane studentsAnchorPane) {
        this.studentsAnchorPane = studentsAnchorPane;
    }

    public CurrentTab getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(CurrentTab currentTab) {
        this.currentTab = currentTab;
    }

    public AnchorPane getCoursesAnchorPane() {
        return coursesAnchorPane;
    }

    public void setCoursesAnchorPane(AnchorPane coursesAnchorPane) {
        this.coursesAnchorPane = coursesAnchorPane;
    }

    public CoursesViewController getCoursesViewController() {
        return coursesViewController;
    }

    public void setCoursesViewController(CoursesViewController coursesViewController) {
        this.coursesViewController = coursesViewController;
    }

    public AnchorPane getFileViewAnchorPane() {
        return fileViewAnchorPane;
    }

    public void setFileViewAnchorPane(AnchorPane fileViewAnchorPane) {
        this.fileViewAnchorPane = fileViewAnchorPane;
    }

    public String getCsvFilename() {
        return csvFilename;
    }

    public void setCsvFilename(String csvFilename) {
        this.csvFilename = csvFilename;
    }

    public StudentsViewController getStudentsViewController() {
        return studentsViewController;
    }

    public void setStudentsViewController(StudentsViewController studentsViewController) {
        this.studentsViewController = studentsViewController;
    }

    public AnchorPane getAttendanceAnchorPane() {
        return attendanceAnchorPane;
    }

    public void setAttendanceAnchorPane(AnchorPane attendanceAnchorPane) {
        this.attendanceAnchorPane = attendanceAnchorPane;
    }

    public AttendanceViewController getAttendanceViewController() {
        return attendanceViewController;
    }

    public void setAttendanceViewController(AttendanceViewController attendanceViewController) {
        this.attendanceViewController = attendanceViewController;
    }
}
