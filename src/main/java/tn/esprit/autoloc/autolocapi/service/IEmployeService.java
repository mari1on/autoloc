package tn.esprit.autoloc.autolocapi.service;

import java.util.List;
import tn.esprit.autoloc.autolocapi.dto.EmployeDto;

public interface IEmployeService {

    EmployeDto create(EmployeDto employe);

    EmployeDto findById(Long id);

    List<EmployeDto> findAll();

    EmployeDto update(Long id, EmployeDto employe);

    void deleteById(Long id);
}
