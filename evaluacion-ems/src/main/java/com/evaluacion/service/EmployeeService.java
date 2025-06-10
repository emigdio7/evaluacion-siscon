package com.evaluacion.service;

import com.evaluacion.dto.EmployeeDTO;
import com.evaluacion.entity.Employee;
import com.evaluacion.exception.ResourceNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> findAll();
    Employee findById(Long id);
    List<Employee> saveAll(List<EmployeeDTO> employees);
    void deleteById(Long id) throws ResourceNotFoundException;
    Employee update(EmployeeDTO employeeDTO, Long id) throws ResourceNotFoundException;
}
