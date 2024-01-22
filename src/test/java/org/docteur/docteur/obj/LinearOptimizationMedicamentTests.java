package org.docteur.docteur.obj;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;
import org.docteur.docteur.models.Medicament;
import org.docteur.docteur.models.MedicamentSymptome;
import org.docteur.docteur.models.PatientSymptome;
import org.docteur.docteur.models.Symptome;

import java.util.*;

public class LinearOptimizationMedicamentTests {

    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        solveMedicationOptimizationProblem();
    }

    public static void solveMedicationOptimizationProblem() {
        MPSolver solver = new MPSolver("MyModel", MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING);
    
        List<Medicament> medicamentList = getMedicaments();
        List<PatientSymptome> patientSymptomeList = getPatientSymptomes();
    
        // Définir les variables de décision (binaire)
        MPVariable[] medicamentVars = new MPVariable[medicamentList.size()];
        for (int i = 0; i < medicamentList.size(); i++) {
            medicamentVars[i] = solver.makeIntVar(0, Double.POSITIVE_INFINITY, "Medicament_" + (i + 1));
        }
    
        // Définir la fonction objectif (minimiser le coût total)
        MPObjective objective = solver.objective();

        for (int i = 0; i < medicamentList.size(); i++) {
            objective.setCoefficient(medicamentVars[i], medicamentList.get(i).getPrixUnitaire());
        }

        objective.setMinimization();
    
        // Définir les contraintes (traiter suffisamment chaque symptôme)
        for (PatientSymptome patientSymptome : patientSymptomeList) {
            MPConstraint symptomConstraint = solver.makeConstraint(patientSymptome.getIntensite(), Double.POSITIVE_INFINITY);
            for (int i = 0; i < medicamentList.size(); i++) {
                MedicamentSymptome medicamentSymptome = getMedicamentSymptome(medicamentList.get(i), patientSymptome.getSymptome());
                assert medicamentSymptome != null;
                symptomConstraint.setCoefficient(medicamentVars[i], medicamentSymptome.getEffet());
            }
        }
    
        // Résoudre le problème
        final MPSolver.ResultStatus resultStatus = solver.solve();
    
        // Afficher les résultats
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Solution optimale trouvée :");
    
            for (int i = 0; i < medicamentList.size(); i++) {
                if(medicamentVars[i].solutionValue() > 0){
                    System.out.println(medicamentList.get(i).getNom() + ": " +medicamentVars[i].solutionValue() );
                }
            }
    
            System.out.println("Coût total : " + Math.round(objective.value()));
        } else {
            System.err.println("Aucune solution optimale trouvée.");
        }
    }
    
    private static MedicamentSymptome getMedicamentSymptome(Medicament medicament, Symptome symptome) {
        for (MedicamentSymptome medicamentSymptome : medicament.getMedicamentSymptomes()) {
            if (Objects.equals(medicamentSymptome.getSymptome().getId(), symptome.getId())) {
                return medicamentSymptome;
            }
        }
        return null;
    }
    

    public static List<Medicament> getMedicaments() {
        List<Medicament> medicaments = new ArrayList<>();

        // Symptomes fictifs
        Symptome toux = new Symptome(1, "Toux");
        Symptome fievre = new Symptome(2, "Fièvre");
        Symptome morve = new Symptome(3, "Morve");
        Symptome mauxDeTete = new Symptome(4, "Maux de tête");
        Symptome mauxDeVentre = new Symptome(5, "Maux de ventre");
        Symptome selle = new Symptome(6, "Selle");
        Symptome affaiblissement = new Symptome(7, "Affaiblissement");


        // Données fictives pour MedicamentSymptome
        List<MedicamentSymptome> medicamentSymptome1 = List.of(
            new MedicamentSymptome(toux,2),
            new MedicamentSymptome(fievre,1),
            new MedicamentSymptome(morve,3),
            new MedicamentSymptome(mauxDeTete,2),
            new MedicamentSymptome(mauxDeVentre,9),
            new MedicamentSymptome(selle,10),
            new MedicamentSymptome(affaiblissement,1)
        );

        List<MedicamentSymptome> medicamentSymptome2 = List.of(
            new MedicamentSymptome(toux,2),
            new MedicamentSymptome(fievre,6),
            new MedicamentSymptome(morve, 2),
            new MedicamentSymptome(mauxDeTete,10),
            //new MedicamentSymptome(mauxDeVentre,0),
            //new MedicamentSymptome(selle,0),
            new MedicamentSymptome(affaiblissement,2)
        );

        List<MedicamentSymptome> medicamentSymptome3 = List.of(
            new MedicamentSymptome(toux,7),
            new MedicamentSymptome(fievre,9),
            new MedicamentSymptome(morve, 6),
            new MedicamentSymptome(mauxDeTete,9),
            new MedicamentSymptome(mauxDeVentre,0),
            new MedicamentSymptome(selle, 1),
            new MedicamentSymptome(affaiblissement, 6)
        );

        List<MedicamentSymptome> medicamentSymptome4 = List.of(
            new MedicamentSymptome(toux,10),
            new MedicamentSymptome(fievre,5),
            new MedicamentSymptome(morve,7),
            new MedicamentSymptome(mauxDeTete,7),
            new MedicamentSymptome(mauxDeVentre,1),
            new MedicamentSymptome(selle, 3),
            new MedicamentSymptome(affaiblissement,4)
        );

        List<MedicamentSymptome> medicamentSymptome5 = List.of(
            new MedicamentSymptome(toux,-9),
            new MedicamentSymptome(fievre,2),
            new MedicamentSymptome(morve,3),
            new MedicamentSymptome(mauxDeTete,2),
            //new MedicamentSymptome(mauxDeVentre,0),
            new MedicamentSymptome(selle,1),
            new MedicamentSymptome(affaiblissement,10)
        );

        List<MedicamentSymptome> medicamentSymptome6 = List.of(
            new MedicamentSymptome(toux,4),
            new MedicamentSymptome(fievre,6),
            new MedicamentSymptome(morve,5),
            new MedicamentSymptome(mauxDeTete,4),
            new MedicamentSymptome(mauxDeVentre,4),
            new MedicamentSymptome(selle,9),
            new MedicamentSymptome(affaiblissement,2)
        );

        // Ajout des médicaments avec MedicamentSymptome fictif
        medicaments.add(new Medicament(1, "Smecta", 9000, medicamentSymptome1));
        medicaments.add(new Medicament(2, "Doliprane", 8000, medicamentSymptome2));
        medicaments.add(new Medicament(3, "Fervex", 11000, medicamentSymptome3));
        medicaments.add(new Medicament(4, "Sirop", 12000, medicamentSymptome4));
        medicaments.add(new Medicament(5, "Vitamine", 4000, medicamentSymptome5));
        medicaments.add(new Medicament(6, "Antibiotique", 10000, medicamentSymptome6));

        // Ajoutez autant de médicaments que nécessaire avec des données fictives

        return medicaments;
    }

    public static List<PatientSymptome> getPatientSymptomes() {
        List<PatientSymptome> patientSymptomes = new ArrayList<>();

        // Données fictives pour les symptômes du patient
        Symptome toux = new Symptome(1, "Toux");
        Symptome fievre = new Symptome(2, "Fièvre");
        Symptome morve = new Symptome(3, "Morve");
        Symptome mauxDeTete = new Symptome(4, "Maux de tête");
        //Symptome mauxDeVentre = new Symptome(5, "Maux de ventre");
        //Symptome selle = new Symptome(6, "Selles");
        Symptome affaiblissement = new Symptome(7, "Affaiblissement");

        // Ajout des symptômes pour le patient
        patientSymptomes.add(new PatientSymptome(fievre, 5));
        patientSymptomes.add(new PatientSymptome(toux, 7));
        patientSymptomes.add(new PatientSymptome(morve, 8));
        patientSymptomes.add(new PatientSymptome(mauxDeTete, 7));
        //patientSymptomes.add(new PatientSymptome(mauxDeVentre, 0));
        //patientSymptomes.add(new PatientSymptome(selle, 0));
        patientSymptomes.add(new PatientSymptome(affaiblissement, 9));

        // Ajoutez autant de symptômes que nécessaire avec des données fictives

        return patientSymptomes;
    } 
}
