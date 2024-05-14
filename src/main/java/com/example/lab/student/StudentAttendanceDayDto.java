package com.example.lab.student;

public class StudentAttendanceDayDto {
    private final String name;
    private final String surname;
    private final String course;
    private final String day;

    public StudentAttendanceDayDto(String name, String surname, String course, String day) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.day = day;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public String getDay() {
        return day;
    }

    public String getCourse() {
        return course;
    }
}
