package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.VehiculeDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMapper mapper;

    @Override
    public VehiculeDto create(VehiculeDto vehicule) {
        Vehicule vehiculeEntity = mapper.toEntity(vehicule);
        if (vehiculeEntity.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        vehiculeEntity.setAgence(resolveAgence(vehiculeEntity.getAgence()));
        return mapper.toDto(vehiculeRepository.save(vehiculeEntity));
    }

    @Override
    public VehiculeDto findById(Long id) {
        Vehicule vehiculeEntity = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        return mapper.toDto(vehiculeEntity);
    }

    @Override
    public List<VehiculeDto> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehiculeDto update(VehiculeDto vehicule) {
        Vehicule vehiculeEntity = mapper.toEntity(vehicule);
        if (vehiculeEntity.getIdVehicule() == null) {
            throw new IllegalArgumentException("Un véhicule à modifier doit avoir un identifiant");
        }
        Vehicule vehiculeExistant = findByIdEntity(vehiculeEntity.getIdVehicule());
        vehiculeExistant.setImmatriculation(vehiculeEntity.getImmatriculation());
        vehiculeExistant.setMarque(vehiculeEntity.getMarque());
        vehiculeExistant.setModele(vehiculeEntity.getModele());
        vehiculeExistant.setCategorie(vehiculeEntity.getCategorie());
        vehiculeExistant.setTarifJournalier(vehiculeEntity.getTarifJournalier());
        vehiculeExistant.setStatut(vehiculeEntity.getStatut());
        if (vehiculeEntity.getAgence() != null) {
            vehiculeExistant.setAgence(resolveAgence(vehiculeEntity.getAgence()));
        }
        return mapper.toDto(vehiculeRepository.save(vehiculeExistant));
    }


    @Override
    public VehiculeDto affecterAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = findByIdEntity(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    // Atelier6
    @Override
    @Transactional
    public VehiculeDto affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findByIdEntity(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().add(equipement);
        equipement.getVehicules().add(vehicule);
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    private Vehicule findByIdEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Agence resolveAgence(Agence agence) {
        if (agence == null || agence.getIdAgence() == null) {
            return null;
        }
        return agenceRepository.findById(agence.getIdAgence())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agence.getIdAgence()));
    }

    //sm10 - Liste des véhicules associés à un contrat
    @Override
    public List<VehiculeDto> findVehiculesByContratId(Long idContrat) {
        return vehiculeRepository.findByReservationsContratIdContrat(idContrat).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    //sm10 -m
    @Override
    public List<VehiculeDto> findVehiculesByMaintenanceId(Long idMaintenance) {
        return vehiculeRepository.findByMaintenanceId(idMaintenance).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }
}
