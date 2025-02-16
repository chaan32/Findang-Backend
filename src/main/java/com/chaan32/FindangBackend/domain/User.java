package com.chaan32.FindangBackend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long useId;

    @Column(name = "user_name", nullable = false)
    private String useName;

    @Column(name = "user_login_id", unique = true, nullable = false)
    private String useLoginId;

    @Column(name = "user_password", nullable = false)
    private String usePassword;
}