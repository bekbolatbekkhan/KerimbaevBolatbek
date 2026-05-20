package com.lab.KerimbaevBolatbek.dto.response;

import com.lab.KerimbaevBolatbek.entity.enums.KerimbaevBolatbekRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KerimbaevBolatbekUserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String avatarUrl;
    private KerimbaevBolatbekRole role;
    private LocalDateTime createdAt;
}