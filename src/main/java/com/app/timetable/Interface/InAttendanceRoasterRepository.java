package com.app.timetable.Interface;

import com.app.timetable.Entity.Administrator;
import com.app.timetable.Entity.Course;
import com.app.timetable.Entity.InAttendanceRoaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InAttendanceRoasterRepository extends CrudRepository<InAttendanceRoaster, Long> {

}

