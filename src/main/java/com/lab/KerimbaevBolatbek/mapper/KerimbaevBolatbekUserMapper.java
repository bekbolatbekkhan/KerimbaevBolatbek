package com.lab.KerimbaevBolatbek.mapper;

import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekUserResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekUser;
import org.springframework.stereotype.Component;

@Component
public class KerimbaevBolatbekUserMapper {

    public KerimbaevBolatbekUserResponse toResponse(KerimbaevBolatbekUser entity) {
        KerimbaevBolatbekUserResponse response = new KerimbaevBolatbekUserResponse();
        response.setId(entity.getId());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setAvatarUrl(entity.getAvatarUrl());
        response.setRole(entity.getRole());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}