package com.app.timetable.Entity;


public class TimetableEntity {

    private String date;
    private String time;
    private String course;
    private String courseNarration;
    private String duration;
    private String numOfStudents;
    private String venue;
    private String examiner;
    private String invigilator;
    private String inAttendance;
    private String examID;

    public TimetableEntity() {
        // Default constructor
    }

    public TimetableEntity(String date, String time, String course, String courseNarration, String duration, String numOfStudents, String venue, String examiner, String invigilator, String inAttendance,String examID) {

        this.date = date;
        this.time = time;
        this.course = course;
        this.courseNarration = courseNarration;
        this.duration = duration;
        this.numOfStudents = numOfStudents;
        this.venue = venue;
        this.examiner = examiner;
        this.invigilator = invigilator;
        this.inAttendance = inAttendance;
        this.examID = examID;

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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getInvigilator() {
        return invigilator;
    }

    public void setInvigilator(String invigilator) {
        this.invigilator = invigilator;
    }

    public String getInAttendance() {
        return inAttendance;
    }

    public void setInAttendance(String inAttendance) {
        this.inAttendance = inAttendance;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }
}

