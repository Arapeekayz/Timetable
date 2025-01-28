package com.app.timetable.Service;

import com.app.timetable.Entity.Exam;
import com.app.timetable.Entity.InAttendanceRoaster;
import com.app.timetable.Interface.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return (List<Exam>) examRepository.findAll();
    }

    public Exam getExamById(Long id) {

        return examRepository.findById(id).orElse(null);
    }

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Exam updateExam(Long id, Exam exam) {
        Exam existingExam = examRepository.findById(id).orElse(null);
        if (existingExam != null) {
            existingExam.setDate(exam.getDate());
            existingExam.setTime(exam.getTime());
            existingExam.setCourseCode(exam.getCourseCode());
            existingExam.setCourseNarration(exam.getCourseNarration());
            existingExam.setDuration(exam.getDuration());
            existingExam.setNumOfStudents(exam.getNumOfStudents());
            existingExam.setVenue(exam.getVenue());
            existingExam.setExaminer(exam.getExaminer());
            return examRepository.save(existingExam);
        } else {
            return null;
        }
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}

