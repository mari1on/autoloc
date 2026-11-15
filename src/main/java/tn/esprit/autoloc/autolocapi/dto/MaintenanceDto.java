package tn.esprit.autoloc.autolocapi.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Atelier6 : DTO retourné après l'affectation d'une maintenance.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDto {

    private Long idMaintenance;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    private Long idVehicule;
}
