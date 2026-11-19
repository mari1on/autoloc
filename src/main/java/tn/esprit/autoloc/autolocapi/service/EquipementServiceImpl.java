package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.dto.EquipementDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final IMapper mapper;

    @Override
    public EquipementDto create(EquipementDto equipement) {
        Equipement equipementEntity = mapper.toEntity(equipement);
        if (equipementEntity.getIdEquipement() != null) {
            throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant");
        }
        return mapper.toDto(equipementRepository.save(equipementEntity));
    }

    @Override
    public EquipementDto findById(Long id) {
        Equipement equipementEntity = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        return mapper.toDto(equipementEntity);
    }

    @Override
    public List<EquipementDto> findAll() {
        return equipementRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EquipementDto update(Long id, EquipementDto equipement) {
        Equipement equipementEntity = mapper.toEntity(equipement);
        Equipement equipementExistant = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        equipementExistant.setLibelle(equipementEntity.getLibelle());
        return mapper.toDto(equipementRepository.save(equipementExistant));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }
}
