package com.evaluacion.service.impl;


import com.evaluacion.dto.EmployeeDTO;
import com.evaluacion.entity.Employee;
import com.evaluacion.exception.ResourceNotFoundException;
import com.evaluacion.repository.EmployeeRepository;
import com.evaluacion.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository ){
        this.employeeRepository = employeeRepository;
        this.modelMapper = new ModelMapper();

    }


    @Override
    public  List<Employee> saveAll(List<EmployeeDTO> employeesDTO) {
        LOG.debug("Saving employees: {}", employeesDTO);
        List<Employee> employeesToSave = employeesDTO.stream()
                            .map(dto -> modelMapper.map(dto,Employee.class))
                            .toList();
        return employeeRepository.saveAll(employeesToSave);
    }

    @Override
    public Employee update(EmployeeDTO employeeDTO, Long idEmp) throws ResourceNotFoundException{

        LOG.debug("Updating employee with  id {}: {}", idEmp,employeeDTO);
        Employee employeeBD = employeeRepository.findById(idEmp)
            .orElseThrow(() -> new ResourceNotFoundException("Error: Employee not found in the database"));

            
        modelMapper.map(employeeDTO, employeeBD);
        employeeBD.setId(idEmp);

        return employeeRepository.save(employeeBD);
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
            LOG.debug("Deleting employee with ID: {}", id);
            employeeRepository.findById(id)
                      .orElseThrow(() -> new ResourceNotFoundException("Error: Employee not found in the database"));
       
            employeeRepository.deleteById(id);
            LOG.debug("Deleted employee id: {}", id);
    }

    @Override
    public List<EmployeeDTO> findAll() {

        List<Employee> listEmployees= employeeRepository.findAll();
        LOG.debug("size ListEmployeesBD: {} ", listEmployees.size());

        return listEmployees.stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .toList();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }


}
