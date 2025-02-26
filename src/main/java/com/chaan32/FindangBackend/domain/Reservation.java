package com.chaan32.FindangBackend.domain;

import com.chaan32.FindangBackend.dto.ReservationDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    /*
    예약에 대한 클래스
     */
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    /*
    [관계도]
    1. User와 연결 해야 함 -> user ID 로 연결 해야 함
    2. InspectionRecord와 연결 해야 함
     */

    //(예약) 유저 아이디 -> 유저 아이디로 연결
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //검사 기록에 대한 연결
    //FK의 주체
    @OneToOne
    @JoinColumn(name = "inspection_record_id")
    private InspectionRecord inspectionRecord;

    //예약 날짜
    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;

    //검사 항목
    @Column(name = "inspection_item01")
    private Integer inspectionItem01;

    @Column(name = "inspection_item02")
    private Integer inspectionItem02;

    @Column(name = "inspection_item03")
    private Integer inspectionItem03;

    //검사 유무
    @Column(name = "status", nullable = false)
    private Boolean status;

    //생성날짜 (데이터 생성) -> DTO에서 build 할 때 생성
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createAt", nullable = false)
    private Date createAt;

    //수정 날짜 (데이터 수정)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt;

    public Reservation updateReservation(ReservationDTO reservationDTO){
        this.reservationDate = reservationDTO.getReservationDate();
        this.inspectionItem01 = reservationDTO.getInspectionItem01();
        this.inspectionItem02 = reservationDTO.getInspectionItem02();
        this.inspectionItem03 = reservationDTO.getInspectionItem03();
        this.updateAt = Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
        return this;
    }


}
