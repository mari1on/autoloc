package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.dto.ClientDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IMapper mapper;

    @Override
    public ClientDto create(ClientDto client) {
        Client clientEntity = mapper.toEntity(client);
        if (clientEntity.getIdClient() != null) {
            throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        }
        return mapper.toDto(clientRepository.save(clientEntity));
    }

    @Override
    public ClientDto findById(Long id) {
        Client clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
        return mapper.toDto(clientEntity);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto update(Long id, ClientDto client) {
        Client clientEntity = mapper.toEntity(client);
        Client clientExistant = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
        clientExistant.setNom(clientEntity.getNom());
        clientExistant.setPrenom(clientEntity.getPrenom());
        clientExistant.setEmail(clientEntity.getEmail());
        clientExistant.setTelephone(clientEntity.getTelephone());
        clientExistant.setNumPermis(clientEntity.getNumPermis());
        clientExistant.setDateInscription(clientEntity.getDateInscription());
        return mapper.toDto(clientRepository.save(clientExistant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }
}
