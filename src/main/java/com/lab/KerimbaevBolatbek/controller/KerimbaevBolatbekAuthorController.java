package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekAuthorRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekAuthorResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class KerimbaevBolatbekAuthorController {

    private final KerimbaevBolatbekAuthorService authorService;

    @GetMapping
    public ResponseEntity<List<KerimbaevBolatbekAuthorResponse>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekAuthorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<KerimbaevBolatbekAuthorResponse> create(
            @Valid @RequestBody KerimbaevBolatbekAuthorRequest request) {
        return new ResponseEntity<>(authorService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekAuthorResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody KerimbaevBolatbekAuthorRequest request) {
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}