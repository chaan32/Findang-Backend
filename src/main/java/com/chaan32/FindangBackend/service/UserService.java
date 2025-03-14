package com.chaan32.FindangBackend.service;

import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.dto.UserDTO;
import com.chaan32.FindangBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    /*

    [필요한 메소드]
    - saveUser : User 저장o
    - overlapCheckLoginId : 로그인 아이디 중복 체크
    - login : 로그인
    - getUser : User 조회

     */

    // User 저장 (회원 가입)
    public User saveUser (User user) {
        return userRepository.save(user);
    }

    // 로그인 아이디 중복 체크
    public Boolean overlapCheckLoginId(String user_login_id){
        log.info("[ [UserService] / [overlapCheckLoginId] ] : {}", user_login_id);
        return userRepository.existsByUserLoginId(user_login_id);
    }

    // 로그인
    public Boolean loginProcess(UserDTO userDTO){
        log.info("[ [UserService] / [loginProcess] ] try to loginID: {}", userDTO.getUserLoginId());
        Optional<User> byUserLoginId = userRepository.findByUserLoginId(userDTO.getUserLoginId());
        if(byUserLoginId.isEmpty()){
            return false;
        }
        Optional<User> requestUser = userRepository.findByUserLoginIdAndUserPassword(userDTO.getUserLoginId(), userDTO.getUserPassword());
        return requestUser.isPresent();
    }

    // 유저 객체 찾아 오기
    public Optional<User> getUser (Long userId) {
        return userRepository.findByUserId(userId);
    }
}

