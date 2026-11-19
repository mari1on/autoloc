package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDto {

    private Long idVehicule;

    @NotBlank
    @Size(max = 20)
    private String immatriculation;

    @NotBlank
    @Size(max = 50)
    private String marque;

    @NotBlank
    @Size(max = 50)
    private String modele;

    @NotNull
    private CategorieVehicule categorie;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal tarifJournalier;

    @NotNull
    private StatutVehicule statut;

    @Positive
    private Long idAgence;
}
