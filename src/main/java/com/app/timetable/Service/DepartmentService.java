package com.app.timetable.Service;

import com.app.timetable.Entity.Department;
import com.app.timetable.Entity.Venue;
import com.app.timetable.Interface.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {

        return departmentRepository.findById(id).orElse(null);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department department) {
        Department existingDepartment = departmentRepository.findById(id).orElse(null);
        if (existingDepartment != null) {
            existingDepartment.setName(department.getName());
            existingDepartment.setCode(department.getCode());
            return departmentRepository.save(existingDepartment);
        } else {
            return null;
        }
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}

