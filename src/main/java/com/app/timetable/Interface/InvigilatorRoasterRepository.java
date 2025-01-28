package com.app.timetable.Interface;

import com.app.timetable.Entity.InvigilatorRoaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvigilatorRoasterRepository extends CrudRepository<InvigilatorRoaster, Long> {

}

