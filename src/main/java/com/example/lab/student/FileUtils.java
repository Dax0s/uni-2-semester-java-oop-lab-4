package com.example.lab.student;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    public static void saveStudentsToPdf(List<Student> students, LocalDate from, LocalDate to) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("data.pdf"));

        document.open();

        PdfPTable table = new PdfPTable(3);

        Stream.of("Full name", "Course", "Date")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle));
                table.addCell(header);
            });

        for (Student student : students) {
            for (String key : student.getAttendance().keySet()) {
                for (LocalDate date : student.getAttendance().get(key)) {
                    if ((date.isAfter(from) || date.isEqual(from))
                            && (date.isBefore(to) || date.isEqual(to))) {
                        table.addCell(student.getName() + " " + student.getSurname());
                        table.addCell(key);
                        table.addCell(date.toString());
                    }
                }
            }
        }

        document.add(table);
        document.close();
    }

    public void saveStudentsToFile(List<Student> students, String filename) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(filename));

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"id", "name", "surname", "courses", "attendance"});

        for (ExportableToCsv element : students) {
            data.add(element.toCsvStringArray());
        }

        writer.writeAll(data);
        writer.close();
    }
}
