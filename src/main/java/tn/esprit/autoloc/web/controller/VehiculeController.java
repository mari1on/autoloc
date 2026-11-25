package tn.esprit.autoloc.web.controller;

import java.util.List;

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
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

@RestController
@Tag(name = "Gestion Véhicules", description = "Gestion des véhicules")
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(description = "Récupérer tous les véhicules de la base de données")
    @GetMapping
    public List<VehiculeDto> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(description = "Récupérer un véhicule à partir de son identifiant")
    @GetMapping("/{id}")
    public VehiculeDto findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(description = "Ajouter un nouveau véhicule")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDto create(@Valid @RequestBody VehiculeDto vehiculeDto) {
        return vehiculeService.create(vehiculeDto);
    }

    @Operation(description = "Modifier un véhicule existant")
    @PutMapping
    public VehiculeDto update(@Valid @RequestBody VehiculeDto vehiculeDto) {
        return vehiculeService.update(vehiculeDto);
    }

    @Operation(description = "Affecter un véhicule à une agence")
    @PutMapping("/{id}/agence/{idAgence}")
    public VehiculeDto affecterAgence(@PathVariable Long id, @PathVariable Long idAgence) {
        return vehiculeService.affecterAgence(id, idAgence);
    }

    // Atelier6
    @Operation(description = "Affecter un équipement existant à un véhicule")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDto affecterEquipement(
            @PathVariable Long idVehicule,
            @PathVariable Long idEquipement
    ) {
        return vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    //sm10 - Afficher les véhicules associés à un contrat
    @GetMapping("/contrat/{idContrat}")
    public List<VehiculeDto> findVehiculesByContratId(@PathVariable Long idContrat) {
        return vehiculeService.findVehiculesByContratId(idContrat);
    }

    //sm10 - Afficher les véhicules associés à une maintenance
    @GetMapping("/maintenance/{idMaintenance}")
    public List<VehiculeDto> findVehiculesByMaintenanceId(@PathVariable Long idMaintenance) {
        return vehiculeService.findVehiculesByMaintenanceId(idMaintenance);
    }

    @Operation(description = "Supprimer un véhicule")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
