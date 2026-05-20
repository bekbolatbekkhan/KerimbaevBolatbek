package com.lab.KerimbaevBolatbek.mapper;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekBookRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekBookResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekBook;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class KerimbaevBolatbekBookMapper {

    public KerimbaevBolatbekBookResponse toResponse(KerimbaevBolatbekBook entity) {
        KerimbaevBolatbekBookResponse response = new KerimbaevBolatbekBookResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setIsbn(entity.getIsbn());
        response.setDescription(entity.getDescription());
        response.setPublicationYear(entity.getPublicationYear());
        response.setPublisher(entity.getPublisher());
        response.setTotalCopies(entity.getTotalCopies());
        response.setAvailableCopies(entity.getAvailableCopies());
        response.setCoverImageUrl(entity.getCoverImageUrl());
        response.setFileUrl(entity.getFileUrl());

        if (entity.getCategory() != null) {
            response.setCategoryName(entity.getCategory().getName());
        }

        if (entity.getAuthors() != null) {
            response.setAuthorNames(
                    entity.getAuthors().stream()
                            .map(a -> a.getFirstName() + " " + a.getLastName())
                            .collect(Collectors.toSet())
            );
        }

        return response;
    }

    public KerimbaevBolatbekBook toEntity(KerimbaevBolatbekBookRequest request) {
        KerimbaevBolatbekBook entity = new KerimbaevBolatbekBook();
        entity.setTitle(request.getTitle());
        entity.setIsbn(request.getIsbn());
        entity.setDescription(request.getDescription());
        entity.setPublicationYear(request.getPublicationYear());
        entity.setPublisher(request.getPublisher());
        entity.setTotalCopies(request.getTotalCopies());
        entity.setAvailableCopies(request.getTotalCopies()); // initially all are available
        return entity;
    }

    public void updateEntity(KerimbaevBolatbekBook entity, KerimbaevBolatbekBookRequest request) {
        entity.setTitle(request.getTitle());
        entity.setIsbn(request.getIsbn());
        entity.setDescription(request.getDescription());
        entity.setPublicationYear(request.getPublicationYear());
        entity.setPublisher(request.getPublisher());
        entity.setTotalCopies(request.getTotalCopies());
    }
}