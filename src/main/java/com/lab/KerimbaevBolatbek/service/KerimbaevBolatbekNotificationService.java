package com.lab.KerimbaevBolatbek.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KerimbaevBolatbekNotificationService {

    // ===== Async method 1: send welcome email after registration =====
    @Async("taskExecutor")
    public void sendWelcomeEmail(String email, String username) {
        log.info("[ASYNC] Sending welcome email to {} on thread {}", email, Thread.currentThread().getName());
        try {
            // simulate slow email sending
            Thread.sleep(3000);
            log.info("[ASYNC] Welcome email sent to {} for user {}", email, username);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Email sending interrupted: {}", e.getMessage());
        }
    }

    // ===== Async method 2: notify about loan creation =====
    @Async("taskExecutor")
    public void sendLoanNotification(String email, String bookTitle, String dueDate) {
        log.info("[ASYNC] Sending loan notification to {} on thread {}", email, Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
            log.info("[ASYNC] Loan notification sent: user={}, book={}, dueDate={}", email, bookTitle, dueDate);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ===== Async method 3: generate report with CompletableFuture =====
    @Async("taskExecutor")
    public CompletableFuture<String> generateUserActivityReport(Long userId) {
        log.info("[ASYNC] Generating activity report for user {} on thread {}", userId, Thread.currentThread().getName());
        try {
            // simulate heavy computation
            Thread.sleep(5000);
            String report = "User Activity Report for userId=" + userId +
                    "\nGenerated at: " + java.time.LocalDateTime.now() +
                    "\nTotal logins: 42\nTotal loans: 7\nTotal reviews: 3";
            log.info("[ASYNC] Report generated for user {}", userId);
            return CompletableFuture.completedFuture(report);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
    }
}