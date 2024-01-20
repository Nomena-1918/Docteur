package org.docteur.docteur;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.docteur.docteur.models.Medicament;
import org.docteur.docteur.models.MedicamentSymptome;
import org.docteur.docteur.models.PatientSymptome;
import org.docteur.docteur.repositories.MedicamentRepository;
import org.docteur.docteur.repositories.MedicamentSymptomeRepository;
import org.docteur.docteur.repositories.PatientSymptomeRepository;
import org.docteur.docteur.utils.ExcelParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class LinearOptimizationMedicamentTests {
    private final MedicamentRepository medicamentRepository;
    private final MedicamentSymptomeRepository medicamentSymptomeRepository;
    private final PatientSymptomeRepository patientSymptomeRepository;
    private final LocalDateTime dateConsultation = LocalDateTime.parse("2024-01-17T06:08:32");
    @Value("${custom.excel.file.location}")
    private final String excelFileLocation = "src/main/resources/file/liste-patient-symptome.xlsx";


    @Autowired
    public LinearOptimizationMedicamentTests(MedicamentRepository medicamentRepository, MedicamentSymptomeRepository medicamentSymptomeRepository, PatientSymptomeRepository patientSymptomeRepository) {
        this.medicamentRepository = medicamentRepository;
        this.medicamentSymptomeRepository = medicamentSymptomeRepository;
        this.patientSymptomeRepository = patientSymptomeRepository;
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
    void primaryTest() {
        Loader.loadNativeLibraries();
        // Create the linear solver with the SCIP backend.
        MPSolver solver = new MPSolver("MyModel", MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING);
        //

        // Create the variables
        Map<Long, MPVariable> quantitesMedicaments = new HashMap<>();
        List<Medicament> medicamentList = medicamentRepository.findAll();
        for (Medicament m : medicamentList) {
            m.setMedicamentSymptomes(medicamentSymptomeRepository.findByIdMedicament(m.getId()));
        }
        for (Medicament medicament : medicamentList) {
            quantitesMedicaments.put(medicament.getId(), solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "Quantite_" + medicament.getNom()));
        }
        System.out.println("Number of variables = " + solver.numVariables());
        //

        // Create linear constraints
        List<PatientSymptome> patientSymptomeList = patientSymptomeRepository.getMaladiesByIdPatientAndDate(1L, dateConsultation);
        for (PatientSymptome patientSymptome : patientSymptomeList) {
            MPConstraint constraint = solver.makeConstraint(patientSymptome.getIntensite(), Double.POSITIVE_INFINITY, "ContrainteSymptome_" + patientSymptome.getSymptome().getId());
            for (Medicament medicament : medicamentList) {
                double sumEffetSurSymptome = medicament.getMedicamentSymptomes().stream()
                        .filter(medSymptome -> medSymptome.getSymptome().equals(patientSymptome.getSymptome()))
                        .mapToDouble(MedicamentSymptome::getEffet)
                        .sum();
                constraint.setCoefficient(quantitesMedicaments.get(medicament.getId()), sumEffetSurSymptome);
            }
        }
        System.out.println("Number of constraints = " + solver.numConstraints());
        //

        // Create the objective function, 3 * x + y.
        MPObjective objective = solver.objective();
        for (Medicament medicament : medicamentList) {
            objective.setCoefficient(quantitesMedicaments.get(medicament.getId()), medicament.getPrixUnitaire());
        }
        objective.setMinimization();
        //

        // Résultats
        MPSolver.ResultStatus resultStatus = solver.solve();
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Solution trouvée !");
            for (Medicament medicament : medicamentList) {
                System.out.println("Medicament: " + medicament.getNom() + ", Quantité: " + quantitesMedicaments.get(medicament.getId()).solutionValue());
            }
        } else {
            System.out.println("Le problème n'a pas de solution optimale.");
        }
        //
    }

    @Test
    void testDeletePatientSymptomeFromExcelAfter() throws Exception {
        var patientList = ExcelParser.getPatientSymptomeFromExcel(excelFileLocation, 1L, dateConsultation);
        patientSymptomeRepository.deleteAll(patientList);
        System.out.println("\n==============\n"+ patientList +"\n==============\n");
    }

}
