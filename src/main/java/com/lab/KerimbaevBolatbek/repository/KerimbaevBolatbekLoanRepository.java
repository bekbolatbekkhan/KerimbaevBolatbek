package com.lab.KerimbaevBolatbek.repository;

import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KerimbaevBolatbekLoanRepository extends JpaRepository<KerimbaevBolatbekLoan, Long> {

    List<KerimbaevBolatbekLoan> findByUserId(Long userId);
}