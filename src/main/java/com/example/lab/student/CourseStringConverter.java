package com.example.lab.student;

import javafx.util.StringConverter;

public class CourseStringConverter extends StringConverter<OnlineCourse> {
    @Override
    public String toString(OnlineCourse onlineCourse) {
        if (onlineCourse == null) return "";
        return onlineCourse.getTitle();
    }

    @Override
    public OnlineCourse fromString(String s) {
        return null;
    }
}
