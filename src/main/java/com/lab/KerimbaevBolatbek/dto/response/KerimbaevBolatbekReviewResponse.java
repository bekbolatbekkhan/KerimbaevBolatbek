package com.lab.KerimbaevBolatbek.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KerimbaevBolatbekReviewResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookTitle;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}