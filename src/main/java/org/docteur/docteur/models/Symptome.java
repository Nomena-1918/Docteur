package org.docteur.docteur.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "symptomes")
@Data
public class Symptome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;
}
