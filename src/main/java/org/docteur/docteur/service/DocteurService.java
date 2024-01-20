package org.docteur.docteur.service;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.docteur.docteur.models.data.Diagnostique;
import org.docteur.docteur.models.data.MedicamentQuantite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.docteur.docteur.repositories.*;
import org.docteur.docteur.models.*;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class DocteurService {
    static {
        Loader.loadNativeLibraries();
    }
    private final MaladieRepository maladieRepository;
    private final List<Medicament> medicamentList;
    private final PatientSymptomeRepository patientSymptomeRepository;
    private final MedicamentSymptomeRepository medicamentSymptomeRepository;

    @Autowired
    public DocteurService(MedicamentRepository medicamentRepository, MaladieRepository maladieRepository, PatientSymptomeRepository patientSymptomeRepository, MedicamentSymptomeRepository medicamentSymptomeRepository) {
        this.medicamentList = medicamentRepository.findAll();
        this.maladieRepository = maladieRepository;
        this.patientSymptomeRepository = patientSymptomeRepository;
        this.medicamentSymptomeRepository = medicamentSymptomeRepository;
        initMedicament(medicamentList);
    }

    private void initMedicament(List<Medicament> medicamentList) {
        for (Medicament m : medicamentList) {
            m.setMedicamentSymptomes(medicamentSymptomeRepository.findByIdMedicament(m.getId()));
        }
    }

    // Liste des maladies d'un patient à une date donnée
    public List<Maladie> getMaladiesPatient(Long idPatient, LocalDateTime localDateTime) {
        return maladieRepository.getMaladiesByIdPatientAndDate(idPatient, localDateTime);
    }


    public MedicamentQuantite getMedicamentSoignant(Long idPatient, LocalDateTime localDateTime) {
        List<PatientSymptome> patientSymptomeList = patientSymptomeRepository.getMaladiesByIdPatientAndDate(idPatient, localDateTime);
        MedicamentQuantite medicamentMoinsCher = null;
        double prixMoinsCher = Double.MAX_VALUE;

        for (Medicament m : medicamentList) {
            List<MedicamentSymptome> medicamentSymptomeList = m.getMedicamentSymptomes();
            boolean medicamentValide = true;
            MedicamentQuantite medicamentQuantite = new MedicamentQuantite();

            for (int i = 0; i < patientSymptomeList.size(); i++) {
                MedicamentSymptome medicamentSymptome = medicamentSymptomeList.get(i);
                PatientSymptome patientSymptome = patientSymptomeList.get(i);

                int quantiteMedicament = (int) Math.ceil((double) patientSymptome.getIntensite() / medicamentSymptome.getEffet());

                // Ajouter le médicament et sa quantité à la liste
                medicamentQuantite.setMedicament(m);
                if(medicamentQuantite.getQuantite() < quantiteMedicament)
                    medicamentQuantite.setQuantite(quantiteMedicament);

                // Vérifier si l'effet du médicament est suffisant pour traiter le symptôme
                if (medicamentSymptome.getEffet() == 0 && patientSymptome.getIntensite() > 0) {
                    medicamentValide = false;
                    break;  // Passer au médicament suivant
                }

            }

            // Si le médicament est valide et moins cher, le sauvegarder
            if (medicamentValide && medicamentQuantite.getPrixTotal() < prixMoinsCher) {
                prixMoinsCher = medicamentQuantite.getPrixTotal();
                medicamentMoinsCher = medicamentQuantite;
            }
        }

        return medicamentMoinsCher;
    }

//===================================
    public List<MedicamentQuantite> getListMedicamentSoignant(Long idPatient, LocalDateTime localDateTime) {
        MPSolver solver = createSolver();
        Map<Long, MPVariable> quantitesMedicaments = createVariables(solver, medicamentList);
        createConstraints(solver, quantitesMedicaments, medicamentList, idPatient, localDateTime);
        setObjective(solver, quantitesMedicaments, medicamentList);
        return getResultatsOptimization(solver, medicamentList, quantitesMedicaments);
    }

    private MPSolver createSolver() {
        return new MPSolver("MyModel", MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING);
    }

    private Map<Long, MPVariable> createVariables(MPSolver solver, List<Medicament> medicamentList) {
        Map<Long, MPVariable> quantitesMedicaments = new HashMap<>();
        for (Medicament medicament : medicamentList) {
            quantitesMedicaments.put(medicament.getId(), solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "Quantite_" + medicament.getNom()));
        }
        return quantitesMedicaments;
    }

    private void createConstraints(MPSolver solver, Map<Long, MPVariable> quantitesMedicaments, List<Medicament> medicamentList, Long idPatient, LocalDateTime localDateTime) {
        List<PatientSymptome> patientSymptomeList = patientSymptomeRepository.getMaladiesByIdPatientAndDate(idPatient, localDateTime);
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
    }

    private void setObjective(MPSolver solver, Map<Long, MPVariable> quantitesMedicaments, List<Medicament> medicamentList) {
        MPObjective objective = solver.objective();
        for (Medicament medicament : medicamentList) {
            objective.setCoefficient(quantitesMedicaments.get(medicament.getId()), medicament.getPrixUnitaire());
        }
        objective.setMinimization();
    }

    private List<MedicamentQuantite> getResultatsOptimization(MPSolver solver, List<Medicament> medicamentList, Map<Long, MPVariable> quantitesMedicaments) {
        List<MedicamentQuantite> results = new ArrayList<>();
        MPSolver.ResultStatus resultStatus = solver.solve();
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            for (Medicament medicament : medicamentList) {
                double quantity = quantitesMedicaments.get(medicament.getId()).solutionValue();
                if (quantity > 0) {
                    results.add(new MedicamentQuantite(medicament, (int) quantity));
                }
            }
        }
        return results;
    }

//===================================


    public Diagnostique getDiagnostique(Patient patient, LocalDateTime localDateTime) {
        Diagnostique diagnostique = new Diagnostique();
        diagnostique.setPatient(patient);
        diagnostique.setMaladieList(getMaladiesPatient(patient.getId(), localDateTime));
        diagnostique.setMedicamentQuantiteList(Collections.singletonList(getMedicamentSoignant(patient.getId(), localDateTime)));
        diagnostique.setDateEtat(localDateTime);
        return diagnostique;
    }

    public Diagnostique getDiagnostiqueSimplexe(Patient patient, LocalDateTime localDateTime) {
        Diagnostique diagnostique = new Diagnostique();
        diagnostique.setPatient(patient);
        diagnostique.setMaladieList(getMaladiesPatient(patient.getId(), localDateTime));
        diagnostique.setMedicamentQuantiteList(getListMedicamentSoignant(patient.getId(), localDateTime));
        diagnostique.setDateEtat(localDateTime);
        return diagnostique;
    }


}
