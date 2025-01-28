package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.Invigilator;
import com.app.timetable.Service.DepartmentService;
import com.app.timetable.Service.InvigilatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
@RequestMapping("/invigilators")
public class InvigilatorController {

    @Autowired
    private InvigilatorService invigilatorService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public String getAllInvigilators(Model model) {
        var list = invigilatorService.getAllInvigilators();
        var iList=new ArrayList<Invigilator>();
        for (Invigilator invigilator : list) {
            invigilator.setDepartment(departmentService.getDepartmentById(Long.parseLong(invigilator.getDepartment())).getName());
            iList.add(invigilator);
        }
        var dList = departmentService.getAllDepartments();
        model.addAttribute("departments", dList);
        model.addAttribute("invigilators", iList);
        if(!dList.isEmpty()) {
            model.addAttribute("defaultID", dList.get(0).getId());
        }
        return "invigilators.html";

    }

    @PostMapping("/add_invigilator")
    public RedirectView createInvigilator(@ModelAttribute Invigilator invigilator) {
        Invigilator newInvigilator=new Invigilator(
                StringConverter.toCamelCase(invigilator.getName()),
                StringConverter.toCamelCase(invigilator.getSurname()),
                StringConverter.toCamelCase(invigilator.getTitle()),
                invigilator.getDepartment(),
                invigilator.getEmail()
        );
        invigilatorService.createInvigilator(newInvigilator);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/invigilators");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateInvigilator(@PathVariable Long id, @ModelAttribute Invigilator invigilator) {
        Invigilator newInvigilator=new Invigilator(
                StringConverter.toCamelCase(invigilator.getName()),
                StringConverter.toCamelCase(invigilator.getSurname()),
                StringConverter.toCamelCase(invigilator.getTitle()),
                invigilator.getDepartment(),
                invigilator.getEmail()
        );
        invigilatorService.updateInvigilator(id,newInvigilator);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/invigilators");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteInvigilator(@PathVariable Long id) {
        invigilatorService.deleteInvigilator(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/invigilators");
        return redirectView;
    }

}
