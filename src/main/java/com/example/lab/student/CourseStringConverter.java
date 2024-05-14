package com.example.lab.student;

import javafx.util.StringConverter;

public class CourseStringConverter extends StringConverter<Course> {
    @Override
    public String toString(Course course) {
        if (course == null) return "";
        return course.getTitle();
    }

    @Override
    public Course fromString(String s) {
        return null;
    }
}
