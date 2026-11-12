package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.dto.EmployeDto;

//sm8
@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(target = "idAgence", source = "agence.idAgence")
    EmployeDto toDto(Employe employe);

    @Mapping(target = "agence", source = "idAgence")
    Employe toEntity(EmployeDto employeDto);

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
