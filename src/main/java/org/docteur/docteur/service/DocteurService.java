package org.docteur.docteur.service;

import org.docteur.docteur.models.data.MedicamentQuantite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.docteur.docteur.repositories.*;
import org.docteur.docteur.models.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocteurService {
    private final MaladieRepository maladieRepository;
    private final List<Medicament> medicamentList;
    private final PatientSymptomeRepository patientSymptomeRepository;

    @Autowired
    public DocteurService(MedicamentRepository medicamentRepository, MaladieRepository maladieRepository, PatientSymptomeRepository patientSymptomeRepository) {
        this.medicamentList = medicamentRepository.findAll();
        this.maladieRepository = maladieRepository;
        this.patientSymptomeRepository = patientSymptomeRepository;
    }

    // Liste des maladies d'un patient à une date donnée
    public List<Maladie> getMaladiesPatient(Long idPatient, LocalDateTime localDateTime) {
        return maladieRepository.getMaladiesByIdPatientAndDate(idPatient, localDateTime);
    }


    // Le medicament permettant de soigner les symptômes
    public MedicamentQuantite getMedicamentSoignant(Long idPatient, LocalDateTime localDateTime) {
        List<PatientSymptome> patientSymptomeList = patientSymptomeRepository.getMaladiesByIdPatientAndDate(idPatient, localDateTime);
        MedicamentQuantite medicamentQuantite = new MedicamentQuantite();
        List<MedicamentSymptome> medicamentSymptomeList;

        for (Medicament m : medicamentList) {
            MedicamentQuantite medicamentQuantiteTemp = medicamentQuantite;
            medicamentSymptomeList = m.getMedicamentSymptomes();
            for (int i = 0; i < patientSymptomeList.size(); i++) {
                medicamentQuantite.setMedicament();
            }
        }

        return medicamentQuantite;
    }

}
