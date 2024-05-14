package com.example.lab.student;

import java.util.*;

public class OnlineCourse extends Course {
    private final Set<String> schedule;

    public OnlineCourse(String title) {
        super(UUID.randomUUID(), title);

        schedule = new HashSet<>();
    }

    public OnlineCourse(String title, String schedule) {
        this(title);

        if (schedule.isEmpty()) return;

        this.schedule.addAll(Arrays.asList(schedule.split("\\.")));
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void addStudent(Student student) {
        if (students.contains(student)) return;

        students.add(student);
        student.addCourse(this);
    }

    @Override
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
