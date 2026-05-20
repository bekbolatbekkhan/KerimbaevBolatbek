package com.lab.KerimbaevBolatbek.service;

import com.lab.KerimbaevBolatbek.dto.response.KerimbaevBolatbekUserResponse;
import com.lab.KerimbaevBolatbek.entity.KerimbaevBolatbekUser;
import com.lab.KerimbaevBolatbek.exception.KerimbaevBolatbekResourceNotFoundException;
import com.lab.KerimbaevBolatbek.mapper.KerimbaevBolatbekUserMapper;
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
public class KerimbaevBolatbekUserService {

    private final KerimbaevBolatbekUserRepository userRepository;
    private final KerimbaevBolatbekUserMapper userMapper;

    public List<KerimbaevBolatbekUserResponse> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public KerimbaevBolatbekUserResponse getById(Long id) {
        log.info("Fetching user with id {}", id);
        KerimbaevBolatbekUser user = findUserById(id);
        return userMapper.toResponse(user);
    }

    public void delete(Long id) {
        log.info("Deleting user with id {}", id);
        KerimbaevBolatbekUser user = findUserById(id);
        userRepository.delete(user);
    }

    public KerimbaevBolatbekUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new KerimbaevBolatbekResourceNotFoundException(
                        "User with id " + id + " not found"));
    }
}

