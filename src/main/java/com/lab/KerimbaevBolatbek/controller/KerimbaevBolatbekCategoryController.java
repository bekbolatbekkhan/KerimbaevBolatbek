package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekCategoryRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekCategoryResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class KerimbaevBolatbekCategoryController {

    private final KerimbaevBolatbekCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<KerimbaevBolatbekCategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekCategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<KerimbaevBolatbekCategoryResponse> create(
            @Valid @RequestBody KerimbaevBolatbekCategoryRequest request) {
        return new ResponseEntity<>(categoryService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekCategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody KerimbaevBolatbekCategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}