
package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.dto.ContratDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final IMapper mapper;

    @Override
    public ContratDto create(ContratDto contrat) {
        Contrat contratEntity = mapper.toEntity(contrat);
        if (contratEntity.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        contratEntity.setReservation(resolveReservation(contratEntity.getReservation()));
        setPaiementContrat(contratEntity);
        return mapper.toDto(contratRepository.save(contratEntity));
    }

    @Override
    public ContratDto findById(Long id) {
        Contrat contratEntity = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        return mapper.toDto(contratEntity);
    }

    @Override
    public List<ContratDto> findAll() {
        return contratRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContratDto update(Long id, ContratDto contrat) {
        Contrat contratEntity = mapper.toEntity(contrat);
        Contrat contratExistant = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        contratExistant.setDateSignature(contratEntity.getDateSignature());
        contratExistant.setMontantTotal(contratEntity.getMontantTotal());
        contratExistant.setValide(contratEntity.isValide());
        contratExistant.setReservation(resolveReservation(contratEntity.getReservation()));
        contratExistant.setPaiements(contratEntity.getPaiements() == null
                ? new ArrayList<>()
                : contratEntity.getPaiements());
        setPaiementContrat(contratExistant);
        return mapper.toDto(contratRepository.save(contratExistant));
    }

    private Reservation resolveReservation(Reservation reservation) {
        if (reservation == null || reservation.getIdReservation() == null) {
            return null;
        }
        return reservationRepository.findById(reservation.getIdReservation())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservation.getIdReservation()));
    }

    private void setPaiementContrat(Contrat contrat) {
        if (contrat.getPaiements() == null) {
            contrat.setPaiements(new ArrayList<>());
        }
        for (Paiement paiement : contrat.getPaiements()) {
            paiement.setContrat(contrat);
        }
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
