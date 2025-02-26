package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.domain.type.ISType;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationResponseDTO {
    private Long reservationId;
    private Long userId;
    private Date reservationDate;
    private Boolean status;
    private Date createAt;
    private Date updateAt;
    private String inspectionItem01;
    private String inspectionItem02;
    private String inspectionItem03;

    public ReservationResponseDTO(Reservation reservation){
        this.reservationId = reservation.getReservationId();
        this.userId = reservation.getUser().getUserId();
        this.reservationDate = reservation.getReservationDate();
        this.status = reservation.getStatus();
        this.createAt = reservation.getCreateAt();
        this.updateAt = reservation.getUpdateAt();
        this.inspectionItem01 = ISType.getDescriptionByNumber(reservation.getInspectionItem01());
        this.inspectionItem02 = ISType.getDescriptionByNumber(reservation.getInspectionItem02());
        this.inspectionItem03 = ISType.getDescriptionByNumber(reservation.getInspectionItem03());
    }
}
