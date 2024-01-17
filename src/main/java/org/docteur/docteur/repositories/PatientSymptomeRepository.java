package org.docteur.docteur.repositories;

import org.docteur.docteur.models.PatientSymptome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientSymptomeRepository extends JpaRepository<PatientSymptome, Long> {
}
