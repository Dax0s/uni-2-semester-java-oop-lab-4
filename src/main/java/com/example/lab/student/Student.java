package com.example.lab.student;

import com.dlsc.formsfx.model.structure.Group;

import java.time.LocalDate;
import java.util.*;

public class Student {
    private final UUID id;
    private String name;
    private String surname;
    private final List<Course> courses;
    private final Map<String, List<LocalDate>> attendance;

    public Student(UUID id, String name, String surname, String attendance) {
        this(id, name, surname);
        addAttendanceData(attendance);
    }

    // MPS:data.data;Filosofija:data
    private void addAttendanceData(String attendance) {
        for (String course : attendance.split(";")) {
            if (attendance.isBlank()) continue;
            String title = course.split(":")[0];
            String[] dates = course.split(":")[1].split("\\.");

            if (!this.attendance.containsKey(title)) this.attendance.put(title, new ArrayList<>());

            for (String date : dates) {
                if (!date.isBlank())
                    this.attendance.get(title).add(LocalDate.parse(date));
            }
        }
    }

    public Student(UUID id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.courses = new ArrayList<>();
        this.attendance = new HashMap<>();
    }

    public Student(String name, String surname) {
        this(UUID.randomUUID(), name, surname);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public String getCoursesString() {
        if (courses == null || courses.isEmpty()) return "";
        StringBuilder courses = new StringBuilder();

        for (Course course : this.courses) {
            courses.append(course.getTitle()).append(", ");
        }
        courses.deleteCharAt(courses.lastIndexOf(","));

        return courses.toString();
    }

    public String[] toCsvStringArray() {
        StringBuilder courses = new StringBuilder();
        if (!this.courses.isEmpty()) {
            for (Course course : this.courses) {
                courses.append(course.getTitle());
                if (!course.getSchedule().isEmpty())
                    courses.append(":");

                for (String key : course.getSchedule()) {
                    courses.append(key).append(".");
                }

                if (!course.getSchedule().isEmpty())
                    courses.deleteCharAt(courses.lastIndexOf("."));

                courses.append(";");
            }
            courses.deleteCharAt(courses.lastIndexOf(";"));
        }

        // MPS:data.data;Filosofija:data
        StringBuilder attendance = new StringBuilder();
        if (!this.attendance.isEmpty()) {
            for (String key : this.attendance.keySet()) {
                if (key.isBlank()) continue;
                attendance.append(key);
                if (!this.attendance.get(key).isEmpty())
                    attendance.append(":");

                for (LocalDate date : this.attendance.get(key)) {
                        attendance.append(date).append(".");
                }

                if (!this.attendance.get(key).isEmpty())
                    attendance.deleteCharAt(attendance.lastIndexOf("."));

                attendance.append(";");
            }
            attendance.deleteCharAt(attendance.lastIndexOf(";"));
        }


        return new String[]{id.toString(), name, surname, courses.toString(), attendance.toString()};
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

    public Map<String, List<LocalDate>> getAttendance() {
        return attendance;
    }
}
