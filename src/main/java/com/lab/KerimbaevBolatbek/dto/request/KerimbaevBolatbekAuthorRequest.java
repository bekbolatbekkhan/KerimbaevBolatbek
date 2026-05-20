package com.lab.KerimbaevBolatbek.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class KerimbaevBolatbekAuthorRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    private LocalDate birthDate;

    @Size(max = 1000)
    private String biography;

    @Size(max = 50)
    private String nationality;
}