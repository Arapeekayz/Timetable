package com.app.timetable.Helpers;

import com.app.timetable.Entity.*;
import com.app.timetable.Exceptions.InvalidNumberOfInAttendancesException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InAttendanceAssigner {


    private final ArrayList<Exam> exams = new ArrayList<>();
    private final ArrayList<InAttendance> inAttendances = new ArrayList<>();

    private final ArrayList<InAttendanceRoaster> inAttendanceRoasters = new ArrayList<>();

    private final int maxCount;

    public InAttendanceAssigner(
            ArrayList<Exam> exams,
            List<InAttendance> inAttendances,
            int maxCount) {

        this.exams.addAll(exams);
        this.inAttendances.addAll(inAttendances);
        this.maxCount=maxCount;

        Collections.shuffle(this.inAttendances);

    }



    public ArrayList<InAttendanceRoaster> getInAttendances() throws InvalidNumberOfInAttendancesException {
        int count=0;
        for (Exam exam : exams) {

            if(Math.floorDiv(Integer.parseInt(exam.getNumOfStudents()), maxCount)+1>inAttendances.size()) {
                throw new InvalidNumberOfInAttendancesException();
            }

            for (int i = 0; i < Math.floorDiv(Integer.parseInt(exam.getNumOfStudents()), maxCount) + 1; i++) {
                InAttendance inAttendance = inAttendances.get(count);
                inAttendanceRoasters.add(
                        new InAttendanceRoaster(exam.getId(), inAttendance.getId())
                );
                if(count==inAttendances.size()-1){
                    count=0;
                }else {
                    count++;
                }
            }
        }

        return inAttendanceRoasters;
    }


}
