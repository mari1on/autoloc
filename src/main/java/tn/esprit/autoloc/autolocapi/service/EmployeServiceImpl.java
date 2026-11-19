package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.dto.EmployeDto;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.mapper.IMapper;
import tn.esprit.autoloc.autolocapi.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IMapper mapper;

    @Override
    public EmployeDto create(EmployeDto employe) {
        Employe employeEntity = mapper.toEntity(employe);
        if (employeEntity.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }
        return mapper.toDto(employeRepository.save(employeEntity));
    }

    @Override
    public EmployeDto findById(Long id) {
        Employe employeEntity = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", id));
        return mapper.toDto(employeEntity);
    }

    @Override
    public List<EmployeDto> findAll() {
        return employeRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeDto update(Long id, EmployeDto employe) {
        Employe employeEntity = mapper.toEntity(employe);
        Employe employeExistant = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", id));
        employeExistant.setNom(employeEntity.getNom());
        employeExistant.setPrenom(employeEntity.getPrenom());
        employeExistant.setRole(employeEntity.getRole());
        employeExistant.setAgence(employeEntity.getAgence());
        return mapper.toDto(employeRepository.save(employeExistant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé", id);
        }
        employeRepository.deleteById(id);
    }
}
