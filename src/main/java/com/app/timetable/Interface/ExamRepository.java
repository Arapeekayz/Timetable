package com.app.timetable.Interface;

import com.app.timetable.Entity.Exam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long> {
}

