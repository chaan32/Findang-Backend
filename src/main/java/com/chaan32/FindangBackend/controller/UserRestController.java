package com.chaan32.FindangBackend.controller;

import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.dto.UserDTO;
import com.chaan32.FindangBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/findang/user")
public class UserRestController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("sign-up")
    public User signUp(@RequestBody UserDTO userDTO){
        log.info("[ [UserRestController] / [signUp] ] 입력된 userDTO : {}", userDTO);
        User user = userService.saveUser(UserDTO.toEntity(userDTO)); // UserDTO -> User toEntity하는 과정에서 userId 값은 null로 들어가지만, 알아서 채워짐
        return user;
    }

    // 로그인 아이디 중복 체크
    @GetMapping("/check/login-id")
    public Boolean checkLoginId(@RequestBody UserDTO requestDTO){
        log.info("[ [UserRestController] / [checkLoginId] ] 입력된 user_login_id  : {}", requestDTO.getUserLoginId());
        return !userService.overlapCheckLoginId(requestDTO.getUserLoginId());
    }


    // 로그인
    @GetMapping("/login")
    public Boolean login(@RequestBody UserDTO userLoginDTO){
        log.info("[ [UserRestController] / [login] ] 입력된 userLoginDTO : {}", userLoginDTO);
        return userService.loginProcess(userLoginDTO);
    }

    // 유저 이름 조회
    @GetMapping("/name")
    public String getNameByUserId(@RequestBody UserDTO userIdDTO){
        log.info("[ [UserRestController] / [getNameByUserId] ] 입력된 userIdDTO : {}", userIdDTO);
        Optional<User> user = userService.getUser(userIdDTO.getUserId());
        return user.map(User::getUserName).orElseGet(
                ()-> defaultMessage(String.valueOf(userIdDTO.getUserId()))
        );
    }

    private String defaultMessage(String userId){
        log.info("defaultMessage 호출 : {}", userId);
        return userId + " : 사용자 없음";
    }
}
