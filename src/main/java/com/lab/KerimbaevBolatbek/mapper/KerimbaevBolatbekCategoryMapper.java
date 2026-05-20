package com.lab.KerimbaevBolatbek.mapper;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekCategoryRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekCategoryResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekCategory;
import org.springframework.stereotype.Component;

@Component
public class KerimbaevBolatbekCategoryMapper {

    public KerimbaevBolatbekCategoryResponse toResponse(KerimbaevBolatbekCategory entity) {
        KerimbaevBolatbekCategoryResponse response = new KerimbaevBolatbekCategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public KerimbaevBolatbekCategory toEntity(KerimbaevBolatbekCategoryRequest request) {
        KerimbaevBolatbekCategory entity = new KerimbaevBolatbekCategory();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public void updateEntity(KerimbaevBolatbekCategory entity, KerimbaevBolatbekCategoryRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }
}