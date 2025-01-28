package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.Course;
import com.app.timetable.Service.CourseService;
import com.app.timetable.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("")
    public String getAllCourses(Model model) {
        var list = courseService.getAllCourses();
        var cList=new ArrayList<Course>();
        for (Course course : list) {
            course.setDepartment(departmentService.getDepartmentById(Long.parseLong(course.getDepartment())).getName());
            cList.add(course);
        }
        var dList = departmentService.getAllDepartments();
        model.addAttribute("courses", cList);
        model.addAttribute("departments", dList);
        if(!dList.isEmpty()) {
            model.addAttribute("defaultID", dList.get(0).getId());
        }
        return "courses.html";

    }

    @PostMapping("/add_course")
    public RedirectView createCourse( @ModelAttribute Course course) {
        Course newCourse = new Course(StringConverter.toCamelCase(course.getName()),course.getDepartment(), StringConverter.toCamelCase(course.getCode()));
        courseService.createCourse(newCourse);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/courses");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateCourse(@PathVariable Long id, @ModelAttribute Course course) {
        Course newCourse = new Course(StringConverter.toCamelCase(course.getName()),course.getDepartment(), StringConverter.toCamelCase(course.getCode()));
        courseService.updateCourse(id,newCourse);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/courses");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/courses");
        return redirectView;
    }


}
