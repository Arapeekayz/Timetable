package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.Department;
import com.app.timetable.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public String getAllDepartmentS(Model model) {
        var list = departmentService.getAllDepartments();
        model.addAttribute("departments", list);
        return "departments.html";
    }

    @PostMapping("/add_department")
    public RedirectView createDepartment( @ModelAttribute Department department) {
        Department newDepartment = new Department(StringConverter.toCamelCase(department.getName()), StringConverter.toCamelCase(department.getCode()));
        departmentService.createDepartment(newDepartment);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/departments");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateDepartment(@PathVariable String id, @ModelAttribute Department department) {
        Department newDepartment = new Department(StringConverter.toCamelCase(department.getName()), StringConverter.toCamelCase(department.getCode()));
        departmentService.updateDepartment(Long.parseLong(id), newDepartment);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/departments");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/departments");
        return redirectView;
    }


}
