package com.app.timetable.Interface;

import com.app.timetable.Entity.Department;
import com.app.timetable.Entity.Invigilator;
import com.app.timetable.Entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

}

