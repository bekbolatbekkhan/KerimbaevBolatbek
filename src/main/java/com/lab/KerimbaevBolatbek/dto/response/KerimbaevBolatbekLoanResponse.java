package com.lab.KerimbaevBolatbek.dto.response;

import com.lab.KerimbaevBolatbek.entity.enums.KerimbaevBolatbekLoanStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class KerimbaevBolatbekLoanResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private KerimbaevBolatbekLoanStatus status;
    private Double fineAmount;
}