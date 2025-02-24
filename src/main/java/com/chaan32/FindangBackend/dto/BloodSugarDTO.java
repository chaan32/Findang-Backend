package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.BloodSugar;
import com.chaan32.FindangBackend.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class BloodSugarDTO {
    @JsonProperty("blood_sugar_id")
    private Long bloodSugarId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("blood_sugar_value")
    private Integer bloodSugarValue;

    @JsonProperty("check_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date checkDate;

    @JsonProperty("check_type")
    private Integer checkType;

    @JsonProperty("status")
    private Integer status;


    /*
           [여기서 해야할 일]
           1. User 객체를 받아서 저장
           2. createAt 날짜 생성
     */
    public static BloodSugar toEntity(BloodSugarDTO bloodSugarDTO, User user){
        return BloodSugar.builder()
                .user(user)
                .bloodSugarValue(bloodSugarDTO.bloodSugarValue)
                .checkDate(bloodSugarDTO.checkDate)
                .checkType(bloodSugarDTO.checkType)
                .status(bloodSugarDTO.status)
                .createAt(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant())) // 한국의 현재 시간을 저장
                .build();
    }

}
