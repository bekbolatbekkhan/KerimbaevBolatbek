package com.lab.KerimbaevBolatbek.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KerimbaevBolatbekAuthResponse {
    private String token;
    private String username;
    private String role;
}