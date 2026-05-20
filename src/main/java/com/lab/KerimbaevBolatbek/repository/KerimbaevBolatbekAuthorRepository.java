package com.lab.KerimbaevBolatbek.repository;

import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KerimbaevBolatbekAuthorRepository extends JpaRepository<KerimbaevBolatbekAuthor, Long> {
}