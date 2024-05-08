package com.example.lab;

import com.example.lab.student.Course;
import com.example.lab.student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Singleton {
    private static Optional<Singleton> instance;

    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();

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
}
