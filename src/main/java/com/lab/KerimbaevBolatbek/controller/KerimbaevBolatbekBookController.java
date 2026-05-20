package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekBookRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekBookResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class KerimbaevBolatbekBookController {

    private final KerimbaevBolatbekBookService bookService;

    // ===== MAIN ENDPOINT: pagination + sorting + search + filtering =====
    // Example: GET /api/books?page=0&size=10&sort=title,asc&search=harry&categoryId=1
    @GetMapping
    public ResponseEntity<Page<KerimbaevBolatbekBookResponse>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(bookService.getAll(search, categoryId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekBookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping
    public ResponseEntity<KerimbaevBolatbekBookResponse> create(
            @Valid @RequestBody KerimbaevBolatbekBookRequest request) {
        return new ResponseEntity<>(bookService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekBookResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody KerimbaevBolatbekBookRequest request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}