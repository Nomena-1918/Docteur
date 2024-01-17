package org.docteur.docteur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.docteur.docteur.repositories.*;
import org.docteur.docteur.models.*;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocteurService {
    private final MedicamentRepository medicamentRepository;
    private final MaladieRepository maladieRepository;

    @Autowired
    public DocteurService(MedicamentRepository medicamentRepository, MaladieRepository maladieRepository) {
        this.medicamentRepository = medicamentRepository;
        this.maladieRepository = maladieRepository;
    }

    public List<Maladie> getMaladiesPatient(Patient patient, LocalDateTime localDateTime) {
        
    }

    /*
    public Medicament getMedicamentSoignant(Patient patient, LocalDateTime localDateTime) {
        List
    }*/
}
