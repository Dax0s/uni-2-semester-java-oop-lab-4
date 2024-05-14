package com.example.lab.student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Course {
    protected final UUID id;
    protected String title;
    protected final List<Student> students;

    protected Course(UUID id, String title) {
        this.id = id;
        this.title = title;
        this.students = new ArrayList<>();
    }


    public abstract void addStudent(Student student);

    public abstract void removeStudent(Student student);
}
