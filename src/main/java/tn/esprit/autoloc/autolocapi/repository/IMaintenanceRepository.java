package tn.esprit.autoloc.autolocapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    //sm10 - Maintenances associées à un véhicule
    List<Maintenance> findByVehiculeIdVehicule(Long idVehicule);
}
