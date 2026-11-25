package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceDto;

public interface IMaintenanceService {

    MaintenanceDto create(MaintenanceDto maintenance);

    MaintenanceDto findById(Long id);

    List<MaintenanceDto> findAll();

    MaintenanceDto update(Long id, MaintenanceDto maintenance);

    MaintenanceDto affecterVehicule(Long idMaintenance, Long idVehicule);

    //sm10 - Liste des maintenances associées à un véhicule
    List<MaintenanceDto> findMaintenancesByVehiculeId(Long idVehicule);

    void deleteById(Long id);
}
