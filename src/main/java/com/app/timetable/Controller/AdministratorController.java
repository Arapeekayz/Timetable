package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.Administrator;
import com.app.timetable.Service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/administrators")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("")
    public String getAllAdministrators(Model model) {
        var list = administratorService.getAllAdministrators();
        model.addAttribute("administrators", list);
        return "administrators.html";
    }

    @PostMapping("/add_administrator")
    public RedirectView createAdministrator( @ModelAttribute Administrator administrator) {
        Administrator newAdministrator = new Administrator(StringConverter.toCamelCase(administrator.getName()), StringConverter.toCamelCase(administrator.getSurname()), StringConverter.toCamelCase(administrator.getTitle()),administrator.getEmail());
        administratorService.createAdministrator(newAdministrator);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/administrators");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateAdministrator(@PathVariable String id, @ModelAttribute Administrator administrator) {
        Administrator newAdministrator = new Administrator(StringConverter.toCamelCase(administrator.getName()), StringConverter.toCamelCase(administrator.getSurname()), StringConverter.toCamelCase(administrator.getTitle()),administrator.getEmail());
        administratorService.updateAdministrator(Long.parseLong(id), newAdministrator);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/administrators");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteAdministrator(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/administrators");
        return redirectView;
    }


}
