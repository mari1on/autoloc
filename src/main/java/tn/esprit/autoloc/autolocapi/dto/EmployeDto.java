package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;

//sm8
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDto {

    private Long idEmploye;

    @NotBlank
    @Size(max = 255)
    private String nom;

    @NotBlank
    @Size(max = 255)
    private String prenom;

    @NotNull
    private RoleEmploye role;

    @Positive
    private Long idAgence;
}
