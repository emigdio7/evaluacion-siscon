package com.evaluacion.service.impl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.evaluacion.dto.EmployeeDTO;
import com.evaluacion.entity.Employee;
import com.evaluacion.exception.ResourceNotFoundException;
import com.evaluacion.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setAge(30);
        employee.setGender("H");
        employee.setPosition("Developer");
        employee.setFirstName("Luis");
        employee.setBirthdate(LocalDate.of(1993, 5, 15));
        employee.setMaternalSurname("Martinez");
        employee.setPaternalLastName("Lopez");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setAge(30);
        employeeDTO.setGender("H");
        employeeDTO.setPosition("Developer");
        employeeDTO.setFirstName("Luis Updated");
        employeeDTO.setBirthdate(LocalDate.of(1993, 5, 15));
        employeeDTO.setMaternalSurname("Martinez");
        employeeDTO.setPaternalLastName("Lopez");
    }

    @Test
    void deleteByIdDeleteSuccessfull() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteById(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteById(1L);
        });

        assertEquals("Error: Employee not found in the database", exception.getMessage());
    }


    @Test
    void updateUpdateSuccessfull() throws ResourceNotFoundException {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = employeeService.update(employeeDTO, 1L);

        assertNotNull(updatedEmployee);
        assertEquals(employeeDTO.getId(), updatedEmployee.getId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateThrowException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.update(employeeDTO, 1L);
        });

        assertEquals("Error: Employee not found in the database", exception.getMessage());
    }


    @Test
    void saveAllSaveSuccessfull() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employeeDTOList.add(employeeDTO);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        when(employeeRepository.saveAll(anyList())).thenReturn(employeeList);

        List<Employee> savedEmployees = employeeService.saveAll(employeeDTOList);

        assertNotNull(savedEmployees);
        assertEquals(1, savedEmployees.size());
        verify(employeeRepository, times(1)).saveAll(anyList());
    }

    @Test
    void saveAllReturnEmptyList() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        List<Employee> savedEmployees = employeeService.saveAll(employeeDTOList);

        assertNotNull(savedEmployees);
        assertTrue(savedEmployees.isEmpty());
        verify(employeeRepository, times(1)).saveAll(anyList());
    }

    @Test
    void findAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<EmployeeDTO> employees = employeeService.findAll();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void findByIdWhenExists() {
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee emp = employeeService.findById(id);

        assertNotNull(emp);
        assertEquals(id, emp.getId());
        assertEquals(emp.getFirstName(), "Luis");
    }

    @Test
    void findByIdWhenNotFound() {
        Long idEmployee = 2L;
        when(employeeRepository.findById(idEmployee)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.findById(idEmployee))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Employee not found with id: " + idEmployee);
    }



}