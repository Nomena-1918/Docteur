package org.docteur.docteur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.docteur.docteur.models.MedicamentSymptome;

import java.util.List;

@Repository
public interface MedicamentSymptomeRepository extends JpaRepository<MedicamentSymptome, Long> {
    @Query("select ms from MedicamentSymptome ms where ms.medicament.id = :idMedicament")
    List<MedicamentSymptome> findByIdMedicament(@Param("idMedicament") Long idMedicament);
}
