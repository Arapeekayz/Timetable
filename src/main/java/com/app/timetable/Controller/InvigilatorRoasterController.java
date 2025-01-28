package com.app.timetable.Controller;

import com.app.timetable.Entity.Exam;
import com.app.timetable.Entity.InvigilatorRoaster;
import com.app.timetable.Service.InvigilatorRoasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invigilator_roasters")
public class InvigilatorRoasterController {

    @Autowired
    private InvigilatorRoasterService invigilatorRoasterService;

    @GetMapping("")
    public List<InvigilatorRoaster> getAllInvigilatorRoaster() {
        return invigilatorRoasterService.getAllInvigilatorRoasters();
    }

    @GetMapping("/{id}")
    public InvigilatorRoaster getInvigilatorRoasterById(@PathVariable Long id) {
        return invigilatorRoasterService.getInvigilatorRoasterById(id);
    }

    @PostMapping("")
    public InvigilatorRoaster createInvigilatorRoaster(@RequestBody InvigilatorRoaster invigilatorRoaster) {
        return invigilatorRoasterService.createInvigilatorRoaster(invigilatorRoaster);
    }

    @PutMapping("/{id}")
    public InvigilatorRoaster updateInvigilatorRoaster(@PathVariable Long id, @RequestBody InvigilatorRoaster invigilatorRoaster) {
        return invigilatorRoasterService.updateInvigilatorRoaster(id, invigilatorRoaster);
    }

    @DeleteMapping("/{id}")
    public void deleteInvigilatorRoaster(@PathVariable Long id) {
        invigilatorRoasterService.deleteInvigilatorRoaster(id);

    }


}
