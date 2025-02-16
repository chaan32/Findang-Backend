package com.chaan32.FindangBackend.service;

import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public User saveUser (User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User getUser (Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}

