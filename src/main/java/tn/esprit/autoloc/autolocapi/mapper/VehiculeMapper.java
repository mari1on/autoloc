package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.dto.VehiculeDto;

//sm8
@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(target = "idAgence", source = "agence.idAgence")
    VehiculeDto toDto(Vehicule vehicule);

    @Mapping(target = "agence", source = "idAgence")
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    Vehicule toEntity(VehiculeDto vehiculeDto);

    //sm8
    default Agence mapAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        Agence agence = new Agence();
        agence.setIdAgence(idAgence);
        return agence;
    }
}
