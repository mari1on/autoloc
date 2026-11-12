package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//sm8
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDto {

    private Long idAgence;

    @NotBlank
    @Size(max = 255)
    private String nom;

    @NotBlank
    @Size(max = 255)
    private String ville;

    @NotBlank
    @Size(max = 255)
    private String adresse;

    @NotBlank
    @Size(max = 255)
    private String telephone;

    private List<EmployeDto> employes = new ArrayList<>();
}
