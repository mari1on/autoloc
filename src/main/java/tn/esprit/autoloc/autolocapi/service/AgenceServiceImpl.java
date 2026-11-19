package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.dto.AgenceDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IMapper mapper;


    @Override
    public AgenceDto create(AgenceDto agence) {
        Agence agenceEntity = mapper.toEntity(agence);
        if (agenceEntity.getIdAgence() != null) {
            throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        return mapper.toDto(agenceRepository.save(agenceEntity));
    }

    @Override
    public AgenceDto findById(Long id) {
        Agence agenceEntity = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        return mapper.toDto(agenceEntity);
    }

    @Override
    public List<AgenceDto> findAll() {
        return agenceRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgenceDto update(Long id, AgenceDto agence) {
        Agence agenceEntity = mapper.toEntity(agence);
        Agence agenceExistante = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        agenceExistante.setNom(agenceEntity.getNom());
        agenceExistante.setVille(agenceEntity.getVille());
        agenceExistante.setAdresse(agenceEntity.getAdresse());
        agenceExistante.setTelephone(agenceEntity.getTelephone());
        return mapper.toDto(agenceRepository.save(agenceExistante));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }
}
