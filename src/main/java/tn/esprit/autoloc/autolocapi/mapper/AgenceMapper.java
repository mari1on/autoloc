package tn.esprit.autoloc.autolocapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.dto.AgenceDto;

//sm8
@Mapper(componentModel = "spring", uses = EmployeMapper.class)
public interface AgenceMapper {

    @Mapping(target = "employes", source = "employes")
    AgenceDto toDto(Agence agence);

    @Mapping(target = "employes", ignore = true)
    Agence toEntity(AgenceDto agenceDto);
}
