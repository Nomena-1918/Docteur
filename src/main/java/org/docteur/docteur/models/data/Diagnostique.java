package org.docteur.docteur.models.data;

import lombok.Data;
import org.docteur.docteur.models.Maladie;
import org.docteur.docteur.models.Patient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Diagnostique {
    Patient patient;
    List<Maladie> maladieList;
    List<MedicamentQuantite> medicamentQuantiteList;
    LocalDateTime dateEtat;

    public Diagnostique(Patient patient, List<Maladie> maladieList, List<MedicamentQuantite> medicamentQuantiteList, LocalDateTime dateEtat) {
        this.patient = patient;
        this.maladieList = maladieList;
        this.medicamentQuantiteList = medicamentQuantiteList;
        this.dateEtat = dateEtat;
    }

    public Diagnostique() {
    }

    public String getDateEtatFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getDateEtat().format(formatter);
    }

    public double getTotalPrix() {
        double total = 0;
        for (MedicamentQuantite m : medicamentQuantiteList) {
            total += m.getPrixTotal();
        }
        return total;
    }

}
