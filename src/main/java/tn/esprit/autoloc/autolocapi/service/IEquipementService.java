package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.EquipementDto;

public interface IEquipementService {

    EquipementDto create(EquipementDto equipement);

    EquipementDto findById(Long id);

    List<EquipementDto> findAll();

    EquipementDto update(Long id, EquipementDto equipement);

    void deleteById(Long id);
}
