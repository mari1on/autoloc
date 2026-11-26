package tn.esprit.autoloc.autolocapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
    //sm10 Véhicules associés à un contrat
    List<Vehicule> findByReservationsContratIdContrat(Long idContrat);

    //sm10 Véhicules associés à une maintenance
    List<Vehicule> findByMaintenancesIdMaintenance(Long idMaintenance);
}
