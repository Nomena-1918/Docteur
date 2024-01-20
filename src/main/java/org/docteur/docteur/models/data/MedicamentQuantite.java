package org.docteur.docteur.models.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.docteur.docteur.models.Medicament;

@Data
public class MedicamentQuantite {
    private Medicament medicament;
    private int quantite;

    public Double getPrixTotal() {
        return medicament.getPrixUnitaire() * quantite;
    }
    public MedicamentQuantite() {
    }

    public MedicamentQuantite(Medicament medicament, int quantite) {
        this.medicament = medicament;
        this.quantite = quantite;
    }
}
