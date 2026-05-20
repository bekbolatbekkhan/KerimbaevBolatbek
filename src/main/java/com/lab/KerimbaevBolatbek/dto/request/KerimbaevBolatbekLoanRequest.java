package com.lab.KerimbaevBolatbek.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class KerimbaevBolatbekLoanRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}