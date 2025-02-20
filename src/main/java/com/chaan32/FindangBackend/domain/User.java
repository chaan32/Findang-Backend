package com.chaan32.FindangBackend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_login_id", unique = true, nullable = false)
    private String userLoginId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    /*
    <관계도>
    BloodSugar : User = N : 1 관계
    InspectionRecord : User = N : 1 관계 (검사 기록)
    Reservation : User = N : 1 관계 (예약)
    InspectionRecord : Reservation = 1 : 1 관계
     */

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BloodSugar> bloodSugars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InspectionRecord> inspectionRecords;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}