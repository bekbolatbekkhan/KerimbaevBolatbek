package com.lab.KerimbaevBolatbek.repository;

import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KerimbaevBolatbekUserRepository extends JpaRepository<KerimbaevBolatbekUser, Long> {

    Optional<KerimbaevBolatbekUser> findByUsername(String username);

    Optional<KerimbaevBolatbekUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}