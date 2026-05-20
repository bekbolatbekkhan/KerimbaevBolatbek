package com.lab.KerimbaevBolatbek.mapper;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekAuthorRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekAuthorResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekAuthor;
import org.springframework.stereotype.Component;

@Component
public class KerimbaevBolatbekAuthorMapper {

    public KerimbaevBolatbekAuthorResponse toResponse(KerimbaevBolatbekAuthor entity) {
        KerimbaevBolatbekAuthorResponse response = new KerimbaevBolatbekAuthorResponse();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setBirthDate(entity.getBirthDate());
        response.setBiography(entity.getBiography());
        response.setNationality(entity.getNationality());
        return response;
    }

    public KerimbaevBolatbekAuthor toEntity(KerimbaevBolatbekAuthorRequest request) {
        KerimbaevBolatbekAuthor entity = new KerimbaevBolatbekAuthor();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setBiography(request.getBiography());
        entity.setNationality(request.getNationality());
        return entity;
    }

    public void updateEntity(KerimbaevBolatbekAuthor entity, KerimbaevBolatbekAuthorRequest request) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(request.getBirthDate());
        entity.setBiography(request.getBiography());
        entity.setNationality(request.getNationality());
    }
}