package com.app.timetable.Helpers;

import com.app.timetable.Entity.*;
import com.app.timetable.Exceptions.InvalidNumberOfInAttendancesException;
import com.app.timetable.Exceptions.InvalidNumberOfInvigilatorsException;

import java.util.*;
import java.util.stream.Collectors;


public class InvigilatorAssigner {

    private final ArrayList<Exam> exams = new ArrayList<>();
    private final ArrayList<Invigilator> invigilators = new ArrayList<>();

    private final ArrayList<InvigilatorRoaster> invigilatorRoasters = new ArrayList<>();

    int maxCount;
    int offset = 0;


    public InvigilatorAssigner(
            List<Exam> exams,
            List<Invigilator> invigilators,
            int maxCount) {

        this.exams.addAll(exams);
        this.invigilators.addAll(invigilators);

        Collections.shuffle(this.invigilators);

        this.maxCount = maxCount;

    }

    public ArrayList<InvigilatorRoaster> getInvigilatorRoaster() throws InvalidNumberOfInvigilatorsException {

        for (Exam exam : exams) {

            if (Math.floorDiv(Integer.parseInt(exam.getNumOfStudents()), maxCount) + 1 > invigilators.size()) {
                throw new InvalidNumberOfInvigilatorsException();
            }

            for (int i = 0; i < Math.floorDiv(Integer.parseInt(exam.getNumOfStudents()), maxCount) + 1; i++) {
                Invigilator invigilator = invigilators.get(offset);
                invigilatorRoasters.add(
                        new InvigilatorRoaster(exam.getId(), invigilator.getId())
                );
                if (offset == invigilators.size() - 1) {
                    offset = 0;
                } else {
                    offset++;
                }
            }
        }

        return invigilatorRoasters;

    }


    private int getNumberOfInvigilationSlots() {
        int count = 0;

        for (Exam exam : exams) {
            count += Math.floorDiv(Integer.parseInt(exam.getNumOfStudents()), maxCount) + 1;
        }

        return count;
    }


    public ArrayList<InvigilatorRoaster> assignInvigilators() {

        Map<String, List<Exam>> groupedByDate = exams.stream().collect(Collectors.groupingBy(Exam::getDate));
        groupedByDate.forEach((dKey, dValue) -> {
            Map<String, List<Exam>> groupedByTime = dValue.stream().collect(Collectors.groupingBy(Exam::getTime));
            groupedByTime.forEach((tKey, tValue) -> {
                Map<String, List<Exam>> groupedByVenue = tValue.stream().collect(Collectors.groupingBy(Exam::getVenue));
                groupedByVenue.forEach((vKey, vValue) -> {

                    ArrayList<Exam> lessThanMax = new ArrayList<>();

                    for (Exam exam : vValue) {

                        if (Integer.parseInt(exam.getNumOfStudents()) >= maxCount) {
                            assignStaff(exam);
                        } else {
                            lessThanMax.add(exam);
                        }
                    }
                    assignGroupedExam(lessThanMax);
                });
            });
        });

        return invigilatorRoasters;


    }

    private void assignStaff(Exam exam) {
        int count = Math.floorDiv(Integer.parseInt(exam.getNumOfStudents()), maxCount) + 1;
        for (int i = 0; i < count; i++) {
            Invigilator invigilator = invigilators.get(offset);
            invigilatorRoasters.add(
                    new InvigilatorRoaster(exam.getId(), invigilator.getId())
            );
            if (offset == invigilators.size() - 1) {
                offset = 0;
            } else {
                offset++;
            }
        }
    }

    private void assignGroupedExam(ArrayList<Exam> lessThanMax) {

        Map<String,ArrayList<Exam>> map=getGroupedExamList(lessThanMax);
        map.forEach((k, v) -> {
            for (Exam exam : v) {
                Invigilator invigilator = invigilators.get(offset);
                invigilatorRoasters.add(
                        new InvigilatorRoaster(exam.getId(), invigilator.getId())
                );
            }
            if (offset == invigilators.size() - 1) {
                offset = 0;
            } else {
                offset++;
            }
        });

    }

    private Map<String,ArrayList<Exam>> getGroupedExamList(ArrayList<Exam> lessThanMax){

        Map<String,ArrayList<Exam>> map = new HashMap<>();
        ArrayList<Exam> unassigned = new ArrayList<>(lessThanMax);

        int counter = 0;

        for (int i = 0; i < lessThanMax.size(); i++) {
            if(!unassigned.contains(lessThanMax.get(i))) {
                continue;
            }

            for (int j = i + 1; j < lessThanMax.size(); j++) {
                if(!unassigned.contains(lessThanMax.get(j))) {
                    continue;
                }
                if(Integer.parseInt(lessThanMax.get(i).getNumOfStudents()) + Integer.parseInt(lessThanMax.get(j).getNumOfStudents())<maxCount) {
                    ArrayList<Exam> groupedExam = new ArrayList<>();
                    groupedExam.add(lessThanMax.get(i));
                    groupedExam.add(lessThanMax.get(j));
                    unassigned.remove(lessThanMax.get(i));
                    unassigned.remove(lessThanMax.get(j));
                    map.put(Integer.toString(counter), groupedExam);
                    counter++;
                    break;
                }
            }

            if(unassigned.contains(lessThanMax.get(i))) {
                ArrayList<Exam> groupedExam = new ArrayList<>();
                groupedExam.add(lessThanMax.get(i));
                unassigned.remove(lessThanMax.get(i));
                map.put(Integer.toString(counter), groupedExam);
                counter++;
            }


        }

        return map;

    }

}
