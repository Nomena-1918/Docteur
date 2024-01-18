package org.docteur.docteur.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_symptomes")
@Data
public class PatientSymptome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_symptome")
    private Symptome symptome;

    @Column(name = "intensite")
    private Integer intensite;

    @Column(name = "date_consultation", insertable=false, updatable=false)
    private LocalDateTime dateConsultation;

    public PatientSymptome(Long idPatient, Long idSymptome, Integer intensite, LocalDateTime dateConsultation) {
        patient = new Patient();
        patient.setId(idPatient);
        symptome = new Symptome();
        symptome.setId(idSymptome);
        this.intensite = intensite;
        this.dateConsultation = dateConsultation;
    }

    public PatientSymptome() {
    }
}

