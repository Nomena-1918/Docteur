package org.docteur.docteur.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "maladies")
@Data
public class Maladie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;
}
