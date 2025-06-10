package com.evaluacion.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListDTO  {

    @NotEmpty(message = "The user list cannot be empty.")
    @Valid
    private List<EmployeeDTO> employees;
}
