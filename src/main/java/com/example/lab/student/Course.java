package com.example.lab.student;

import java.util.*;

public class Course {
    private final UUID id;
    private String title;
    private final List<Student> students;

    private final Map<String, Boolean> schedule;

    public Course(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        students = new ArrayList<>();

        schedule = new HashMap<>();
        schedule.put("monday", false);
        schedule.put("tuesday", false);
        schedule.put("wednesday", false);
        schedule.put("thursday", false);
        schedule.put("friday", false);
    }

    public UUID getId() {
        return id;
    }

    public void addStudent(Student student) {
        if (students.contains(student)) return;

        students.add(student);
        student.addCourse(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.removeCourse(this);
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int getStudentCount() {
        return students.size();
    }

    public Map<String, Boolean> getSchedule() {
        return schedule;
    }
}
