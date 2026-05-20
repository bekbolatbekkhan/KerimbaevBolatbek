package com.lab.KerimbaevBolatbek.mapper;

import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekLoanResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekLoan;
import org.springframework.stereotype.Component;

@Component
public class KerimbaevBolatbekLoanMapper {

    public KerimbaevBolatbekLoanResponse toResponse(KerimbaevBolatbekLoan entity) {
        KerimbaevBolatbekLoanResponse response = new KerimbaevBolatbekLoanResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUser().getId());
        response.setUsername(entity.getUser().getUsername());
        response.setBookId(entity.getBook().getId());
        response.setBookTitle(entity.getBook().getTitle());
        response.setLoanDate(entity.getLoanDate());
        response.setDueDate(entity.getDueDate());
        response.setReturnDate(entity.getReturnDate());
        response.setStatus(entity.getStatus());
        response.setFineAmount(entity.getFineAmount());
        return response;
    }
}