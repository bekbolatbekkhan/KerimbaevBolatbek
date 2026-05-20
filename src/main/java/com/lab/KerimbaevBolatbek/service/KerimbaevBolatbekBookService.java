package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekBookRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekBookResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekAuthor;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekBook;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekCategory;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekResourceNotFoundException;
import com.lab.KerimbaevBolatbek.mapper.KerimbaevBolatbekBookMapper;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekAuthorRepository;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekBookRepository;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KerimbaevBolatbekBookService {

    private final KerimbaevBolatbekBookRepository bookRepository;
    private final KerimbaevBolatbekCategoryRepository categoryRepository;
    private final KerimbaevBolatbekAuthorRepository authorRepository;
    private final KerimbaevBolatbekBookMapper bookMapper;

    // ===== Pagination + Search + Filter =====
    public Page<KerimbaevBolatbekBookResponse> getAll(String search, Long categoryId, Pageable pageable) {
        log.info("Fetching books with search={}, categoryId={}, pageable={}", search, categoryId, pageable);

        Page<KerimbaevBolatbekBook> books;

        if (search != null && !search.isBlank()) {
            books = bookRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else if (categoryId != null) {
            books = bookRepository.findByCategoryId(categoryId, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        return books.map(bookMapper::toResponse);
    }

    public KerimbaevBolatbekBookResponse getById(Long id) {
        log.info("Fetching book with id {}", id);
        return bookMapper.toResponse(findBookById(id));
    }

    public KerimbaevBolatbekBookResponse create(KerimbaevBolatbekBookRequest request) {
        log.info("Creating new book: {}", request.getTitle());
        KerimbaevBolatbekBook book = bookMapper.toEntity(request);

        // set category
        if (request.getCategoryId() != null) {
            KerimbaevBolatbekCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                            "Category with id " + request.getCategoryId() + " not found"));
            book.setCategory(category);
        }

        // set authors
        if (request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()) {
            Set<KerimbaevBolatbekAuthor> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            book.setAuthors(authors);
        }

        KerimbaevBolatbekBook saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    public KerimbaevBolatbekBookResponse update(Long id, KerimbaevBolatbekBookRequest request) {
        log.info("Updating book with id {}", id);
        KerimbaevBolatbekBook book = findBookById(id);
        bookMapper.updateEntity(book, request);

        if (request.getCategoryId() != null) {
            KerimbaevBolatbekCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                            "Category with id " + request.getCategoryId() + " not found"));
            book.setCategory(category);
        }

        if (request.getAuthorIds() != null) {
            Set<KerimbaevBolatbekAuthor> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            book.setAuthors(authors);
        }

        KerimbaevBolatbekBook saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting book with id {}", id);
        KerimbaevBolatbekBook book = findBookById(id);
        bookRepository.delete(book);
    }

    public KerimbaevBolatbekBook findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Book with id " + id + " not found"));
    }
}