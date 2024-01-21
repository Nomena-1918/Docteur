package org.docteur.docteur.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public MedicamentSymptome(Symptome mauxDeVentre, int i) {
        this.symptome = mauxDeVentre;
        this.effet = i;
    }

    public MedicamentSymptome() {

    }

}
