package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public PaiementDto create(PaiementDto paiement) {
        Paiement paiementEntity = mapper.toEntity(paiement);
        if (paiementEntity.getIdPaiement() != null) {
            throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant");
        }
        Contrat contrat = resolveContrat(paiement.getIdContrat());
        if (contrat == null) {
            return toDto(paiementRepository.save(paiementEntity), null);
        }
        contrat.getPaiements().add(paiementEntity);
        contratRepository.save(contrat);
        return toDto(paiementEntity, contrat.getIdContrat());
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDto findById(Long id) {
        Paiement paiementEntity = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        Contrat contrat = findContratForPaiement(id);
        return toDto(paiementEntity, contrat == null ? null : contrat.getIdContrat());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDto> findAll() {
        return paiementRepository.findAll().stream()
                .map(paiement -> {
                    Contrat contrat = findContratForPaiement(paiement.getIdPaiement());
                    return toDto(paiement, contrat == null ? null : contrat.getIdContrat());
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaiementDto update(Long id, PaiementDto paiement) {
        Paiement paiementEntity = mapper.toEntity(paiement);
        Paiement paiementExistant = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        paiementExistant.setMontant(paiementEntity.getMontant());
        paiementExistant.setDatePaiement(paiementEntity.getDatePaiement());
        paiementExistant.setModePaiement(paiementEntity.getModePaiement());
        Contrat ancienContrat = findContratForPaiement(id);
        Contrat nouveauContrat = resolveContrat(paiement.getIdContrat());
        if (ancienContrat != null && (nouveauContrat == null
                || !ancienContrat.getIdContrat().equals(nouveauContrat.getIdContrat()))) {
            ancienContrat.getPaiements().removeIf(item -> id.equals(item.getIdPaiement()));
            contratRepository.save(ancienContrat);
        }
        if (nouveauContrat != null && (ancienContrat == null
                || !ancienContrat.getIdContrat().equals(nouveauContrat.getIdContrat()))) {
            nouveauContrat.getPaiements().add(paiementExistant);
            contratRepository.save(nouveauContrat);
        } else if (nouveauContrat == null) {
            paiementRepository.save(paiementExistant);
        }
        return toDto(paiementExistant, nouveauContrat == null ? null : nouveauContrat.getIdContrat());
    }

    private Contrat resolveContrat(Long idContrat) {
        if (idContrat == null) {
            return null;
        }
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", idContrat));
    }

    private Contrat findContratForPaiement(Long idPaiement) {
        return contratRepository.findByPaiementsIdPaiement(idPaiement).orElse(null);
    }

    private PaiementDto toDto(Paiement paiement, Long idContrat) {
        PaiementDto dto = mapper.toDto(paiement);
        dto.setIdContrat(idContrat);
        return dto;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        Contrat contrat = findContratForPaiement(id);
        if (contrat != null) {
            contrat.getPaiements().removeIf(item -> id.equals(item.getIdPaiement()));
            contratRepository.save(contrat);
        }
        paiementRepository.delete(paiement);
    }
}
