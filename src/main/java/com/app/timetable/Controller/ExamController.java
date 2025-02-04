package com.app.timetable.Controller;


import com.app.timetable.Helpers.FileService;
import com.app.timetable.Helpers.MultiPartFileToFileConverter;
import com.app.timetable.Helpers.PDFService;
import com.app.timetable.Helpers.TimetableHTMLGenerator;
import com.app.timetable.Entity.*;
import com.app.timetable.Service.*;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/exams")
public class ExamController {

    private static final String INVIGILATOR_TYPE = "Invigilators";
    private static final String IN_ATTENDANCE_TYPE = "In-attendance";
    private static final String ADMINISTRATOR_TYPE = "Administrators";

    @Autowired
    PDFService pdfService;

    @Autowired
    private EmailService emailService;


    @Autowired
    private ExamService examService;

    @Autowired
    private InvigilatorService invigilatorService;

    @Autowired
    private InAttendanceService inAttendanceService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private VenueService venueService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private InvigilatorRoasterService invigilatorRoasterService;

    @Autowired
    private InAttendanceRoasterService inattendanceRoasterService;

    @GetMapping("")
    public String getAllExams(Model model) {

        var exams = examService.getAllExams();
        model.addAttribute("exams", exams);

        return "exams.html";

    }

    @PostMapping("/add_exam")
    public RedirectView createExam(@ModelAttribute Exam exam) {
        examService.createExam(exam);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/exams");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateExam(@PathVariable Long id, @ModelAttribute Exam exam) {

        examService.updateExam(id, exam);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/exams");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/exams");
        return redirectView;
    }

    @GetMapping("/send-emails")
    public RedirectView sendEmails() {
        
        emailService.sendEmailsToAll();

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/exams");
        return redirectView;
    }

    @PostMapping("/uploadPDF")
    public RedirectView handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        ArrayList<Exam> data= FileService.getExamListFromPDF(MultiPartFileToFileConverter.convert(file));
        for (Exam exam : data) {
            examService.createExam(exam);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/exams");
        return redirectView;

    }


    private Map<String, Long> getType(String email) {

        var invigilators = invigilatorService.getAllInvigilators();
        var invigilator = invigilators.stream().filter(var -> var.getEmail().equals(email)).findFirst().orElse(null);

        var inAttendances = inAttendanceService.getAllInAttendance();
        var inAttendance = inAttendances.stream().filter(var -> var.getEmail().equals(email)).findFirst().orElse(null);

        var admins = administratorService.getAllAdministrators();
        var admin = admins.stream().filter(var -> var.getEmail().equals(email)).findFirst().orElse(null);

        Map<String, Long> map = new HashMap<>();

        if (invigilator != null) {
            map.put(INVIGILATOR_TYPE, invigilator.getId());
            return map;
        } else if (inAttendance != null) {
            map.put(IN_ATTENDANCE_TYPE, inAttendance.getId());
            return map;
        } else if (admin != null) {
            map.put(ADMINISTRATOR_TYPE, admin.getId());
            return map;
        } else {
            return null;
        }

    }

    private ArrayList<TimetableEntity> getTimetableEntities() {
        var exams = examService.getAllExams();
        var invigilatorRoasters = invigilatorRoasterService.getAllInvigilatorRoasters();
        var inAttendanceRoasters = inattendanceRoasterService.getAllInAttendanceRoasters();


        ArrayList<TimetableEntity> timetables = new ArrayList<>();
        for (Exam exam : exams) {

            StringBuilder invigilatorsList = new StringBuilder();
            StringBuilder inAttendanceList = new StringBuilder();

            for (InvigilatorRoaster invigilatorRoaster : invigilatorRoasters.stream().filter(var -> var.getExam().equals(exam.getId())).toList()) {
                Invigilator invigilator = invigilatorService.getInvigilatorById(invigilatorRoaster.getInvigilatorID());
                if (invigilator != null) {
                    invigilatorsList.append(invigilator.getName()).append(" ").append(invigilator.getSurname()).append(", ");
                } else {
                    for (InvigilatorRoaster roaster : invigilatorRoasters) {
                        invigilatorRoasterService.deleteInvigilatorRoaster(roaster.getInvigilatorID());
                    }
                }
            }

            for (InAttendanceRoaster inAttendanceRoaster : inAttendanceRoasters.stream().filter(var -> var.getExam().equals(exam.getId())).toList()) {
                InAttendance inAttendance = inAttendanceService.getInAttendanceById(inAttendanceRoaster.getInAttendanceID());
                if (inAttendance != null) {
                    inAttendanceList.append(inAttendance.getName()).append(" ").append(inAttendance.getSurname()).append(", ");
                } else {
                    for (InAttendanceRoaster roaster : inAttendanceRoasters) {
                        inattendanceRoasterService.deleteInAttendanceRoaster(roaster.getInAttendanceID());
                    }
                }
            }

            timetables.add(new TimetableEntity(exam.getDate(), exam.getTime(), exam.getCourseCode(), exam.getCourseNarration(), exam.getDuration(), exam.getNumOfStudents(), exam.getVenue(), exam.getExaminer() , invigilatorsList.toString(), inAttendanceList.toString(),exam.getId().toString()));
        }

        return timetables;
    }


    private String getEmailsString(){
        StringBuilder emails = new StringBuilder();
        for(Invigilator invigilator : invigilatorService.getAllInvigilators()) {
            emails.append(invigilator.getEmail()).append(", ");
        }
        for(InAttendance inAttendance:inAttendanceService.getAllInAttendance()) {
            emails.append(inAttendance.getEmail()).append(", ");
        }
        for (Administrator administrator : administratorService.getAllAdministrators()) {
            emails.append(administrator.getEmail()).append(", ");
        }

        return emails.toString().trim();
    }


}
