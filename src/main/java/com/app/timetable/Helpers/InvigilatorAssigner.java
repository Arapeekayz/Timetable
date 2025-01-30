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

    public ArrayList<InvigilatorRoaster> getInvigilatorRoaster() throws InvalidNumberOfInvigilatorsException {
        ArrayList<Exam> smallExams = new ArrayList<>();

        for (Exam exam : exams) {
            if (exam.getNumOfStudents() < 30) {
                smallExams.add(exam);
            } else {
                assignInvigilators(exam);
            }
        }

        while (!smallExams.isEmpty()) {
            Exam exam1 = smallExams.remove(0);
            Exam exam2 = null;

            for (Exam exam : smallExams) {
                if (exam1.getNumOfStudents() + exam.getNumOfStudents() <= 30) {
                    exam2 = exam;
                    smallExams.remove(exam);
                    break;
                }
            }

            if (exam2 != null) {
                assignInvigilators(exam1, exam2);
            } else {
                assignInvigilators(exam1);
            }
        }

        return invigilatorRoasters;
    }

    private void assignInvigilators(Exam... exams) throws InvalidNumberOfInvigilatorsException {
        int totalStudents = Arrays.stream(exams).mapToInt(Exam::getNumOfStudents).sum();
        int requiredInvigilators = Math.floorDiv(totalStudents, maxCount) + 1;

        if (requiredInvigilators > invigilators.size()) {
            throw new InvalidNumberOfInvigilatorsException();
        }

        for (int i = 0; i < requiredInvigilators; i++) {
            Invigilator invigilator = findInvigilator(getCourseDepartment(Long.parseLong(exams[0].getCourseID())));
            for (Exam exam : exams) {
                InvigilatorRoaster invigilatorRoaster = new InvigilatorRoaster(exam.getId(), invigilator.getId());
                invigilatorRoasters.add(invigilatorRoaster);
            }
        }
    }

    private int getNumberOfInvigilationSlots() {
        int count = 0;
        for (Exam exam : exams) {
            count += Math.floorDiv(exam.getNumOfStudents(), maxCount) + 1;
        }
        return count;
    }

    private Invigilator findInvigilator(String department) {
        int max = Math.floorDiv(getNumberOfInvigilationSlots(), allInvigilators.size()) + 1;
        for (Invigilator invigilator : invigilators) {
            var x = invigilatorRoasters.stream().filter(var -> var.getInvigilatorID().equals(invigilator.getId())).toList();
            if (x.size() <= max) {
                if (!Objects.equals(invigilator.getDepartment(), department)) {
                    invigilators.remove(invigilator);
                    if (invigilators.isEmpty()) {
                        invigilators.addAll(allInvigilators);
                    }
                    return invigilator;
                }
            } else {
                invigilators.remove(invigilator);
            }
        }

        Invigilator invigilator = invigilators.get(0);
        invigilators.remove(invigilator);
        if (invigilators.isEmpty()) {
            invigilators.addAll(allInvigilators);
        }

        return invigilator;
    }

    private String getCourseDepartment(Long courseID) {
        var x = courses.stream().filter(var -> var.getId().equals(courseID)).toList();
        return x.get(0).getDepartment();
    }
}
