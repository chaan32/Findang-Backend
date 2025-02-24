package com.chaan32.FindangBackend.repository;

import com.chaan32.FindangBackend.domain.BloodSugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BloodSugarRepository extends JpaRepository<BloodSugar, Long> {
    @Query("SELECT bs FROM BloodSugar bs WHERE bs.user.userId = :userId AND bs.checkDate BETWEEN :startDate AND :endDate")
    List<BloodSugar> findByUser_UserIdAndCheckDateBetween(@Param("userId") Long userId, Date startDate, @Param("endDate") Date endDate);

    List<BloodSugar> findByUser_UserId(Long userId);
}
