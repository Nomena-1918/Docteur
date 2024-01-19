package org.docteur.docteur.service;

import org.docteur.docteur.models.data.Diagnostique;
import org.docteur.docteur.models.data.MedicamentQuantite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.docteur.docteur.repositories.*;
import org.docteur.docteur.models.*;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class DocteurService {
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


    public Diagnostique getDiagnostique(Patient patient, LocalDateTime localDateTime) {
        Diagnostique diagnostique = new Diagnostique();
        diagnostique.setPatient(patient);
        diagnostique.setMaladieList(getMaladiesPatient(patient.getId(), localDateTime));
        diagnostique.setMedicamentQuantiteList(Collections.singletonList(getMedicamentSoignant(patient.getId(), localDateTime)));
        diagnostique.setDateEtat(localDateTime);
        return diagnostique;
    }


}
