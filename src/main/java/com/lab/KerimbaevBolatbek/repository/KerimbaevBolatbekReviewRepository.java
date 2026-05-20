package com.lab.KerimbaevBolatbek.repository;

import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KerimbaevBolatbekReviewRepository extends JpaRepository<KerimbaevBolatbekReview, Long> {

    List<KerimbaevBolatbekReview> findByBookId(Long bookId);
}