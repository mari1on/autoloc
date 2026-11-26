package tn.esprit.autoloc.autolocapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
    //sm10 Véhicules associés à un contrat
    List<Vehicule> findByReservationsContratIdContrat(Long idContrat);


  //sql
  //@Query(value = """
    //        SELECT v.*
      //      FROM vehicule v
        //    INNER JOIN maintenance m ON m.vehicule_id_vehicule = v.id_vehicule
          //  WHERE m.id_maintenance = :idMaintenance
           // """, nativeQuery = true)

    //sm10 -m
    //jpql
    @Query("""
            SELECT DISTINCT v
            FROM Vehicule v
            JOIN v.maintenances m
            WHERE m.idMaintenance = :idMaintenance
            """)
    List<Vehicule> findByMaintenanceId(@Param("idMaintenance") Long idMaintenance);
}
