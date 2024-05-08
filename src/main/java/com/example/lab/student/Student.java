package com.example.lab.student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Student {
    private final UUID id;
    private String name;
    private String surname;
    private final List<Course> courses;

    public Student(String name, String surname) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
