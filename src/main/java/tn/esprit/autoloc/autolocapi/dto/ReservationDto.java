package tn.esprit.autoloc.autolocapi.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Long idClient;
    private Long idVehicule;
    private Long idContrat;
}
