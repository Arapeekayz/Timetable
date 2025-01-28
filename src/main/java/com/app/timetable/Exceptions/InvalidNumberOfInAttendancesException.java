package com.app.timetable.Exceptions;

public class InvalidNumberOfInAttendancesException extends Exception {
    public InvalidNumberOfInAttendancesException(){
        super("Invalid number of in-attendances. Increase the number of in-attendance staff or increase the maximum number of candidates per staff member.");
    }
}
