package com.example.lab.controllers;

import com.example.lab.Singleton;
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
            if (!Objects.equals(firstRow[0], "id") || !Objects.equals(firstRow[1], "name") || !Objects.equals(firstRow[2], "surname")) {
                System.out.println("Bad input file");
                return;
            }

            List<Student> students = Singleton.getInstance().getStudents();
            students.clear();

            for (int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);
                students.add(new Student(UUID.fromString(row[0]), row[1], row[2]));
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
