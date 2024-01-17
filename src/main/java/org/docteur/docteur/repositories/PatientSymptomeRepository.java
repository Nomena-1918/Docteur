package org.docteur.docteur.repositories;

import org.docteur.docteur.models.PatientSymptome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface PatientSymptomeRepository extends JpaRepository<PatientSymptome, Long> {

@Query(value = """
        select ps
            from PatientSymptome ps
        where ps.dateConsultation = :localDateTime
        and ps.patient.id = :idPatient
        order by ps.symptome.id
       """)
    List<PatientSymptome> getMaladiesByIdPatientAndDate(@Param("idPatient") Long idPatient, @Param("localDateTime") LocalDateTime localDateTime);
}
