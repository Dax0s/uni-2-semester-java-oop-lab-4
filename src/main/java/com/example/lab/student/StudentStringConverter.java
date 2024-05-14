package com.example.lab.student;

import javafx.util.StringConverter;

public class StudentStringConverter extends StringConverter<Student> {
    @Override
    public String toString(Student student) {
        if (student == null) return "";
        return student.toString();
    }

    @Override
    public Student fromString(String s) {
        return null;
    }
}
