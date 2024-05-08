package com.example.lab.student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course {
    private final UUID id;
    private String title;
    private final List<Student> students;

    public Course(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        students = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.addCourse(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.removeCourse(this);
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int getStudentCount() {
        return students.size();
    }
}
