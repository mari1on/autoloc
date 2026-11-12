package tn.esprit.autoloc.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.dto.VehiculeDto;
import tn.esprit.autoloc.autolocapi.mapper.VehiculeMapper;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

@RestController
@Tag(name = "Gestion Véhicules", description = "Gestion des véhicules")
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    //sm8
    private final VehiculeMapper vehiculeMapper;

    @Operation(description = "Récupérer tous les véhicules de la base de données")
    @GetMapping
    public List<VehiculeDto> findAll() {
        return vehiculeService.findAll().stream()
                .map(vehiculeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(description = "Récupérer un véhicule à partir de son identifiant")
    @GetMapping("/{id}")
    public VehiculeDto findById(@PathVariable Long id) {
        return vehiculeMapper.toDto(vehiculeService.findById(id));
    }

    @Operation(description = "Ajouter un nouveau véhicule")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDto create(@Valid @RequestBody VehiculeDto vehiculeDto) {
        return vehiculeMapper.toDto(vehiculeService.create(vehiculeMapper.toEntity(vehiculeDto)));
    }

    @Operation(description = "Modifier un véhicule existant")
    @PutMapping("/{id}")
    public VehiculeDto update(@PathVariable Long id, @Valid @RequestBody VehiculeDto vehiculeDto) {
        return vehiculeMapper.toDto(vehiculeService.update(id, vehiculeMapper.toEntity(vehiculeDto)));
    }
    //sm8!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //recevoir la requête HTTP
    @Operation(description = "Affecter un véhicule à une agence")
    @PutMapping("/{id}/agence/{idAgence}")
    public VehiculeDto affecterAgence(@PathVariable Long id, @PathVariable Long idAgence) {
        return vehiculeMapper.toDto(vehiculeService.affecterAgence(id, idAgence));
    }

    @Operation(description = "Supprimer un véhicule")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
