package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.request.KerimbaevBolatbekCategoryRequest;
import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekCategoryResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekCategory;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekResourceNotFoundException;
import com.lab.KerimbaevBolatbek.mapper.KerimbaevBolatbekCategoryMapper;
import com.lab.KerimbaevBolatbek.repository.KerimbaevBolatbekCategoryRepository;
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
public class KerimbaevBolatbekCategoryService {

    private final KerimbaevBolatbekCategoryRepository categoryRepository;
    private final KerimbaevBolatbekCategoryMapper categoryMapper;

    public List<KerimbaevBolatbekCategoryResponse> getAll() {
        log.info("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public KerimbaevBolatbekCategoryResponse getById(Long id) {
        log.info("Fetching category with id {}", id);
        KerimbaevBolatbekCategory category = findCategoryById(id);
        return categoryMapper.toResponse(category);
    }

    public KerimbaevBolatbekCategoryResponse create(KerimbaevBolatbekCategoryRequest request) {
        log.info("Creating new category: {}", request.getName());
        KerimbaevBolatbekCategory category = categoryMapper.toEntity(request);
        KerimbaevBolatbekCategory saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public KerimbaevBolatbekCategoryResponse update(Long id, KerimbaevBolatbekCategoryRequest request) {
        log.info("Updating category with id {}", id);
        KerimbaevBolatbekCategory category = findCategoryById(id);
        categoryMapper.updateEntity(category, request);
        KerimbaevBolatbekCategory saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public void delete(Long id) {
        log.info("Deleting category with id {}", id);
        KerimbaevBolatbekCategory category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    // helper method for internal use
    public KerimbaevBolatbekCategory findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "Category with id " + id + " not found"));
    }
}
