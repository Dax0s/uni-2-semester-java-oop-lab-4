package com.example.lab.controllers;

import com.example.lab.Singleton;
import com.example.lab.student.Course;
import com.example.lab.student.Student;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class FileViewController {
    @FXML
    private TextField csvFileName;

    @FXML
    protected void onUseFileButtonClick() {
        if (csvFileName.getText() == null) return;

        Singleton.getInstance().setCsvFilename(csvFileName.getText());
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFileName.getText()));

            List<String[]> data = reader.readAll();

            String[] firstRow = data.get(0);
            if (!Objects.equals(firstRow[0], "id") || !Objects.equals(firstRow[1], "name") || !Objects.equals(firstRow[2], "surname") || !Objects.equals(firstRow[3], "courses")) {
                System.out.println("Bad input file");
                return;
            }

            List<Course> courses = Singleton.getInstance().getCourses();
            courses.clear();

            for (int i = 1; i < data.size(); i++) {
                String[] coursesString = data.get(i)[3].split(";");

                for (String courseTitle : coursesString) {
                    if (courses.stream().filter(course -> Objects.equals(course.getTitle(), courseTitle)).findFirst().isEmpty())
                        courses.add(new Course(courseTitle));
                }
            }


            List<Student> students = Singleton.getInstance().getStudents();
            students.clear();

            for (int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);
                Student student = new Student(UUID.fromString(row[0]), row[1], row[2]);
                students.add(student);

                String[] coursesString = data.get(i)[3].split(";");

                for (String courseTitle : coursesString) {
                    Optional<Course> currentCourse = courses.stream().filter(course -> Objects.equals(course.getTitle(), courseTitle)).findFirst();
                    currentCourse.ifPresent(course -> course.addStudent(student));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("An error has occurred while reading file");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File is missing columns");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("File is empty");
        }
    }
}
