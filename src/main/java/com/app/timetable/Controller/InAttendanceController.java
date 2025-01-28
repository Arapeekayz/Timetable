package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.InAttendance;
import com.app.timetable.Service.InAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/in_attendances")
public class InAttendanceController {

    @Autowired
    private InAttendanceService inAttendanceService;

    @GetMapping("")
    public String getAllInAttendance(Model model) {
        var list = inAttendanceService.getAllInAttendance();
        model.addAttribute("in_attendances", list);
        return "inAttendance.html";
    }

    @PostMapping("/add_in_attendance")
    public RedirectView createInAttendance( @ModelAttribute InAttendance inAttendance) {
        InAttendance newInAttendance = new InAttendance(StringConverter.toCamelCase(inAttendance.getName()), StringConverter.toCamelCase(inAttendance.getSurname()), StringConverter.toCamelCase(inAttendance.getTitle()),inAttendance.getEmail());
        inAttendanceService.createInAttendance(newInAttendance);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/in_attendances");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateInAttendance(@PathVariable String id, @ModelAttribute InAttendance inAttendance) {
        InAttendance newInAttendance = new InAttendance(StringConverter.toCamelCase(inAttendance.getName()), StringConverter.toCamelCase(inAttendance.getSurname()), StringConverter.toCamelCase(inAttendance.getTitle()),inAttendance.getEmail());
        inAttendanceService.updateInAttendance(Long.parseLong(id), newInAttendance);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/in_attendances");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteInAttendance(@PathVariable Long id) {
        inAttendanceService.deleteInAttendance(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/in_attendances");
        return redirectView;
    }

}
