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
import org.springframework.beans.factory.annotation.Value;
import org.docteur.docteur.utils.ExcelParser;


import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DocteurApplicationTests {
    private final MaladieRepository maladieRepository;
    private final PatientSymptomeRepository patientSymptomeRepository;
    private final DocteurService docteurService;

    @Value("${custom.excel.file.location}")
    private final String excelFileLocation = "src/main/resources/file/liste-patient-symptome.xlsx";
    private final LocalDateTime dateConsultation = LocalDateTime.parse("2024-01-17T06:08:32");

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
    void testDeletePatientSymptomeFromExcelBefore() throws Exception {
        var patientList = ExcelParser.getPatientSymptomeFromExcel(excelFileLocation, 1L, dateConsultation);
        patientSymptomeRepository.deleteAll(patientList);
        System.out.println("\n==============\n"+ patientList +"\n==============\n");
    }

    @Test
    void testInsertPatientSymptomeFromExcelBefore() throws Exception {
        var patientList = ExcelParser.getPatientSymptomeFromExcel(excelFileLocation, 1L, dateConsultation);
        patientSymptomeRepository.saveAll(patientList);
        System.out.println("\n==============\n"+ patientList +"\n==============\n");
    }


    @Test
    void testPatientSymptomeList() {
        List<PatientSymptome> patientSymptomeList = patientSymptomeRepository
                .getMaladiesByIdPatientAndDate(1L, dateConsultation);
        System.out.println(patientSymptomeList);
    }

    @Test
    void testMaladiePatient() {
        List<Maladie> maladieList = maladieRepository
                .getMaladiesByIdPatientAndDate(1L, dateConsultation);
        System.out.println(maladieList);
    }

    @Test
    void testDocteurService() {
        MedicamentQuantite medicamentQuantite = docteurService.getMedicamentSoignant(1L, dateConsultation);
        System.out.println(medicamentQuantite);
    }

    @Test
    void testDeletePatientSymptomeFromExcelAfter() throws Exception {
        var patientList = ExcelParser.getPatientSymptomeFromExcel(excelFileLocation, 1L, dateConsultation);
        patientSymptomeRepository.deleteAll(patientList);
        System.out.println("\n==============\n"+ patientList +"\n==============\n");
    }

}
