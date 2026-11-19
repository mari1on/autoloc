package tn.esprit.autoloc.autolocapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDto {

    private Long idAgence;

    @NotBlank(message = "Le nom de l'agence est obligatoire.")
    @Size(max = 255,min =3, message = "Le nom de l'agence doit contenir au moins 3 caractères et au plus 255 caractères.")
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
