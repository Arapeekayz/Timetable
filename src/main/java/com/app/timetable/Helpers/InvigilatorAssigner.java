package com.app.timetable.Helpers;

import com.app.timetable.Entity.*;
import com.app.timetable.Exceptions.InvalidNumberOfInAttendancesException;
import com.app.timetable.Exceptions.InvalidNumberOfInvigilatorsException;

import java.util.*;

public class InvigilatorAssigner {

    private final ArrayList<Course> courses = new ArrayList<>();
    private final ArrayList<Exam> exams = new ArrayList<>();
    private final ArrayList<Invigilator> invigilators = new ArrayList<>();
    private final ArrayList<Invigilator> allInvigilators = new ArrayList<>();
    private final ArrayList<InvigilatorRoaster> invigilatorRoasters = new ArrayList<>();
    int maxCount;

    public InvigilatorAssigner(
            List<Course> courses,
            List<Exam> exams,
            List<Invigilator> invigilators,
            int maxCount) {

        this.courses.addAll(courses);
        this.exams.addAll(exams);
        this.allInvigilators.addAll(invigilators);
        Collections.shuffle(this.allInvigilators);
        this.invigilators.addAll(allInvigilators);
        this.maxCount = maxCount;
    }

    public void assignInvigilators() throws InvalidNumberOfInAttendancesException, InvalidNumberOfInvigilatorsException {
        for (Exam exam : exams) {
            if (exam.getNumOfStudents() < 30) {
                Exam combinedExam = findAndCombineExam(exam);
                if (combinedExam != null) {
                    assignInvigilatorsToExam(combinedExam);
                } else {
                    assignInvigilatorsToExam(exam);
                }
            } else {
                assignInvigilatorsToExam(exam);
            }
        }
    }

    private Exam findAndCombineExam(Exam exam) {
        for (Exam otherExam : exams) {
            if (otherExam != exam && otherExam.getNumOfStudents() < 30 &&
                    (exam.getNumOfStudents() + otherExam.getNumOfStudents()) <= 30) {
                return combineExams(exam, otherExam);
            }
        }
        return null;
    }

    private Exam combineExams(Exam exam1, Exam exam2) {
        exam1.setNumOfStudents(exam1.getNumOfStudents() + exam2.getNumOfStudents());
        exams.remove(exam2);
        return exam1;
    }

    private void assignInvigilatorsToExam(Exam exam) throws InvalidNumberOfInvigilatorsException {
        int requiredInvigilators = exam.getNumOfStudents() > 30 ? 2 : 1;
        if (invigilators.size() < requiredInvigilators) {
            throw new InvalidNumberOfInvigilatorsException();
        }
        for (int i = 0; i < requiredInvigilators; i++) {
            Invigilator invigilator = invigilators.remove(0);
            invigilatorRoasters.add(new InvigilatorRoaster(invigilator.getId(), exam.getId()));
        }
    }
}
