package com.evaluacion.controller;

import com.evaluacion.dto.EmployeeDTO;
import com.evaluacion.dto.EmployeeListDTO;
import com.evaluacion.dto.ResponseDTO;
import com.evaluacion.exception.ResourceNotFoundException;
import com.evaluacion.service.EmployeeService;
import com.evaluacion.utils.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/employee")
@Tag(name = "Employees", description = "Management Employees")
@Validated
public class EmployeeController {
    
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }


    @Operation(summary = "Get all employees", description = "Return a list of employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Employees", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll(@RequestHeader("X-Request-ID") String requestId) {
        return Utils.buildResponse(employeeService.findAll(), "Employees retrieved successfully", HttpStatus.OK);
    }



    @Operation(summary = "Save employees", description = "Save employees in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save Employees", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid EmployeeListDTO employees, @RequestHeader("X-Request-ID") String requestId) throws MethodArgumentNotValidException {
        return Utils.buildResponse(employeeService.saveAll(employees.getEmployees()), "Employees saved successfully", HttpStatus.CREATED);
    }



    @Operation(summary = "Delete employee", description = "Delete employee in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee Deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable @Min(1) Long id, @RequestHeader("X-Request-ID") String requestId) throws ResourceNotFoundException {
        employeeService.deleteById(id);
        return Utils.buildResponse(null, "Successfully deleted employee", HttpStatus.OK);
    }



    @Operation(summary = "Update employee", description = "Update employee in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Employee", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDTO, @RequestHeader("X-Request-ID") String requestId) throws ResourceNotFoundException {
        return Utils.buildResponse(employeeService.update(employeeDTO, id), "Employee updated successfully", HttpStatus.OK);
    }



    @Operation(summary = "Find by Id employee", description = "Find employee in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Employee", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findByID(@PathVariable Long id, @RequestHeader("X-Request-ID") String requestId) throws ResourceNotFoundException {
        return Utils.buildResponse(employeeService.findById(id), "Success", HttpStatus.OK);
    }

}
