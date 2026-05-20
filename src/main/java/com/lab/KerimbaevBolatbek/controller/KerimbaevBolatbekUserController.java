package com.lab.KerimbaevBolatbek.controller;

import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekUserResponse;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekNotificationService;
import com.lab.KerimbaevBolatbek.service.KerimbaevBolatbekUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class KerimbaevBolatbekUserController {

    private final KerimbaevBolatbekUserService userService;
    private final KerimbaevBolatbekNotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<KerimbaevBolatbekUserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KerimbaevBolatbekUserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    @GetMapping("/{id}/report")
    public CompletableFuture<ResponseEntity<String>> getUserReport(@PathVariable Long id) {
        return notificationService.generateUserActivityReport(id)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}