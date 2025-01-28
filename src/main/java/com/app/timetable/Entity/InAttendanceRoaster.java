package com.app.timetable.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "in_attendance_roaster")
public class InAttendanceRoaster {

    // Getters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exam;
    private Long inAttendanceID;

    public InAttendanceRoaster() {
        // Default constructor
    }

    public InAttendanceRoaster(Long exam, Long inAttendanceID) {

        this.exam = exam;
        this.inAttendanceID = inAttendanceID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExam() {
        return exam;
    }

    public void setExam(Long exam) {
        this.exam = exam;
    }

    public Long getInAttendanceID() {
        return inAttendanceID;
    }

    public void setInAttendanceID(Long inAttendanceID) {
        this.inAttendanceID = inAttendanceID;
    }
}

