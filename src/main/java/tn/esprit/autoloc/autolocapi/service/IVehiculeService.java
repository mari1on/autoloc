package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.VehiculeDto;

public interface IVehiculeService {

    VehiculeDto create(VehiculeDto vehicule);

    VehiculeDto findById(Long id);

    List<VehiculeDto> findAll();

    VehiculeDto update(VehiculeDto vehicule);

    VehiculeDto affecterAgence(Long idVehicule, Long idAgence);

    VehiculeDto affecterEquipement(Long idVehicule, Long idEquipement);

    //sm10 - Liste des véhicules associés à un contrat
    List<VehiculeDto> findVehiculesByContratId(Long idContrat);

    //sm10 - Liste des véhicules associés à une maintenance
    List<VehiculeDto> findVehiculesByMaintenanceId(Long idMaintenance);

    void deleteById(Long id);
}
