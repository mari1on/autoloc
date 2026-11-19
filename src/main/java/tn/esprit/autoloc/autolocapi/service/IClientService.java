package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.ClientDto;

public interface IClientService {

    ClientDto create(ClientDto client);

    ClientDto findById(Long id);

    List<ClientDto> findAll();

    ClientDto update(Long id, ClientDto client);

    void deleteById(Long id);
}
