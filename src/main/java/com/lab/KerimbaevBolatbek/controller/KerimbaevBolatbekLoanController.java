package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekLoanRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekLoanResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class KerimbaevBolatbekLoanController {

    private final KerimbaevBolatbekLoanService loanService;

    @GetMapping
    public ResponseEntity<List<KerimbaevBolatbekLoanResponse>> getAll(
            @RequestParam(required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok(loanService.getByUserId(userId));
        }
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekLoanResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getById(id));
    }

    @PostMapping
    public ResponseEntity<KerimbaevBolatbekLoanResponse> create(
            @Valid @RequestBody KerimbaevBolatbekLoanRequest request) {
        return new ResponseEntity<>(loanService.createLoan(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<KerimbaevBolatbekLoanResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}