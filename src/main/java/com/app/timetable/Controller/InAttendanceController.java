package com.app.timetable.Controller;

import com.app.timetable.Entity.Invigilator;
import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.InAttendance;
import com.app.timetable.Service.InAttendanceService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

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
        InAttendance newInAttendance = new InAttendance(StringConverter.toCamelCase(inAttendance.getName()), StringConverter.toCamelCase(inAttendance.getSurname()),inAttendance.getEmail());
        inAttendanceService.createInAttendance(newInAttendance);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/in_attendances");
        return redirectView;
    }

    @PostMapping("/update/{id}")
    public RedirectView updateInAttendance(@PathVariable String id, @ModelAttribute InAttendance inAttendance) {
        InAttendance newInAttendance = new InAttendance(StringConverter.toCamelCase(inAttendance.getName()), StringConverter.toCamelCase(inAttendance.getSurname()),inAttendance.getEmail());
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

    @PostMapping("/upload")
    public RedirectView mapReapExcelDataToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=0;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            InAttendance inAttendance = new InAttendance();

            XSSFRow row = worksheet.getRow(i);

            inAttendance.setName(row.getCell(0).getStringCellValue());
            inAttendance.setSurname(row.getCell(1).getStringCellValue());
            inAttendance.setEmail(row.getCell(2).getStringCellValue());

            inAttendanceService.createInAttendance(inAttendance);

        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/in_attendances");
        return redirectView;
    }

}
