package com.example.lab.student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Course {
    private final UUID id;
    private String name;
    private final List<Student> students;

    public Course(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
