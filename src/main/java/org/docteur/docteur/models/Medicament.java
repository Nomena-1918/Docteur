package org.docteur.docteur.models;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medicaments")
@Data
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @OneToMany(mappedBy = "medicament", fetch = FetchType.EAGER)
    @OrderBy("symptome.id")
    private List<MedicamentSymptome> medicamentSymptomes;
}
