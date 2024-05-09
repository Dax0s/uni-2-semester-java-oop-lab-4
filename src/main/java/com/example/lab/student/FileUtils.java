package com.example.lab.student;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

//    public void test() throws IOException {
//        CSVReader reader = new CSVReader(new FileReader("test.csv"));
//
//
//    }

    public void saveStudentsToFile(List<Student> students, String filename) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filename));

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"id", "name", "surname", "courses"});

        for (Student student : students) {
            data.add(student.toCsvStringArray());
        }

        writer.writeAll(data);
        writer.close();
    }
}
