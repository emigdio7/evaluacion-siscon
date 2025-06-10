package com.evaluacion.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO  {


    private Long id;

    @NotEmpty(message = "the firstName field cannot be empty")
    @NotNull(message ="the firstName field cannot be null")
    private String firstName;

    private String secondName;

    @NotEmpty(message = "the paternalLastName field cannot be empty")
    @NotNull(message ="the paternalLastName field cannot be null")
    private String paternalLastName;

    @NotEmpty(message = "the maternalSurname field cannot be empty")
    @NotNull(message ="the maternalSurname field cannot be null")
    private String maternalSurname;

    @NotNull(message ="the age field cannot be null")
    private Integer age;

    @NotEmpty(message = "the gender field cannot be empty")
    @NotNull(message ="the gender field cannot be null")
    private String gender;

    @NotNull(message ="the birthdate field cannot be null")
    private LocalDate birthdate;

    @NotEmpty(message = "the position field cannot be empty")
    @NotNull(message ="the position field cannot be null")
    private String position;

}