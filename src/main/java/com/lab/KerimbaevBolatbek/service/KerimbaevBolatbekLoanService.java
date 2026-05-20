package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekLoanRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekLoanResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekBook;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekLoan;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekUser;
import com.lab.KerimbaevBolatbek.entity.enums.KerimbaevBolatbekLoanStatus;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekBadRequestException;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekResourceNotFoundException;
import com.lab.KerimbaevBolatbek.mapper.KerimbaevBolatbekLoanMapper;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekBookRepository;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekLoanRepository;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KerimbaevBolatbekLoanService {

    private final KerimbaevBolatbekLoanRepository loanRepository;
    private final KerimbaevBolatbekUserRepository userRepository;
    private final KerimbaevBolatbekBookRepository bookRepository;
    private final KerimbaevBolatbekLoanMapper loanMapper;
    private final KerimbaevBolatbekNotificationService notificationService;

    public List<KerimbaevBolatbekLoanResponse> getAll() {
        log.info("Fetching all loans");
        return loanRepository.findAll().stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<KerimbaevBolatbekLoanResponse> getByUserId(Long userId) {
        log.info("Fetching loans for user {}", userId);
        return loanRepository.findByUserId(userId).stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    public KerimbaevBolatbekLoanResponse getById(Long id) {
        log.info("Fetching loan with id {}", id);
        return loanMapper.toResponse(findLoanById(id));
    }

    // ===== Issue a book (business logic!) =====
    public KerimbaevBolatbekLoanResponse createLoan(KerimbaevBolatbekLoanRequest request) {
        log.info("Creating loan: userId={}, bookId={}", request.getUserId(), request.getBookId());

        KerimbaevBolatbekUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "User with id " + request.getUserId() + " not found"));

        KerimbaevBolatbekBook book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Book with id " + request.getBookId() + " not found"));

        // business rule: check availability
        if (book.getAvailableCopies() <= 0) {
            throw new KerimbaevBolatbekBadRequestException("No available copies of this book");
        }

        // decrement available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        KerimbaevBolatbekLoan loan = KerimbaevBolatbekLoan.builder()
                .user(user)
                .book(book)
                .loanDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .status(KerimbaevBolatbekLoanStatus.ACTIVE)
                .fineAmount(0.0)
                .build();

        KerimbaevBolatbekLoan saved = loanRepository.save(loan);
        notificationService.sendLoanNotification(
                user.getEmail(),
                book.getTitle(),
                loan.getDueDate().toString()
        );
        return loanMapper.toResponse(saved);
    }

    // ===== Return a book =====
    public KerimbaevBolatbekLoanResponse returnBook(Long loanId) {
        log.info("Returning book for loan {}", loanId);

        KerimbaevBolatbekLoan loan = findLoanById(loanId);

        if (loan.getStatus() != KerimbaevBolatbekLoanStatus.ACTIVE) {
            throw new KerimbaevBolatbekBadRequestException("This loan is not active");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(KerimbaevBolatbekLoanStatus.RETURNED);

        // increment available copies
        KerimbaevBolatbekBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        KerimbaevBolatbekLoan saved = loanRepository.save(loan);
        return loanMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting loan with id {}", id);
        KerimbaevBolatbekLoan loan = findLoanById(id);
        loanRepository.delete(loan);
    }

    public KerimbaevBolatbekLoan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Loan with id " + id + " not found"));
    }
}

