package org.docteur.docteur.repositories;

import org.docteur.docteur.models.Maladie;
import org.docteur.docteur.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaladieRepository extends JpaRepository<Maladie, Long> {
    @Query(value = """
       select id_maladie as id, nom
            from v_maladies_patient
        where date_consultation = :localDateTime
        and id_patient = :idPatient
       """, nativeQuery = true)
    List<Maladie> getMaladiesByIdPatientAndDate(@Param("idPatient") Long idPatient, @Param("localDateTime") LocalDateTime localDateTime);
}
