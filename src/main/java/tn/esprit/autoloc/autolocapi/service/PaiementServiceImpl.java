package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.dto.PaiementDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final IContratRepository contratRepository;
    private final IMapper mapper;

    @Override
    public PaiementDto create(PaiementDto paiement) {
        Paiement paiementEntity = mapper.toEntity(paiement);
        if (paiementEntity.getIdPaiement() != null) {
            throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant");
        }
        paiementEntity.setContrat(resolveContrat(paiementEntity.getContrat()));
        return mapper.toDto(paiementRepository.save(paiementEntity));
    }

    @Override
    public PaiementDto findById(Long id) {
        Paiement paiementEntity = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        return mapper.toDto(paiementEntity);
    }

    @Override
    public List<PaiementDto> findAll() {
        return paiementRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaiementDto update(Long id, PaiementDto paiement) {
        Paiement paiementEntity = mapper.toEntity(paiement);
        Paiement paiementExistant = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        paiementExistant.setMontant(paiementEntity.getMontant());
        paiementExistant.setDatePaiement(paiementEntity.getDatePaiement());
        paiementExistant.setModePaiement(paiementEntity.getModePaiement());
        paiementExistant.setContrat(resolveContrat(paiementEntity.getContrat()));
        return mapper.toDto(paiementRepository.save(paiementExistant));
    }

    private Contrat resolveContrat(Contrat contrat) {
        if (contrat == null || contrat.getIdContrat() == null) {
            return null;
        }
        return contratRepository.findById(contrat.getIdContrat())
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", contrat.getIdContrat()));
    }

    @Override
    public void deleteById(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement", id);
        }
        paiementRepository.deleteById(id);
    }
}
