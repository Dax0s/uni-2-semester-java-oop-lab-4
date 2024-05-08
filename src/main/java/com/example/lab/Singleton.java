package com.example.lab;

import com.example.lab.student.Course;
import com.example.lab.student.Student;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Singleton {
    private static Optional<Singleton> instance = Optional.empty();

    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();

    private BorderPane mainBorderPane;
    private AnchorPane studentsAnchorPane;
    private AnchorPane coursesAnchorPane;

    private CurrentTab currentTab = CurrentTab.STUDENTS;

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

    public List<Course> getCourses() {
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
}
