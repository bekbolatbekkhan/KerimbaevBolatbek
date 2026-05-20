package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekReviewRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekReviewResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekBook;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekReview;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekUser;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekResourceNotFoundException;
import com.lab.KerimbaevBolatbek.mapper.KerimbaevBolatbekReviewMapper;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekBookRepository;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekReviewRepository;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KerimbaevBolatbekReviewService {

    private final KerimbaevBolatbekReviewRepository reviewRepository;
    private final KerimbaevBolatbekUserRepository userRepository;
    private final KerimbaevBolatbekBookRepository bookRepository;
    private final KerimbaevBolatbekReviewMapper reviewMapper;

    public List<KerimbaevBolatbekReviewResponse> getAll() {
        log.info("Fetching all reviews");
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<KerimbaevBolatbekReviewResponse> getByBookId(Long bookId) {
        log.info("Fetching reviews for book {}", bookId);
        return reviewRepository.findByBookId(bookId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    public KerimbaevBolatbekReviewResponse create(Long userId, KerimbaevBolatbekReviewRequest request) {
        log.info("Creating review: userId={}, bookId={}", userId, request.getBookId());

        KerimbaevBolatbekUser user = userRepository.findById(userId)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "User with id " + userId + " not found"));

        KerimbaevBolatbekBook book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Book with id " + request.getBookId() + " not found"));

        KerimbaevBolatbekReview review = KerimbaevBolatbekReview.builder()
                .user(user)
                .book(book)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        KerimbaevBolatbekReview saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting review with id {}", id);
        KerimbaevBolatbekReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Review with id " + id + " not found"));
        reviewRepository.delete(review);
    }
}
