package org.docteur.docteur;

import org.docteur.docteur.models.Maladie;
import org.docteur.docteur.models.PatientSymptome;
import org.docteur.docteur.models.data.MedicamentQuantite;
import org.docteur.docteur.repositories.MaladieRepository;
import org.docteur.docteur.repositories.PatientSymptomeRepository;
import org.docteur.docteur.service.DocteurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DocteurApplicationTests {
    private final MaladieRepository maladieRepository;
    private final PatientSymptomeRepository patientSymptomeRepository;
    private final DocteurService docteurService;

    @Autowired
    DocteurApplicationTests(MaladieRepository maladieRepository, PatientSymptomeRepository patientSymptomeRepository, DocteurService docteurService) {
        this.maladieRepository = maladieRepository;
        this.patientSymptomeRepository = patientSymptomeRepository;
        this.docteurService = docteurService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testPatientSymptomeList() {
        List<PatientSymptome> patientSymptomeList = patientSymptomeRepository
                .getMaladiesByIdPatientAndDate(1L, LocalDateTime.parse("2024-01-17T06:08:32"));
        System.out.println(patientSymptomeList);
    }

    @Test
    void testMaladiePatient() {
        List<Maladie> maladieList = maladieRepository
                .getMaladiesByIdPatientAndDate(1L, LocalDateTime.parse("2024-01-17T06:08:32"));
        System.out.println(maladieList);
    }

    @Test
    void testDocteurService() {
        MedicamentQuantite medicamentQuantite = docteurService.getMedicamentSoignant(1L, LocalDateTime.parse("2024-01-17T06:08:32"));
        System.out.println(medicamentQuantite);
    }

}
