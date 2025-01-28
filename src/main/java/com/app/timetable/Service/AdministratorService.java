package com.app.timetable.Service;

import com.app.timetable.Entity.Administrator;
import com.app.timetable.Interface.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public List<Administrator> getAllAdministrators() {
        return (List<Administrator>) administratorRepository.findAll();
    }

    public Administrator getAdministratorById(Long id) {

        return administratorRepository.findById(id).orElse(null);
    }

    public Administrator createAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public Administrator updateAdministrator(Long id, Administrator administrator) {
        Administrator existingAdministrator = administratorRepository.findById(id).orElse(null);
        if (existingAdministrator != null) {
            existingAdministrator.setName(administrator.getName());
            existingAdministrator.setSurname(administrator.getSurname());
            existingAdministrator.setTitle(administrator.getTitle());
            return administratorRepository.save(existingAdministrator);
        } else {
            return null;
        }
    }

    public void deleteAdministrator(Long id) {
        administratorRepository.deleteById(id);
    }
}

