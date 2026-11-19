package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.AgenceDto;

public interface IAgenceService {

    AgenceDto create(AgenceDto agence);

    AgenceDto findById(Long id);

    List<AgenceDto> findAll();

    AgenceDto update(Long id, AgenceDto agence);

    void deleteById(Long id);
}
