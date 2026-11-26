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
    @Transactional
    public MaintenanceDto create(MaintenanceDto maintenance) {
        Maintenance maintenanceEntity = mapper.toEntity(maintenance);
        if (maintenanceEntity.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        Vehicule vehicule = resolveVehicule(maintenance.getIdVehicule());
        if (vehicule == null) {
            return toDto(maintenanceRepository.save(maintenanceEntity), null);
        }
        vehicule.getMaintenances().add(maintenanceEntity);
        vehiculeRepository.save(vehicule);
        return toDto(maintenanceEntity, vehicule.getIdVehicule());
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDto findById(Long id) {
        Maintenance maintenanceEntity = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        Vehicule vehicule = findVehiculeForMaintenance(id);
        return toDto(maintenanceEntity, vehicule == null ? null : vehicule.getIdVehicule());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDto> findAll() {
        return maintenanceRepository.findAll().stream()
                .map(maintenance -> {
                    Vehicule vehicule = findVehiculeForMaintenance(maintenance.getIdMaintenance());
                    return toDto(maintenance, vehicule == null ? null : vehicule.getIdVehicule());
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MaintenanceDto update(Long id, MaintenanceDto maintenance) {
        Maintenance maintenanceEntity = mapper.toEntity(maintenance);
        Maintenance maintenanceExistante = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        maintenanceExistante.setDateDebut(maintenanceEntity.getDateDebut());
        maintenanceExistante.setDateFin(maintenanceEntity.getDateFin());
        maintenanceExistante.setDescription(maintenanceEntity.getDescription());
        Vehicule ancienVehicule = findVehiculeForMaintenance(id);
        Vehicule nouveauVehicule = resolveVehicule(maintenance.getIdVehicule());
        if (ancienVehicule != null && (nouveauVehicule == null
                || !ancienVehicule.getIdVehicule().equals(nouveauVehicule.getIdVehicule()))) {
            ancienVehicule.getMaintenances().removeIf(item -> id.equals(item.getIdMaintenance()));
        }
        if (nouveauVehicule != null && (ancienVehicule == null
                || !ancienVehicule.getIdVehicule().equals(nouveauVehicule.getIdVehicule()))) {
            nouveauVehicule.getMaintenances().add(maintenanceExistante);
        }
        return toDto(maintenanceRepository.save(maintenanceExistante),
                nouveauVehicule == null ? null : nouveauVehicule.getIdVehicule());
    }

    @Override
    @Transactional
    public MaintenanceDto affecterVehicule(Long idMaintenance, Long idVehicule) {
        Maintenance maintenance = maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", idMaintenance));
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        Vehicule ancienVehicule = findVehiculeForMaintenance(idMaintenance);
        if (ancienVehicule != null && !ancienVehicule.getIdVehicule().equals(idVehicule)) {
            ancienVehicule.getMaintenances().removeIf(item -> idMaintenance.equals(item.getIdMaintenance()));
        }
        if (ancienVehicule == null || !ancienVehicule.getIdVehicule().equals(idVehicule)) {
            vehicule.getMaintenances().add(maintenance);
        }
        return toDto(maintenanceRepository.save(maintenance), idVehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDto> findMaintenancesByVehiculeId(Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        return vehicule.getMaintenances().stream()
                .map(maintenance -> toDto(maintenance, idVehicule))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        Vehicule vehicule = findVehiculeForMaintenance(id);
        if (vehicule != null) {
            vehicule.getMaintenances().removeIf(item -> id.equals(item.getIdMaintenance()));
        }
        maintenanceRepository.delete(maintenance);
    }

    private Vehicule resolveVehicule(Long idVehicule) {
        if (idVehicule == null) {
            return null;
        }
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
    }

    private Vehicule findVehiculeForMaintenance(Long idMaintenance) {
        return vehiculeRepository.findByMaintenanceId(idMaintenance).stream()
                .findFirst()
                .orElse(null);
    }

    private MaintenanceDto toDto(Maintenance maintenance, Long idVehicule) {
        MaintenanceDto dto = mapper.toDto(maintenance);
        dto.setIdVehicule(idVehicule);
        return dto;
    }
}
