package com.example.lab.student;

import java.util.*;

public class Course {
    private final UUID id;
    private String title;
    private final List<Student> students;

    private final Set<String> schedule;

    public Course(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        students = new ArrayList<>();

        schedule = new HashSet<>();
//        schedule.add("monday");
//        schedule.add("tuesday");
//        schedule.add("wednesday");
//        schedule.add("thursday");
//        schedule.add("friday");
    }

    public Course(String title, String schedule) {
        this(title);

        if (schedule.isEmpty()) return;

        this.schedule.addAll(Arrays.asList(schedule.split("\\.")));
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

    public Set<String> getSchedule() {
        return schedule;
    }
}
