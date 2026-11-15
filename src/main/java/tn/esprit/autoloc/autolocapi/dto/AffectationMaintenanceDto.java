package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Atelier6 : identifiants validés pour l'affectation d'une maintenance.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffectationMaintenanceDto {

    @NotNull
    @Positive
    private Long idMaintenance;

    @NotNull
    @Positive
    private Long idVehicule;
}
