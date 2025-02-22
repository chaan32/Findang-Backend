package com.chaan32.FindangBackend.repository;

import com.chaan32.FindangBackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserLoginId(String userLoginId);

    Optional<User> findByUserLoginIdAndUserPassword(String userLoginId, String userPassword);

    Optional<User> findByUserLoginId(String userLoginId);

    Optional<User> findByUserId(Long userId);
}
