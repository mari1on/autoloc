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

    void deleteById(Long id);
}
