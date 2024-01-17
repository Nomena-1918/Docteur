package org.docteur.docteur.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medicament_symptomes")
@Data
public class MedicamentSymptome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_medicament")
    private Medicament medicament;

    @ManyToOne
    @JoinColumn(name = "id_symptome")
    private Symptome symptome;

    @Column(name = "effet")
    private Integer effet;
}
