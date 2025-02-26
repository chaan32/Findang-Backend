package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class ReservationDTO {
    @JsonProperty("reservation_id")
    private Long reservationId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("reservation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date reservationDate;

    // 검사항목
    @JsonProperty("inspection_item01")
    private Integer inspectionItem01;
    @JsonProperty("inspection_item02")
    private Integer inspectionItem02;
    @JsonProperty("inspection_item03")
    private Integer inspectionItem03;

    public static Reservation toEntity(ReservationDTO reservationDTO, User user){
        return Reservation.builder()
                .user(user)
                .reservationDate(reservationDTO.reservationDate)
                .inspectionItem01(reservationDTO.inspectionItem01)
                .inspectionItem02(reservationDTO.inspectionItem02)
                .inspectionItem03(reservationDTO.inspectionItem03)
                .status(false)
                .createAt(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant()))
                .build();
    }
}
