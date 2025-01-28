package com.app.timetable.Service;

import com.app.timetable.Entity.Administrator;
import com.app.timetable.Entity.InAttendance;
import com.app.timetable.Interface.InAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InAttendanceService {

    @Autowired
    private InAttendanceRepository inAttendanceRepository;

    public List<InAttendance> getAllInAttendance() {
        return (List<InAttendance>) inAttendanceRepository.findAll();
    }

    public InAttendance getInAttendanceById(Long id) {

        return inAttendanceRepository.findById(id).orElse(null);
    }

    public InAttendance createInAttendance(InAttendance inAttendance) {
        return inAttendanceRepository.save(inAttendance);
    }

    public InAttendance updateInAttendance(Long id, InAttendance inAttendance) {
        InAttendance existingInAttendance = inAttendanceRepository.findById(id).orElse(null);
        if (existingInAttendance != null) {
            existingInAttendance.setName(inAttendance.getName());
            existingInAttendance.setSurname(inAttendance.getSurname());
            existingInAttendance.setTitle(inAttendance.getTitle());
            return inAttendanceRepository.save(existingInAttendance);
        } else {
            return null;
        }
    }

    public void deleteInAttendance(Long id) {
        inAttendanceRepository.deleteById(id);
    }
}

