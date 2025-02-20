package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class UserDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_login_id")
    private String userLoginId;
    @JsonProperty("user_password")
    private String userPassword;
    @JsonProperty("user_name")
    private String userName;

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .userId(userDTO.getUserId())
                .userLoginId(userDTO.getUserLoginId())
                .userPassword(userDTO.getUserPassword())
                .userName(userDTO.getUserName())
                .build();
    }
}
