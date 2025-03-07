package com.chaan32.FindangBackend.domain;

import com.chaan32.FindangBackend.dto.InspectionRecordDTO;
import com.chaan32.FindangBackend.exception.NotFoundReservationException;
import com.chaan32.FindangBackend.service.ReservationService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "inspection_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspectionRecord {
    /*
    검사 기록에 대한 클래스
     */
    @Id
    @Column(name = "inspection_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inspectionRecordId;

    /*
    [관계도]
    1. User와 연결 해야 함 -> user ID 로 연결 해야 함
    2. Reservation과 연결 해야 함 -> reservation ID 로 연결 해야 함
     */

    //유저 아이디
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //예약 아이디
    @OneToOne(mappedBy = "inspectionRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reservation reservation;


    //검사 날짜
    @Column(name = "inspection_date")
    private Date inspectionDate;

    //당화혈색소
    @Column(name = "hba1c")
    private Double hba1c;

    //최고혈압
    @Column(name = "systolic_blood_pressure")
    private Integer systolicBP;

    //최저혈압
    @Column(name = "diastolic_blood_pressure")
    private Integer diastolicBP;

    //생성날짜 (데이터 생성)
    @Column(name = "createAt")
    private Date createAt;

    //수정 날짜 (데이터 수정)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt;

    public InspectionRecord(User user, Reservation reservation){
        this.user = user;
        this.reservation = reservation;
        this.createAt =  Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
    }

    public InspectionRecord update(InspectionRecordDTO dto, Reservation reservation){
        this.hba1c = dto.getHba1c();
        this.systolicBP = dto.getSystolicBP();
        this.diastolicBP = dto.getDiastolicBP();
        this.inspectionDate = dto.getInspectionDate();
        this.reservation = reservation;
        return this;
    }

    public InspectionRecord update(InspectionRecordDTO dto){
        this.hba1c = dto.getHba1c();
        this.systolicBP = dto.getSystolicBP();
        this.diastolicBP = dto.getDiastolicBP();
        this.updateAt = Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
        return this;
    }
}
