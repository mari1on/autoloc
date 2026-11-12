package tn.esprit.autoloc.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import tn.esprit.autoloc.autolocapi.dto.EmployeDto;
import tn.esprit.autoloc.autolocapi.mapper.EmployeMapper;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

@RestController
@Tag(name = "Gestion Employés", description = "Gestion des employés")
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;
    //sm8
    private final EmployeMapper employeMapper;

    @Operation(description = "Récupérer tous les employés de la base de données")
    @GetMapping
    public List<EmployeDto> findAll() {
        return employeService.findAll().stream()
                .map(employeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(description = "Récupérer un employé à partir de son identifiant")
    @GetMapping("/{id}")
    public EmployeDto findById(@PathVariable Long id) {
        return employeMapper.toDto(employeService.findById(id));
    }

    @Operation(description = "Ajouter un nouvel employé")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDto create(@Valid @RequestBody EmployeDto employeDto) {
        return employeMapper.toDto(employeService.create(employeMapper.toEntity(employeDto)));
    }

    @Operation(description = "Modifier un employé existant")
    @PutMapping("/{id}")
    public EmployeDto update(@PathVariable Long id, @Valid @RequestBody EmployeDto employeDto) {
        return employeMapper.toDto(employeService.update(id, employeMapper.toEntity(employeDto)));
    }

    @Operation(description = "Supprimer un employé")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
