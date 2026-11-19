package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.AgenceDto;
import tn.esprit.autoloc.autolocapi.dto.EmployeDto;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceDto;
import tn.esprit.autoloc.autolocapi.dto.VehiculeDto;

@Mapper(componentModel = "spring")
public interface IMapper {

    @Mapping(target = "idAgence", source = "agence.idAgence")
    VehiculeDto toDto(Vehicule vehicule);

    @Mapping(target = "agence", source = "idAgence")
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
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
}
