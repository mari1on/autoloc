package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.ReservationDto;

public interface IReservationService {

    ReservationDto create(ReservationDto reservation);

    ReservationDto findById(Long id);

    List<ReservationDto> findAll();

    ReservationDto update(Long id, ReservationDto reservation);

    void deleteById(Long id);
}
