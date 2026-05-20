package com.lab.KerimbaevBolatbek.repository;

import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KerimbaevBolatbekBookRepository extends JpaRepository<KerimbaevBolatbekBook, Long> {

    Page<KerimbaevBolatbekBook> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<KerimbaevBolatbekBook> findByCategoryId(Long categoryId, Pageable pageable);
}