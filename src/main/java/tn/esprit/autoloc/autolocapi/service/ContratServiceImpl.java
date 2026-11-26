
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.dto.ContratDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IPaiementRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final IPaiementRepository paiementRepository;
    private final IMapper mapper;

    @Override
    public ContratDto create(ContratDto contrat) {
        Contrat contratEntity = mapper.toEntity(contrat);
        if (contratEntity.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        contratEntity.setReservation(resolveReservation(contratEntity.getReservation()));
        return toDto(contratRepository.save(contratEntity));
    }

    @Override
    public ContratDto findById(Long id) {
        Contrat contratEntity = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        return toDto(contratEntity);
    }

    @Override
    public List<ContratDto> findAll() {
        return contratRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContratDto update(Long id, ContratDto contrat) {
        Contrat contratEntity = mapper.toEntity(contrat);
        Contrat contratExistant = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        contratExistant.setDateSignature(contratEntity.getDateSignature());
        contratExistant.setMontantTotal(contratEntity.getMontantTotal());
        contratExistant.setValide(contratEntity.isValide());
        contratExistant.setReservation(resolveReservation(contratEntity.getReservation()));
        List<Paiement> paiementsExistants = new ArrayList<>(contratExistant.getPaiements());
        List<Paiement> nouveauxPaiements = contratEntity.getPaiements() == null
                ? new ArrayList<>()
                : contratEntity.getPaiements();
        Set<Long> idsPaiementsConserves = nouveauxPaiements.stream()
                .map(Paiement::getIdPaiement)
                .filter(idPaiement -> idPaiement != null)
                .collect(Collectors.toCollection(HashSet::new));
        contratExistant.setPaiements(nouveauxPaiements);
        paiementsExistants.stream()
                .filter(paiement -> !idsPaiementsConserves.contains(paiement.getIdPaiement()))
                .forEach(paiementRepository::delete);
        return toDto(contratRepository.save(contratExistant));
    }

    private Reservation resolveReservation(Reservation reservation) {
        if (reservation == null || reservation.getIdReservation() == null) {
            return null;
        }
        return reservationRepository.findById(reservation.getIdReservation())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservation.getIdReservation()));
    }

    private ContratDto toDto(Contrat contrat) {
        ContratDto dto = mapper.toDto(contrat);
        if (dto.getPaiements() != null) {
            dto.getPaiements().forEach(paiement -> paiement.setIdContrat(contrat.getIdContrat()));
        }
        return dto;
    }

    //sm10 - Liste des contrats associés à un véhicule
    @Override
    public List<ContratDto> findContratsByVehiculeId(Long idVehicule) {
        return contratRepository.findByReservationVehiculeIdVehicule(idVehicule).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }
}
