package com.lab.KerimbaevBolatbek.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class KerimbaevBolatbekAuthorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String biography;
    private String nationality;
}