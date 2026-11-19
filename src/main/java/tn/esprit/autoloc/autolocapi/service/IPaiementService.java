package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.PaiementDto;

public interface IPaiementService {

    PaiementDto create(PaiementDto paiement);

    PaiementDto findById(Long id);

    List<PaiementDto> findAll();

    PaiementDto update(Long id, PaiementDto paiement);

    void deleteById(Long id);
}
