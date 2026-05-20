package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekReviewRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekReviewResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class KerimbaevBolatbekReviewController {

    private final KerimbaevBolatbekReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<KerimbaevBolatbekReviewResponse>> getAll(
            @RequestParam(required = false) Long bookId) {
        if (bookId != null) {
            return ResponseEntity.ok(reviewService.getByBookId(bookId));
        }
        return ResponseEntity.ok(reviewService.getAll());
    }

    @PostMapping
    public ResponseEntity<KerimbaevBolatbekReviewResponse> create(
            @RequestParam Long userId,
            @Valid @RequestBody KerimbaevBolatbekReviewRequest request) {
        return new ResponseEntity<>(reviewService.create(userId, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}