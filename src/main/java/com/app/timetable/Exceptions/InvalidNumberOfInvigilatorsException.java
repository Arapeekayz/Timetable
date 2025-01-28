package com.app.timetable.Exceptions;

public class InvalidNumberOfInvigilatorsException extends Exception{
    public InvalidNumberOfInvigilatorsException(){
        super("Invalid number of invigilators. Increase the number of invigilator staff or increase the maximum number of candidates per staff member.");
    }
}
