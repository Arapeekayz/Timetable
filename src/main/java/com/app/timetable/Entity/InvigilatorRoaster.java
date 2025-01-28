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
@Table(name = "invigilator_roaster")
public class InvigilatorRoaster {

    // Getters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exam;
    private Long invigilatorID;

    public InvigilatorRoaster() {
        // Default constructor
    }

    public InvigilatorRoaster(Long exam, Long invigilatorID) {

        this.exam = exam;
        this.invigilatorID = invigilatorID;
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

    public Long getInvigilatorID() {
        return invigilatorID;
    }

    public void setInvigilatorID(Long invigilatorID) {
        this.invigilatorID = invigilatorID;
    }
}

