package com.example.lab.student;

import java.time.LocalDate;
import java.util.*;

public class Student implements ExportableToCsv {
    private final UUID id;
    private String name;
    private String surname;
    private final List<OnlineCourse> courses;
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

    public void addCourse(OnlineCourse onlineCourse) {
        this.courses.add(onlineCourse);
    }

    public void removeCourse(OnlineCourse onlineCourse) {
        this.courses.remove(onlineCourse);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public String getCoursesString() {
        if (courses == null || courses.isEmpty()) return "";
        StringBuilder courses = new StringBuilder();

        for (OnlineCourse onlineCourse : this.courses) {
            courses.append(onlineCourse.getTitle()).append(", ");
        }
        courses.deleteCharAt(courses.lastIndexOf(","));

        return courses.toString();
    }

    @Override
    public String[] toCsvStringArray() {
        StringBuilder courses = new StringBuilder();
        if (!this.courses.isEmpty()) {
            for (OnlineCourse onlineCourse : this.courses) {
                courses.append(onlineCourse.getTitle());
                if (!onlineCourse.getSchedule().isEmpty())
                    courses.append(":");

                for (String key : onlineCourse.getSchedule()) {
                    courses.append(key).append(".");
                }

                if (!onlineCourse.getSchedule().isEmpty())
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

    public List<OnlineCourse> getCourses() {
        return courses;
    }

    public Map<String, List<LocalDate>> getAttendance() {
        return attendance;
    }
}
