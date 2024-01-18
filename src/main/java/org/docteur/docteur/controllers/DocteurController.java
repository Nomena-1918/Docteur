package org.docteur.docteur.controllers;

import jakarta.transaction.Transactional;
import org.docteur.docteur.dto.FormConsultation;
import org.docteur.docteur.models.Patient;
import org.docteur.docteur.models.*;
import org.docteur.docteur.models.data.Diagnostique;
import org.docteur.docteur.repositories.*;
import org.docteur.docteur.service.*;
import org.docteur.docteur.utils.ExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class DocteurController {
    private final PatientRepository patientRepository;
    private final PatientSymptomeRepository patientSymptomeRepository;
    private final DocteurService docteurService;

    @Autowired
    public DocteurController(PatientRepository patientRepository, PatientSymptomeRepository patientSymptomeRepository, DocteurService docteurService) {
        this.patientRepository = patientRepository;
        this.patientSymptomeRepository = patientSymptomeRepository;
        this.docteurService = docteurService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/form-consultation")
    public String getFormConsultation(Model model) {
        List<Patient> patients = patientRepository.findAll();

        model.addAttribute("patients", patients);
        model.addAttribute("formConsultation", new FormConsultation());

        return "pages/formConsultation";
    }

    @PostMapping("/submit-consultation")
    @Transactional
    public String submitConsultation(@ModelAttribute FormConsultation formConsultation, Model model) throws Exception {
        // Insert données dans la table patient_dent
        File fileData = File.createTempFile("uploaded-", formConsultation.getFile().getOriginalFilename());
        formConsultation.getFile().transferTo(fileData);

        LocalDateTime dateConsultation = formConsultation.getDateConsultationT();
        Long idPatient = formConsultation.getIdPatient();

        List<PatientSymptome> patientDentList = ExcelParser.getPatientSymptomeFromExcel(fileData, idPatient, dateConsultation);
        patientSymptomeRepository.saveAll(patientDentList);

        // Diagnostique
        assert patientRepository.findById(idPatient).isPresent();
        Patient patient = patientRepository.findById(idPatient).get();

        Diagnostique diagnostique = docteurService.getDiagnostique(patient, dateConsultation);
        model.addAttribute("diagnostique", diagnostique);

        return "pages/diagnostique";
    }
}