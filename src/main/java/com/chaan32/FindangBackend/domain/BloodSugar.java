package com.chaan32.FindangBackend.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "blood_sugar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "check_date", nullable = false)
    private Date checkDate;

    //검사 타입 -> 아침 식전, 아침 식후 등
    @Column(name = "check_type", nullable = false)
    private String checkType;

    //검사 상태 -> 정상, 과도, 저혈당 등
    @Column(name = "status", nullable = false)
    private String status;

    //생성날짜 (데이터 생성)
    @Column(name = "createAt", nullable = false)
    private Date createAt;
}
