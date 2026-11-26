package tn.esprit.autoloc.autolocapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;

public interface IContratRepository
        extends JpaRepository<Contrat, Long> {
    //sm10 - Contrats associés à un véhicule
    List<Contrat> findByReservationVehiculeIdVehicule(Long idVehicule);

    Optional<Contrat> findByPaiementsIdPaiement(Long idPaiement);
}
