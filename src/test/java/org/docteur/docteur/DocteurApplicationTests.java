package org.docteur.docteur;

import org.docteur.docteur.models.Maladie;
import org.docteur.docteur.repositories.MaladieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DocteurApplicationTests {
    private final MaladieRepository maladieRepository;

    @Autowired
    DocteurApplicationTests(MaladieRepository maladieRepository) {
        this.maladieRepository = maladieRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testMaladiePatient() {
        List<Maladie> maladieList = maladieRepository
                                    .getMaladiesByIdPatientAndDate(1L, LocalDateTime.parse("2024-01-17T06:08:32"));
        System.out.println(maladieList);
    }

}
