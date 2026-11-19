package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper mapper;

    @Override
    public MaintenanceDto create(MaintenanceDto maintenance) {
        Maintenance maintenanceEntity = mapper.toEntity(maintenance);
        if (maintenanceEntity.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        return mapper.toDto(maintenanceRepository.save(maintenanceEntity));
    }

    @Override
    public MaintenanceDto findById(Long id) {
        Maintenance maintenanceEntity = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        return mapper.toDto(maintenanceEntity);
    }

    @Override
    public List<MaintenanceDto> findAll() {
        return maintenanceRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceDto update(Long id, MaintenanceDto maintenance) {
        Maintenance maintenanceEntity = mapper.toEntity(maintenance);
        Maintenance maintenanceExistante = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        maintenanceExistante.setDateDebut(maintenanceEntity.getDateDebut());
        maintenanceExistante.setDateFin(maintenanceEntity.getDateFin());
        maintenanceExistante.setDescription(maintenanceEntity.getDescription());
        if (maintenanceEntity.getVehicule() != null && maintenanceEntity.getVehicule().getIdVehicule() != null) {
            Vehicule vehicule = vehiculeRepository.findById(maintenanceEntity.getVehicule().getIdVehicule())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vehicule", maintenanceEntity.getVehicule().getIdVehicule()));
            maintenanceExistante.setVehicule(vehicule);
        } else {
            maintenanceExistante.setVehicule(null);
        }
        return mapper.toDto(maintenanceRepository.save(maintenanceExistante));
    }

    // Atelier6
    @Override
    @Transactional
    public MaintenanceDto affecterVehicule(Long idMaintenance, Long idVehicule) {
        Maintenance maintenance = maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", idMaintenance));
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        maintenance.setVehicule(vehicule);
        return mapper.toDto(maintenanceRepository.save(maintenance));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }
}
