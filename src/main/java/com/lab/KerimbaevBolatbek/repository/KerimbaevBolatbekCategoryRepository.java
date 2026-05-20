package com.lab.KerimbaevBolatbek.repository;

import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KerimbaevBolatbekCategoryRepository extends JpaRepository<KerimbaevBolatbekCategory, Long> {
}