package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.AgenceDto;
import tn.esprit.autoloc.autolocapi.dto.ClientDto;
import tn.esprit.autoloc.autolocapi.dto.ContratDto;
import tn.esprit.autoloc.autolocapi.dto.EmployeDto;
import tn.esprit.autoloc.autolocapi.dto.EquipementDto;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.dto.PaiementDto;
import tn.esprit.autoloc.autolocapi.dto.ReservationDto;
import tn.esprit.autoloc.autolocapi.dto.VehiculeDto;

@Mapper(componentModel = "spring")
public interface IMapper {

    @Mapping(target = "idAgence", source = "agence.idAgence")
    VehiculeDto toDto(Vehicule vehicule);

    @Mapping(target = "agence", source = "idAgence")
    //sm10 - Ignorer les associations lors du mapping DTO vers entité
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Vehicule toEntity(VehiculeDto vehiculeDto);

    @Mapping(target = "employes", source = "employes")
    AgenceDto toDto(Agence agence);

    @Mapping(target = "employes", ignore = true)
    Agence toEntity(AgenceDto agenceDto);

    @Mapping(target = "idAgence", source = "agence.idAgence")
    EmployeDto toDto(Employe employe);

    @Mapping(target = "agence", source = "idAgence")
    Employe toEntity(EmployeDto employeDto);

    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    MaintenanceDto toDto(Maintenance maintenance);

    @Mapping(target = "vehicule", source = "idVehicule")
    Maintenance toEntity(MaintenanceDto maintenanceDto);

    ClientDto toDto(Client client);

    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "idReservation", source = "reservation.idReservation")
    @Mapping(target = "paiements", source = "paiements")
    ContratDto toDto(Contrat contrat);

    @Mapping(target = "reservation", source = "idReservation")
    @Mapping(target = "paiements", source = "paiements")
    Contrat toEntity(ContratDto contratDto);

    @Mapping(target = "idContrat", source = "contrat.idContrat")
    PaiementDto toDto(Paiement paiement);

    @Mapping(target = "contrat", source = "idContrat")
    Paiement toEntity(PaiementDto paiementDto);

    EquipementDto toDto(Equipement equipement);

    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDto equipementDto);

    @Mapping(target = "idClient", source = "client.idClient")
    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    @Mapping(target = "idContrat", source = "contrat.idContrat")
    ReservationDto toDto(Reservation reservation);

    @Mapping(target = "client", source = "idClient")
    @Mapping(target = "vehicule", source = "idVehicule")
    @Mapping(target = "contrat", source = "idContrat")
    Reservation toEntity(ReservationDto reservationDto);

    default Agence mapAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        Agence agence = new Agence();
        agence.setIdAgence(idAgence);
        return agence;
    }

    default Vehicule mapVehicule(Long idVehicule) {
        if (idVehicule == null) {
            return null;
        }
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(idVehicule);
        return vehicule;
    }

    default Contrat mapContrat(Long idContrat) {
        if (idContrat == null) {
            return null;
        }
        Contrat contrat = new Contrat();
        contrat.setIdContrat(idContrat);
        return contrat;
    }

    default Client mapClient(Long idClient) {
        if (idClient == null) {
            return null;
        }
        Client client = new Client();
        client.setIdClient(idClient);
        return client;
    }

    default Reservation mapReservation(Long idReservation) {
        if (idReservation == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setIdReservation(idReservation);
        return reservation;
    }
}
