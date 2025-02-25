package com.chaan32.FindangBackend.domain;


import com.chaan32.FindangBackend.dto.BloodSugarDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "blood_sugar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodSugar {
    @Id
    @Column(name = "blood_sugar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bloodSugarId;

    //User와 연결 해야 함 -> user ID 로 연결 해야 함
    //유저 아이디
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //혈당 값
    @Column(name = "blood_sugar_value", nullable = false)
    private Integer bloodSugarValue;

    //검사 시점
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_date", nullable = false)
    private Date checkDate;

    //검사 타입 -> 아침 식전, 아침 식후 등
    @Column(name = "check_type", nullable = false)
    private Integer checkType;

    //검사 상태 -> 정상, 과도, 저혈당 등
    @Column(name = "status", nullable = false)
    private Integer status;

    //생성날짜 (데이터 생성)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createAt", nullable = false)
    private Date createAt;

    //수정 날짜 (데이터 수정)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt;

    public void updateBloodSugar(BloodSugarDTO bloodSugarDTO){
        this.bloodSugarValue = bloodSugarDTO.getBloodSugarValue();
        this.checkDate = bloodSugarDTO.getCheckDate();
        this.checkType = bloodSugarDTO.getCheckType();
        this.status = bloodSugarDTO.getStatus();
        this.updateAt = Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
    }
}
