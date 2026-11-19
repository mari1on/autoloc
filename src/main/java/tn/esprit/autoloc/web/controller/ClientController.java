package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.autolocapi.dto.ClientDto;
import tn.esprit.autoloc.autolocapi.service.IClientService;

@RestController
@Tag(name = "Gestion Clients", description = "Gestion des clients")
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @Operation(description = "Récupérer tous les clients de la base de données")
    @GetMapping
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Operation(description = "Récupérer un client à partir de son identifiant")
    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(description = "Ajouter un nouveau client")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@RequestBody ClientDto client) {
        return clientService.create(client);
    }

    @Operation(description = "Modifier un client existant")
    @PutMapping("/{id}")
    public ClientDto update(@PathVariable Long id, @RequestBody ClientDto client) {
        return clientService.update(id, client);
    }

    @Operation(description = "Supprimer un client")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
