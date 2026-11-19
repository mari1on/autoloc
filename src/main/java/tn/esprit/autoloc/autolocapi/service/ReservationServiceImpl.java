package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.ReservationDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final IMapper mapper;

    @Override
    public ReservationDto create(ReservationDto reservation) {
        Reservation reservationEntity = mapper.toEntity(reservation);
        if (reservationEntity.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        resolveAssociations(reservationEntity);
        return mapper.toDto(reservationRepository.save(reservationEntity));
    }

    @Override
    public ReservationDto findById(Long id) {
        Reservation reservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return mapper.toDto(reservationEntity);
    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto update(Long id, ReservationDto reservation) {
        Reservation reservationEntity = mapper.toEntity(reservation);
        Reservation reservationExistante = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        reservationExistante.setDateDebut(reservationEntity.getDateDebut());
        reservationExistante.setDateFin(reservationEntity.getDateFin());
        reservationExistante.setStatut(reservationEntity.getStatut());
        resolveAssociations(reservationEntity);
        reservationExistante.setClient(reservationEntity.getClient());
        reservationExistante.setVehicule(reservationEntity.getVehicule());
        reservationExistante.setContrat(reservationEntity.getContrat());
        return mapper.toDto(reservationRepository.save(reservationExistante));
    }

    private void resolveAssociations(Reservation reservation) {
        reservation.setClient(resolveClient(reservation.getClient()));
        reservation.setVehicule(resolveVehicule(reservation.getVehicule()));
        reservation.setContrat(resolveContrat(reservation.getContrat()));
    }

    private Client resolveClient(Client client) {
        if (client == null || client.getIdClient() == null) {
            return null;
        }
        return clientRepository.findById(client.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client", client.getIdClient()));
    }

    private Vehicule resolveVehicule(Vehicule vehicule) {
        if (vehicule == null || vehicule.getIdVehicule() == null) {
            return null;
        }
        return vehiculeRepository.findById(vehicule.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehicule.getIdVehicule()));
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
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }
}
