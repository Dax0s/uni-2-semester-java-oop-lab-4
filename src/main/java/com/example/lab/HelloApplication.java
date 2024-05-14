package com.example.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Locale;

public class HelloApplication extends Application {
    public static Locale adjustWeekStart(Locale locale, DayOfWeek day) {
        String dayString = day.toString().substring(0, 3);

        return new Locale.Builder()
                .setLocale(locale)
                .setExtension(Locale.UNICODE_LOCALE_EXTENSION, "fw-" + dayString)
                .build();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(adjustWeekStart(Locale.getDefault(), DayOfWeek.MONDAY));

        FXMLLoader borderPaneLoader = new FXMLLoader(HelloApplication.class.getResource("main-border-pane.fxml"));
        BorderPane pane = borderPaneLoader.load();

        FXMLLoader studentAnchorPaneLoader = new FXMLLoader(HelloApplication.class.getResource("students-view.fxml"));
        AnchorPane studentsAnchorPane = studentAnchorPaneLoader.load();

        FXMLLoader coursesAnchorPaneLoader = new FXMLLoader(HelloApplication.class.getResource("courses-view.fxml"));
        AnchorPane coursesAnchorPane = coursesAnchorPaneLoader.load();

        FXMLLoader fileViewAnchorPaneLoader = new FXMLLoader(HelloApplication.class.getResource("file-view.fxml"));
        AnchorPane fileViewAnchorPane = fileViewAnchorPaneLoader.load();

        FXMLLoader attendanceAnchorPaneLoader = new FXMLLoader(HelloApplication.class.getResource("attendance-view.fxml"));
        AnchorPane attendanceAnchorPane = attendanceAnchorPaneLoader.load();

        Singleton.getInstance().setMainBorderPane(pane);
        Singleton.getInstance().setStudentsAnchoPane(studentsAnchorPane);
        Singleton.getInstance().setCoursesAnchorPane(coursesAnchorPane);
        Singleton.getInstance().setFileViewAnchorPane(fileViewAnchorPane);
        Singleton.getInstance().setAttendanceAnchorPane(attendanceAnchorPane);

        Singleton.getInstance().setCoursesViewController(coursesAnchorPaneLoader.getController());
        Singleton.getInstance().setStudentsViewController(studentAnchorPaneLoader.getController());
        Singleton.getInstance().setAttendanceViewController(attendanceAnchorPaneLoader.getController());

        pane.setCenter(fileViewAnchorPane);

        Scene scene = new Scene(pane, 1200, 600);
        stage.setTitle("Lab 4");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}