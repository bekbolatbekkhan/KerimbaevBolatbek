package com.lab.KerimbaevBolatbek.mapper;

import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekReviewResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekReview;
import org.springframework.stereotype.Component;

@Component
public class KerimbaevBolatbekReviewMapper {

    public KerimbaevBolatbekReviewResponse toResponse(KerimbaevBolatbekReview entity) {
        KerimbaevBolatbekReviewResponse response = new KerimbaevBolatbekReviewResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUser().getId());
        response.setUsername(entity.getUser().getUsername());
        response.setBookId(entity.getBook().getId());
        response.setBookTitle(entity.getBook().getTitle());
        response.setRating(entity.getRating());
        response.setComment(entity.getComment());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}