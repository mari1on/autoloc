package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.dto.MaintenanceDto;

// Atelier6 : conversion de la maintenance affectée vers sa réponse DTO.
@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    MaintenanceDto toDto(Maintenance maintenance);
}
