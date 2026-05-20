package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekAuthorRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekAuthorResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekAuthor;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekResourceNotFoundException;
import com.lab.KerimbaevBolatbek.mapper.KerimbaevBolatbekAuthorMapper;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekAuthorRepository;
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
public class KerimbaevBolatbekAuthorService {

    private final KerimbaevBolatbekAuthorRepository authorRepository;
    private final KerimbaevBolatbekAuthorMapper authorMapper;

    public List<KerimbaevBolatbekAuthorResponse> getAll() {
        log.info("Fetching all authors");
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public KerimbaevBolatbekAuthorResponse getById(Long id) {
        log.info("Fetching author with id {}", id);
        KerimbaevBolatbekAuthor author = findAuthorById(id);
        return authorMapper.toResponse(author);
    }

    public KerimbaevBolatbekAuthorResponse create(KerimbaevBolatbekAuthorRequest request) {
        log.info("Creating new author: {} {}", request.getFirstName(), request.getLastName());
        KerimbaevBolatbekAuthor author = authorMapper.toEntity(request);
        KerimbaevBolatbekAuthor saved = authorRepository.save(author);
        return authorMapper.toResponse(saved);
    }

    public KerimbaevBolatbekAuthorResponse update(Long id, KerimbaevBolatbekAuthorRequest request) {
        log.info("Updating author with id {}", id);
        KerimbaevBolatbekAuthor author = findAuthorById(id);
        authorMapper.updateEntity(author, request);
        KerimbaevBolatbekAuthor saved = authorRepository.save(author);
        return authorMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting author with id {}", id);
        KerimbaevBolatbekAuthor author = findAuthorById(id);
        authorRepository.delete(author);
    }

    public KerimbaevBolatbekAuthor findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Author with id " + id + " not found"));
    }
}
