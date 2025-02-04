package com.app.timetable.Controller;

import com.app.timetable.Helpers.*;
import com.app.timetable.Entity.*;
import com.app.timetable.Exceptions.InvalidNumberOfInAttendancesException;
import com.app.timetable.Exceptions.InvalidNumberOfInvigilatorsException;
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
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    PDFService pdfService;

    @Autowired
    private InvigilatorService invigilatorService;

    @Autowired
    private ExamService examService;

    @Autowired
    private InAttendanceService inAttendanceService;

    @Autowired
    private InvigilatorRoasterService invigilatorRoasterService;

    @Autowired
    private InAttendanceRoasterService inattendanceRoasterService;


    @GetMapping("/")
    public String getDefault() {
        return "landing_page.html";
    }

    @GetMapping("/home")
    public String getHome(Model model)
    {
        model.addAttribute("timetableEntities", getTimetableEntities());
        return "home.html";
    }

    @GetMapping(value = "/generate_pdf")
    public ResponseEntity<byte[]> convertToPDF() throws DocumentException, IOException {

        byte[] bytes = pdfService.generatePDFFromHTML(TimetableHTMLGenerator.generateHTML(getTimetableEntities(), "Main timetable"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "output.pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

    }

    @PostMapping("/assign_staff")
    public RedirectView assignStaff(int maxInvigilators, int maxInAttendance) throws InvalidNumberOfInvigilatorsException, InvalidNumberOfInAttendancesException {

        assignInvigilators(maxInvigilators);
        assignInAttendances(maxInAttendance);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/home");
        return redirectView;

    }

    private void assignInvigilators(int maxCount) throws InvalidNumberOfInvigilatorsException {

        ArrayList<InvigilatorRoaster> roasters = new ArrayList<>(invigilatorRoasterService.getAllInvigilatorRoasters());

        for (InvigilatorRoaster invigilatorRoaster : roasters) {
            invigilatorRoasterService.deleteInvigilatorRoaster(invigilatorRoaster.getId());
        }

        InvigilatorAssigner invigilatorAssigner = new InvigilatorAssigner( examService.getAllExams(), invigilatorService.getAllInvigilators(), maxCount);
        ArrayList<InvigilatorRoaster> invigilatorRoasters = invigilatorAssigner.assignInvigilators();
        for (InvigilatorRoaster invigilatorRoaster : invigilatorRoasters) {
            invigilatorRoasterService.createInvigilatorRoaster(invigilatorRoaster);
        }

    }

    private void assignInAttendances(int maxCount) throws InvalidNumberOfInAttendancesException {

        ArrayList<InAttendanceRoaster> roasters = new ArrayList<>(inattendanceRoasterService.getAllInAttendanceRoasters());

        for (InAttendanceRoaster inAttendanceRoaster : roasters) {
            invigilatorRoasterService.deleteInvigilatorRoaster(inAttendanceRoaster.getId());
        }

        InAttendanceAssigner inAttendanceAssigner = new InAttendanceAssigner((ArrayList<Exam>) examService.getAllExams(), inAttendanceService.getAllInAttendance(), maxCount);
        ArrayList<InAttendanceRoaster> inAttendanceRoasters = inAttendanceAssigner.getInAttendances();
        for (InAttendanceRoaster inAttendanceRoaster : inAttendanceRoasters) {
            inattendanceRoasterService.createInAttendanceRoaster(inAttendanceRoaster);
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


}
