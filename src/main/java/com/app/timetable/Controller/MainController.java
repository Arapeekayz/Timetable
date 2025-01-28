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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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
    private AdministratorService administratorService;

    @Autowired
    private VenueService venueService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private InvigilatorRoasterService invigilatorRoasterService;

    @Autowired
    private InAttendanceRoasterService inattendanceRoasterService;


    @GetMapping("/")
    public String getDefault() {
        return "landing_page.html";
    }


/*
    @PostMapping("/assign_staff")
    public RedirectView assignStaff(int maxInvigilators, int maxInAttendance) throws InvalidNumberOfInvigilatorsException, InvalidNumberOfInAttendancesException {

        assignInvigilators(maxInvigilators);
        assignInAttendances(maxInAttendance);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/exams");
        return redirectView;

    }*/
/*

    @GetMapping(value = "/generate_pdf")
    public ResponseEntity<byte[]> convertToPDF() throws DocumentException, IOException {

        byte[] bytes = pdfService.generatePDFFromHTML(TimetableHTMLGenerator.generateHTML(getTimetableEntities(), "Generated timetable"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "output.pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

    }

    @GetMapping("/get_timetable/{id}")
    public ResponseEntity<byte[]> getMyTimetable(@PathVariable Long id) throws DocumentException, IOException {

        Invigilator invigilator = invigilatorService.getInvigilatorById(id);

        byte[] bytes = pdfService.generatePDFFromHTML(TimetableHTMLGenerator.generateHTML(getInvigilatorTimetable(invigilator), "Your timetable"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "output.pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

    }
*/

/*


    private ArrayList<TimetableEntity> getInvigilatorTimetable(Invigilator invigilator) {

        var invigilatorRoasters = invigilatorRoasterService.getAllInvigilatorRoasters();
        var inAttendanceRoasters = inattendanceRoasterService.getAllInAttendanceRoasters();

        ArrayList<Exam> exams=new ArrayList<>();

        List<InvigilatorRoaster> roasters = invigilatorRoasterService.getAllInvigilatorRoasters().stream().filter(var -> var.getInvigilatorID().equals(invigilator.getId())).toList();

        for(InvigilatorRoaster invigilatorRoaster : roasters) {
            exams.add(examService.getExamById(invigilatorRoaster.getExam()));
        }

        exams.sort(Comparator.comparing(Exam::getTime));
        exams.sort(Comparator.comparing(Exam::getDate));


        ArrayList<TimetableEntity> timetables = new ArrayList<>();
        for (Exam exam : exams) {
            Course course = courseService.getCourseById(Long.parseLong(exam.getCourseID()));
            Venue venue = venueService.getVenueById(Long.parseLong(exam.getVenue()));

            StringBuilder invigilatorsList = new StringBuilder();
            StringBuilder inAttendanceList = new StringBuilder();

            for (InvigilatorRoaster invigilatorRoaster : invigilatorRoasters.stream().filter(var -> var.getExam().equals(exam.getId())).toList()) {
                Invigilator inv = invigilatorService.getInvigilatorById(invigilatorRoaster.getInvigilatorID());
                invigilatorsList.append(inv.getName()).append(" ").append(inv.getSurname()).append(", ");
            }

            for (InAttendanceRoaster inAttendanceRoaster : inAttendanceRoasters.stream().filter(var -> var.getExam().equals(exam.getId())).toList()) {
                InAttendance inAttendance = inAttendanceService.getInAttendanceById(inAttendanceRoaster.getInAttendanceID());
                inAttendanceList.append(inAttendance.getName()).append(" ").append(inAttendance.getSurname()).append(", ");
            }

            Administrator administrator = administratorService.getAdministratorById(Long.parseLong(exam.getAdministrator()));

            timetables.add(new TimetableEntity(exam.getDate(), exam.getTime(), course.getCode(), course.getName(), invigilatorsList.toString(), Integer.toString(exam.getNumOfStudents()), venue.getName(), administrator.getName() + " " + administrator.getSurname(), inAttendanceList.toString(), ""));
        }

        return timetables;

    }


    private void assignInvigilators(int maxCount) throws InvalidNumberOfInvigilatorsException {

        ArrayList<InvigilatorRoaster> roasters = new ArrayList<>(invigilatorRoasterService.getAllInvigilatorRoasters());

        for (InvigilatorRoaster invigilatorRoaster : roasters) {
            invigilatorRoasterService.deleteInvigilatorRoaster(invigilatorRoaster.getId());
        }

        InvigilatorAssigner invigilatorAssigner = new InvigilatorAssigner(courseService.getAllCourses(), examService.getAllExams(), invigilatorService.getAllInvigilators(), maxCount);
        ArrayList<InvigilatorRoaster> invigilatorRoasters = invigilatorAssigner.getInvigilatorRoaster();
        for (InvigilatorRoaster invigilatorRoaster : invigilatorRoasters) {
            invigilatorRoasterService.createInvigilatorRoaster(invigilatorRoaster);
        }

    }

    private void assignInAttendances(int maxCount) throws InvalidNumberOfInAttendancesException {

        ArrayList<InAttendanceRoaster> iRoasters = new ArrayList<>(inattendanceRoasterService.getAllInAttendanceRoasters());

        for (InAttendanceRoaster inAttendanceRoaster : iRoasters) {
            inattendanceRoasterService.deleteInAttendanceRoaster(inAttendanceRoaster.getId());
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

        exams.sort(Comparator.comparing(Exam::getTime));
        exams.sort(Comparator.comparing(Exam::getDate));


        ArrayList<TimetableEntity> timetables = new ArrayList<>();
        for (Exam exam : exams) {
            Course course = courseService.getCourseById(Long.parseLong(exam.getCourseID()));
            Venue venue = venueService.getVenueById(Long.parseLong(exam.getVenue()));

            StringBuilder invigilatorsList = new StringBuilder();
            StringBuilder inAttendanceList = new StringBuilder();

            for (InvigilatorRoaster invigilatorRoaster : invigilatorRoasters.stream().filter(var -> var.getExam().equals(exam.getId())).toList()) {
                Invigilator invigilator = invigilatorService.getInvigilatorById(invigilatorRoaster.getInvigilatorID());
                invigilatorsList.append(invigilator.getName()).append(" ").append(invigilator.getSurname()).append(", ");
            }

            for (InAttendanceRoaster inAttendanceRoaster : inAttendanceRoasters.stream().filter(var -> var.getExam().equals(exam.getId())).toList()) {
                InAttendance inAttendance = inAttendanceService.getInAttendanceById(inAttendanceRoaster.getInAttendanceID());
                inAttendanceList.append(inAttendance.getName()).append(" ").append(inAttendance.getSurname()).append(", ");
            }

            Administrator administrator = administratorService.getAdministratorById(Long.parseLong(exam.getAdministrator()));

            timetables.add(new TimetableEntity(exam.getDate(), exam.getTime(), course.getCode(), course.getName(), invigilatorsList.toString(), Integer.toString(exam.getNumOfStudents()), venue.getName(), administrator.getName() + " " + administrator.getSurname(), inAttendanceList.toString(), ""));
        }

        return timetables;
    }


    private ResponseEntity<byte[]> getPDFTable(String filename, String title,Invigilator invigilator) throws DocumentException, IOException {
        byte[] bytes = pdfService.generatePDFFromHTML(TimetableHTMLGenerator.generateHTML(getInvigilatorTimetable(invigilator), title));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(filename, filename + ".pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
*/

}
