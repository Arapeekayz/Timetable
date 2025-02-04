package com.app.timetable.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exams")
public class Exam {

    // Getters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String time;
    private String courseCode;
    private String courseNarration;
    private String duration;
    private String numOfStudents;
    private String venue;
    private String examiner;


    public Exam() {
        // Default constructor
    }

    public Exam(String date, String time, String courseCode, String courseNarration, String duration, String numOfStudents, String venue, String examiner) {

        this.date = date;
        this.time = time;
        this.courseCode = courseCode;
        this.courseNarration = courseNarration;
        this.duration = duration;
        this.numOfStudents = numOfStudents;
        this.venue = venue;
        this.examiner = examiner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public String getCourseNarration() {
        return courseNarration;
    }
    public void setCourseNarration(String courseNarration) {
        this.courseNarration = courseNarration;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getNumOfStudents() {
        return numOfStudents;
    }
    public void setNumOfStudents(String numOfStudents) {
        this.numOfStudents = numOfStudents;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public String getExaminer() {
        return examiner;
    }
    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

}

