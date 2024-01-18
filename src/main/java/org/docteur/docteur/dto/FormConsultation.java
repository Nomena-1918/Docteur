package org.docteur.docteur.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Getter
public class FormConsultation {
    private Long idPatient;
    private String dateConsultation;
    private MultipartFile file;


    public FormConsultation() {
    }

    public FormConsultation setIdPatient(Long idPatient) {
        if (idPatient == null) throw new IllegalArgumentException("idPatient ne peut pas être null");
        this.idPatient = idPatient;
        return this;
    }

    public FormConsultation setDateConsultation(String dateConsultation) {
        if (dateConsultation == null)
            throw new IllegalArgumentException("dateConsultation ne peut pas être null");
        LocalDateTime date = LocalDateTime.parse(dateConsultation);
        if (date.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("dateConsultation ne peut pas être dans le futur");
        this.dateConsultation = dateConsultation;
        return this;
    }

    public FormConsultation setFile(MultipartFile file) {
        if (file == null)
            throw new IllegalArgumentException("file ne peut pas être null");
        this.file = file;
        return this;
    }


    public LocalDateTime getDateConsultationT() {
        return LocalDateTime.parse(dateConsultation);
    }
}
