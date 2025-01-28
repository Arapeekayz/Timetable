package com.app.timetable.Service;

import com.app.timetable.Entity.Invigilator;
import com.app.timetable.Interface.InvigilatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvigilatorService {

    @Autowired
    private InvigilatorRepository invigilatorRepository;

    public List<Invigilator> getAllInvigilators() {
        return (List<Invigilator>) invigilatorRepository.findAll();
    }

    public Invigilator getInvigilatorById(Long id) {

        return invigilatorRepository.findById(id).orElse(null);
    }

    public Invigilator createInvigilator(Invigilator invigilator) {
        return invigilatorRepository.save(invigilator);
    }

    public Invigilator updateInvigilator(Long id, Invigilator invigilator) {
        Invigilator existingInvigilator = invigilatorRepository.findById(id).orElse(null);
        if (existingInvigilator != null) {
            existingInvigilator.setName(invigilator.getName());
            existingInvigilator.setSurname(invigilator.getSurname());
            existingInvigilator.setTitle(invigilator.getTitle());
            existingInvigilator.setDepartment(invigilator.getDepartment());
            return invigilatorRepository.save(existingInvigilator);
        } else {
            return null;
        }
    }

    public void deleteInvigilator(Long id) {
        invigilatorRepository.deleteById(id);
    }
}

