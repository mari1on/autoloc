package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.ContratDto;

public interface IContratService {

    ContratDto create(ContratDto contrat);

    ContratDto findById(Long id);

    List<ContratDto> findAll();

    ContratDto update(Long id, ContratDto contrat);

    void deleteById(Long id);
}