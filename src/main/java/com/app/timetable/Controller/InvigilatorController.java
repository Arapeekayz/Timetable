package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.Invigilator;
import com.app.timetable.Service.DepartmentService;
import com.app.timetable.Service.InvigilatorService;
import jakarta.persistence.criteria.CriteriaBuilder;
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
import java.util.ArrayList;
import java.util.List;

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

        model.addAttribute("invigilators", list);

        return "invigilators.html";

    }

    @PostMapping("/add_invigilator")
    public RedirectView createInvigilator(@ModelAttribute Invigilator invigilator) {
        Invigilator newInvigilator=new Invigilator(
                StringConverter.toCamelCase(invigilator.getName()),
                StringConverter.toCamelCase(invigilator.getSurname()),
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

    @PostMapping("/upload")
    public RedirectView mapReapExcelDataToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=0;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            Invigilator invigilator = new Invigilator();

            XSSFRow row = worksheet.getRow(i);

            invigilator.setName(row.getCell(0).getStringCellValue());
            invigilator.setSurname(row.getCell(1).getStringCellValue());
            invigilator.setEmail(row.getCell(2).getStringCellValue());

            invigilatorService.createInvigilator(invigilator);

        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/invigilators");
        return redirectView;
    }

}
