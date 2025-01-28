package com.app.timetable.Controller;

import com.app.timetable.Entity.InAttendanceRoaster;
import com.app.timetable.Entity.InvigilatorRoaster;
import com.app.timetable.Service.InAttendanceRoasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/in_attendance_roasters")
public class InAttendanceRoasterController {

    @Autowired
    private InAttendanceRoasterService inAttendanceRoasterService;

    @GetMapping("")
    public List<InAttendanceRoaster> getAllInAttendanceRoaster() {
        return inAttendanceRoasterService.getAllInAttendanceRoasters();
    }

    @GetMapping("/{id}")
    public InAttendanceRoaster getInAttendanceRoasterById(@PathVariable Long id) {
        return inAttendanceRoasterService.getInAttendanceRoasterById(id);
    }

    @PostMapping("")
    public InAttendanceRoaster createInAttendanceRoaster(@RequestBody InAttendanceRoaster inAttendanceRoaster) {
        return inAttendanceRoasterService.createInAttendanceRoaster(inAttendanceRoaster);
    }

    @PutMapping("/{id}")
    public InAttendanceRoaster updateInAttendanceRoaster(@PathVariable Long id, @RequestBody InAttendanceRoaster inAttendanceRoaster) {
        return inAttendanceRoasterService.updateInAttendanceRoaster(id, inAttendanceRoaster);
    }

    @DeleteMapping("/{id}")
    public void deleteInAttendanceRoaster(@PathVariable Long id) {
        inAttendanceRoasterService.deleteInAttendanceRoaster(id);

    }


}
